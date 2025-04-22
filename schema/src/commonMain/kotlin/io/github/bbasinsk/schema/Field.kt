package io.github.bbasinsk.schema

data class Field<A, B>(
    val name: String,
    val schema: Schema<B>,
    val extract: (A) -> B
)

data class FieldMetadata(
    val description: String? = null
)
