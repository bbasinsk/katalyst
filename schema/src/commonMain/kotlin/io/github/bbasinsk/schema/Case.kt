package io.github.bbasinsk.schema

data class Case<A, B : A>(
    val name: String,
    val schema: Schema<B>,
    val deconstruct: (A) -> B?
)
