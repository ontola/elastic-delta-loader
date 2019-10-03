package io.ontola.rdfUtils

import io.inbot.eskotlinwrapper.ModelReaderAndWriter
import io.ontola.ori.api.ORio
import io.ontola.ori.api.elastic.ORILDFormat
import io.ontola.ori.api.elastic.ORILDSettings
import org.eclipse.rdf4j.model.Model
import org.eclipse.rdf4j.model.IRI
import org.eclipse.rdf4j.model.impl.LinkedHashModel
import java.io.StringWriter
import java.nio.charset.StandardCharsets
import kotlin.reflect.KClass

class JSONLDModelReaderAndWriter(private val identityResource: IRI?) : ModelReaderAndWriter<Model> {
    override val clazz: KClass<Model>
        get() = Model::class

    override fun deserializer(): (ByteArray?) -> Model {
        return lambda@{ data ->
            if (data === null) {
                return@lambda LinkedHashModel()
            }

            ORio.parseToModel(
                data.toString(StandardCharsets.UTF_8),
                identityResource.toString(),
                ORILDFormat
            )
        }
    }

    override fun serializer(): (Model) -> ByteArray {
        return { model ->
            val out = StringWriter()

            val writer = ORio.createWriter(ORILDFormat, out)
            writer.setContext("https://id.openraadsinformatie.nl/")
            writer.set(ORILDSettings.BASE_DOCUMENT, identityResource?.stringValue())
            writer.handleSingleModel(model)

            out.toString().toByteArray()
        }
    }
}
