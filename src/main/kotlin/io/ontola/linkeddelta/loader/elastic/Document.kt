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
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package io.ontola.linkeddelta.loader.elastic

import io.ontola.linkeddelta.loader.base.DocumentBase
import io.ontola.ori.api.ORIContext
import io.ontola.ori.api.context.ResourceCtx
import io.ontola.rdfUtils.JSONLDModelReaderAndWriter
import org.eclipse.rdf4j.model.Model
import org.elasticsearch.ElasticsearchStatusException
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.client.crudDao

/**
 * A resource in the data store.
 */
class Document(
    override val docCtx: ResourceCtx<*, RestHighLevelClient>,
    override val data: Model
) : DocumentBase<RestHighLevelClient>(docCtx, data) {

    override fun save(): Document {
        System.out.printf(
            "[at:%s][orid:%s] Writing subject '${docCtx.iri}' to partition '${docCtx.partition}'\n",
            docCtx.record?.timestamp(),
            docCtx.id
        )

        val dao = docCtx.datastore.crudDao(index(docCtx), JSONLDModelReaderAndWriter(docCtx.iri))
        dao.index(docCtx.id!!, data, create = false)

        return this
    }

    override fun read() {
        val dao = docCtx.datastore.crudDao(index(docCtx), JSONLDModelReaderAndWriter(docCtx.iri))
        val test: Model? = dao.get(docCtx.id!!)
        if (test !== null) {
            data.clear()
            data.addAll(test)
        }
    }

    companion object {
        fun index(docCtx: ResourceCtx<*, RestHighLevelClient>): String {
            val partition = docCtx.partition
            if (partition === null) {
                throw Exception("Document ${docCtx.iri} has no partition")
            }

            return listOf(
                ORIContext.getCtx().config.getProperty("ori.api.elastic.index_prefix"),
                partition
            ).joinToString("_")
        }

        fun findExisting(docCtx: ResourceCtx<*, RestHighLevelClient>): Document? {
            val id = docCtx.id
            if (id === null) {
                return null
            }

            // We don't have enough information here to calculate the index name
            val searchIndex = listOf(
                ORIContext.getCtx().config.getProperty("ori.api.elastic.index_prefix"),
                "*"
            ).joinToString("_")

            try {
                val dao = docCtx.datastore.crudDao(searchIndex, JSONLDModelReaderAndWriter(docCtx.iri))
                val model = dao.get(id)

                if (model === null) {
                    return null
                }

                return Document(
                    docCtx.copy(),
                    model
                )
            } catch(e: ElasticsearchStatusException) {
                if (e.status().status == 404) {
                    return null
                }

                throw e
            }
        }
    }

}
