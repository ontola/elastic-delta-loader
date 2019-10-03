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

package io.ontola.linkeddelta.loader.base

import io.ontola.linkeddelta.loader.elastic.Document
import io.ontola.ori.api.*
import io.ontola.ori.api.context.ResourceCtx
import org.eclipse.rdf4j.model.Model
import org.eclipse.rdf4j.model.Statement

abstract class DocumentSetBase<DS>(
    internal open val docCtx: ResourceCtx<*, DS>,
    internal open val delta: Model
) {
    open fun addAll(s: List<Statement>): Boolean {
        return delta.addAll(s)
    }

    abstract fun process()

    internal abstract fun findLatestDocument(): Document?

    internal abstract fun initNewVersion(existing: Document?): Document

    override operator fun equals(other: Any?): Boolean {
        if (other == null || this.javaClass != other.javaClass) {
            return false
        }

        return docCtx.iri == (other as DeltaEvent).iri
    }

    override fun hashCode(): Int {
        return docCtx.iri.hashCode()
    }
}
