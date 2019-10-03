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

package io.ontola.linkeddelta.loader.base

import io.ontola.ori.api.context.ResourceCtx
import org.eclipse.rdf4j.model.Model
import org.eclipse.rdf4j.model.util.Models

/**
 * A resource in the data store.
 */
abstract class DocumentBase<DS>(
    internal open val docCtx: ResourceCtx<*, DS>,
    internal open val data: Model
) {
    fun id(): String {
        return docCtx.iri!!.stringValue().substring((docCtx.iri?.stringValue()?.lastIndexOf('/') ?: 0) + 1)
    }

    abstract fun save(): DocumentBase<DS>

    /**
     * Reads the existing document from the store into the model, overwriting any previous statements.
     */
    internal abstract fun read()

    /**
     * Compares two documents by their contents.
     *
     * Will return true even if the versions differ, but the IRI and statements are equal.
     */
    override operator fun equals(other: Any?): Boolean {
        if (other == null || this.javaClass != other.javaClass) {
            return false
        }

        return docCtx.iri == (other as DocumentBase<DS>).docCtx.iri && Models.isomorphic(data, other.data)
    }

    override fun hashCode(): Int {
        return "${docCtx.iri.hashCode()}${data.hashCode()}".hashCode()
    }
}
