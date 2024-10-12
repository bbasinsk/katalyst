package io.github.bbasinsk.schema

import kotlin.reflect.KClass

data class Metadata<A>(
    val name: String,
    val namespace: String?
)

fun <A : Any> KClass<A>.toMetadata(): Metadata<A> =
    if (qualifiedName == null) {
        Metadata(
            name = simpleName ?: "Unknown",
            namespace = null
        )
    } else {
        Metadata(
            name = qualifiedName!!.substringAfterLast("."),
            namespace = qualifiedName!!.substringBeforeLast(".")
        )
    }
