package io.github.bbasinsk.schema

import kotlin.reflect.KClass

data class ObjectMetadata<A>(
    val name: String,
    val namespace: String?
) {
    fun qualifiedName(): String =
        if (namespace == null) name else "$namespace.$name"
}

fun <A> KClass<*>.toMetadata(): ObjectMetadata<A> =
    if (qualifiedName == null) {
        ObjectMetadata(
            name = simpleName ?: "Unknown",
            namespace = null
        )
    } else {
        ObjectMetadata(
            name = qualifiedName!!.substringAfterLast("."),
            namespace = qualifiedName!!.substringBeforeLast(".")
        )
    }
