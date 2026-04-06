package io.github.bbasinsk.schema

import kotlin.reflect.KClass

actual fun <A> KClass<*>.toMetadata(typeArguments: List<String>): ObjectMetadata<A> =
    qualifiedName?.let { fqn ->
        ObjectMetadata(
            name = fqn.substringAfterLast("."),
            namespace = fqn.substringBeforeLast(".", missingDelimiterValue = "").ifBlank { null },
            typeArguments = typeArguments
        )
    } ?: ObjectMetadata(
        name = simpleName ?: "Unknown",
        namespace = null,
        typeArguments = typeArguments
    )
