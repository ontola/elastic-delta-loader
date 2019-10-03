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

package io.ontola.ori.api.elastic

import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.github.jsonldjava.core.*
import com.github.jsonldjava.utils.JsonUtils
import com.google.common.io.Resources
import org.eclipse.rdf4j.model.*
import org.eclipse.rdf4j.model.impl.LinkedHashModel
import org.eclipse.rdf4j.model.impl.SimpleValueFactory
import org.eclipse.rdf4j.model.vocabulary.RDF
import org.eclipse.rdf4j.rio.RDFFormat
import org.eclipse.rdf4j.rio.RDFFormat.SUPPORTS_CONTEXTS
import org.eclipse.rdf4j.rio.RDFFormat.SUPPORTS_NAMESPACES
import org.eclipse.rdf4j.rio.RDFWriter
import org.eclipse.rdf4j.rio.RioSetting
import org.eclipse.rdf4j.rio.WriterConfig
import org.eclipse.rdf4j.rio.helpers.JSONLDSettings
import org.eclipse.rdf4j.rio.helpers.StatementCollector
import org.eclipse.rdf4j.rio.helpers.StringRioSetting
import org.eclipse.rdf4j.rio.jsonld.JSONLDHierarchicalProcessor
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.io.Writer
import java.nio.charset.StandardCharsets
import java.util.*

private class ModelParser : RDFParser {
    override fun parse(input: Any?): RDFDataset {
        if (input !is Model) {
            throw IllegalArgumentException("Model parser must be given a rdf4j.model.Model")
        }

        val dataset = RDFDataset()
        for (st in input) {
            val subj = parseValue(st.subject)
            val pred = parseValue(st.predicate)
            val obj = st.`object`

            if (obj is Literal) {
                dataset.addTriple(
                    subj,
                    pred,
                    obj.stringValue(),
                    obj.datatype.stringValue(),
                    obj.language.orElse(null)
                )
            } else {
                dataset.addTriple(subj, pred, parseValue(obj))
            }
        }

        return dataset
    }

    private fun parseValue(value: Value): String {
        return if (value is Resource) {
            value.toString()
        } else {
            value.stringValue()
        }
    }
}

val ORILDFormat = RDFFormat(
    "JSON-LD ORI Style",
    Arrays.asList<String>("application/ld+json"),
    StandardCharsets.UTF_8,
    Arrays.asList<String>("jsonld"),
    SimpleValueFactory.getInstance().createIRI("https://docs.openraadsinformatie.nl"),
    SUPPORTS_NAMESPACES,
    SUPPORTS_CONTEXTS)

class ORILDSettings {
    companion object {
        val BASE_DOCUMENT = StringRioSetting(
            "baseDocument",
            "The main resource in the dataset",
            null
        )
    }
}

/**
 * Patches some issues with regards to formatting and top-level @context string values for optimal ORI v0 API compatibility
 */
class ORILDWriter : RDFWriter {
    private var config: WriterConfig
    private val writer: Writer
    private val context = Context(JsonLdOptions(null))

    private val model = LinkedHashModel()
    private val collector = StatementCollector(model)
    private val rdf = SimpleValueFactory.getInstance()

    private val contexts = mapOf<IRI, String>(
        rdf.createIRI("https://argu.co/ns/meeting/AgendaItem") to "orild.agendaitem.jsonld",
        rdf.createIRI("http://schema.org/CreativeWork") to "orild.creativework.jsonld",
        rdf.createIRI("http://schema.org/Event") to "orild.event.jsonld",
        rdf.createIRI("http://schema.org/MediaObject") to "orild.mediaobject.jsonld",
        rdf.createIRI("https://argu.co/ns/meeting/Meeting") to "orild.meeting.jsonld",
        rdf.createIRI("http://www.w3.org/ns/org#Membership") to "orild.membership.jsonld",
        rdf.createIRI("http://www.w3.org/ns/org#Organization") to "orild.organization.jsonld",
        rdf.createIRI("http://www.w3.org/ns/person#Person") to "orild.person.jsonld"
    )

    private val baseDocument
        get() = rdf.createIRI(config.get(ORILDSettings.BASE_DOCUMENT))

    constructor(out: OutputStream, config: WriterConfig) {
        this.config = config
        this.writer = OutputStreamWriter(out)
    }

    constructor(writer: Writer, config: WriterConfig) {
        this.config = config
        this.writer = writer
    }

    override fun getWriterConfig(): WriterConfig {
        return config
    }

    override fun setWriterConfig(config: WriterConfig?): RDFWriter {
        if (config != null) {
            this.config = config
        }

        return this
    }

    override fun getSupportedSettings(): MutableCollection<RioSetting<*>> {
        return Collections.emptyList()
    }

    override fun <T : Any?> set(setting: RioSetting<T>?, value: T): RDFWriter {
        return ORILDWriter(writer, config.set(setting, value))
    }

    override fun getRDFFormat(): RDFFormat {
        return ORILDFormat
    }

    override fun handleNamespace(prefix: String?, uri: String?) {
        context[prefix] = uri
    }

    override fun handleComment(comment: String?) {
        /* JSON has no comments */
    }

    override fun handleStatement(st: Statement?) {
        collector.handleStatement(st)
    }

    override fun startRDF() {}

    override fun endRDF() {
        val options = JsonLdOptions()
        options.useRdfType = false
        options.useNativeTypes = true
        val oriCtx = Resources.getResource(contextFileForModel()).openStream()
        val oriCtxObj = Parser.default().parse(oriCtx) as JsonObject?
        oriCtxObj?.forEach { k, v -> context[k] = v }

        var obj: Any = JsonLdProcessor.fromRDF(model, options, ModelParser())
        if (writerConfig.get(JSONLDSettings.HIERARCHICAL_VIEW)) {
            obj = JSONLDHierarchicalProcessor.fromJsonLdObject(obj)
        }
        obj = JsonLdProcessor.compact(obj, context, options)

        JsonUtils.writePrettyPrint(writer, obj)
    }

    private fun contextFileForModel(): String {
        if (baseDocument == null) {
            return "orild.jsonld"
        }

        val specificCtxDocument = model
            .filter { s -> s.subject == baseDocument && s.predicate == RDF.TYPE }
            .map { s -> s.`object` }
            .find { obj -> contexts[obj] !== null }
            ?.let { obj -> contexts[obj] }

        return specificCtxDocument ?: "orild.jsonld"
    }
}
