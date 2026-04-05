package io.github.bbasinsk.schema

import kotlin.reflect.KClass

actual fun <A> KClass<*>.toMetadata(typeArguments: List<String>): ObjectMetadata<A> =
    if (qualifiedName == null) {
        ObjectMetadata(
            name = simpleName ?: "Unknown",
            namespace = null,
            typeArguments = typeArguments
        )
    } else {
        ObjectMetadata(
            name = qualifiedName!!.substringAfterLast("."),
            namespace = qualifiedName!!.substringBeforeLast("."),
            typeArguments = typeArguments
        )
    }
