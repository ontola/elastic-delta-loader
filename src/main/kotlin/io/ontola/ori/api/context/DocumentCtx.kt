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

package io.ontola.ori.api.context

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.eclipse.rdf4j.model.IRI

/** To keep track of the state while processing a record into a file on disk. */
class DocumentCtx<DS>(
    override val ctx: CtxProps,
    override val datastore: DS
) : ResourceCtx<DocumentCtx<DS>, DS>(ctx, datastore) {

    override val cmd: String? = ctx.cmd

    override val iri: IRI? = ctx.iri

    override val record: ConsumerRecord<String, String>? = ctx.record

    override val version: String? = ctx.version

    override val id: String? = ctx.iri?.stringValue()?.substring(ctx.iri.stringValue().lastIndexOf('/') + 1)

    override val partition: String? = ctx.partition

    override fun copy(
        cmd: String?,
        record: ConsumerRecord<String, String>?,
        iri: IRI?,
        partition: String?,
        version: String?,
        datastore: DS?
    ): DocumentCtx<DS> {
        return DocumentCtx(
            ctx.copy(
                cmd = cmd,
                record = record,
                iri = iri,
                partition = partition,
                version = version
            ),
            datastore ?: this.datastore
        )
    }
}
