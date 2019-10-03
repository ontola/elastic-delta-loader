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

package io.ontola.ori.api.context

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.eclipse.rdf4j.model.IRI

data class CtxProps(
    val cmd: String? = null,
    val record: ConsumerRecord<String, String>? = null,
    val iri: IRI? = null,
    val partition: String? = null,
    val version: String? = null
)

typealias ResourceCtxObj<T, DS> = ResourceCtx<T, DS>

abstract class ResourceCtx<T : ResourceCtx<T, DS>, DS>(
    open val ctx: CtxProps = CtxProps(),
    open val datastore: DS
) {
    abstract val cmd: String?

    abstract val iri: IRI?

    abstract val record: ConsumerRecord<String, String>?

    abstract val version: String?

    abstract val id: String?

    abstract val partition: String?

    abstract fun copy(
        cmd: String? = this.ctx.cmd,
        record: ConsumerRecord<String, String>? = this.ctx.record,
        iri: IRI? = this.ctx.iri,
        partition: String? = this.ctx.partition,
        version: String? = this.ctx.version,
        datastore: DS? = this.datastore
    ): ResourceCtxObj<T, DS>
}
