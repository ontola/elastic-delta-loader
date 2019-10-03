/*
 * ORI API
 * Copyright (C), Argu BV
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package io.ontola.linkeddelta.loader.elastic

import io.ontola.linkeddelta.applyDelta
import io.ontola.linkeddelta.loader.base.DocumentSetBase
import io.ontola.linkeddelta.processors
import io.ontola.ori.api.context.ResourceCtx
import org.eclipse.rdf4j.model.Model
import org.eclipse.rdf4j.model.impl.LinkedHashModel
import org.eclipse.rdf4j.model.vocabulary.VCARD4
import org.elasticsearch.client.RestHighLevelClient

class DocumentSet(
    override val docCtx: ResourceCtx<*, RestHighLevelClient>,
    override val delta: Model = LinkedHashModel()
) : DocumentSetBase<RestHighLevelClient>(docCtx, delta) {
    override fun process() {
//        System.out.printf("[at:%s][orid:%s] starting processing\n", docCtx.record?.timestamp(), docCtx.id)

        val latestVersion = findLatestDocument()
        val newVersion = initNewVersion(latestVersion)

        newVersion.save()

//        System.out.printf("[at:%s][orid:%s] finished processing\n", docCtx.record?.timestamp(), docCtx.id)
    }

    override fun findLatestDocument(): Document? {
        return Document.findExisting(docCtx)
    }

    override fun initNewVersion(existing: Document?): Document {
        val data = existing?.data ?: LinkedHashModel()
        val newData = applyDelta(processors, data, delta)
        val partition = partition(newData)

        return Document(
            docCtx.copy(
                partition = partition
            ),
            newData
        )
    }

    private fun partition(data: Model): String? {
        return data
            .find { s -> s.subject == docCtx.iri && s.predicate == VCARD4.HAS_ORGANIZATION_NAME }
            ?.`object`
            ?.stringValue()
            ?.split("/")
            ?.last()
    }
}
