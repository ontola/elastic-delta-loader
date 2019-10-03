package io.ontola.linkeddelta.processor

import org.eclipse.rdf4j.model.Statement

data class DeltaProcessorResult(
    val addable: List<Statement>,
    val removable: List<Statement>,
    val replaceable: List<Statement>
)
