package io.github.bbasinsk.schema

import kotlin.reflect.KClass

actual fun <A> KClass<*>.toMetadata(typeArguments: List<String>): ObjectMetadata<A> =
    ObjectMetadata(
        name = simpleName ?: "Unknown",
        namespace = null,
        typeArguments = typeArguments
    )
