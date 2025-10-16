package io.github.bbasinsk.schema

import kotlin.ExperimentalStdlibApi
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.typeOf

data class ObjectMetadata<A>(
    val name: String,
    val namespace: String?,
    val typeArguments: List<String> = emptyList()
) {
    fun qualifiedName(): String =
        if (namespace == null) name else "$namespace.$name"
}

fun <A> KClass<*>.toMetadata(typeArguments: List<String> = emptyList()): ObjectMetadata<A> =
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

@OptIn(ExperimentalStdlibApi::class)
inline fun <reified A> metadataFromType(): ObjectMetadata<A> {
    val type = typeOf<A>()
    val classifier = type.classifier
    return if (classifier is KClass<*>) {
        classifier.toMetadata(type.typeArgumentNames())
    } else {
        (classifier as? KClass<*>)?.toMetadata() ?: A::class.toMetadata()
    }
}

@PublishedApi
internal fun KType.typeArgumentNames(): List<String> =
    arguments.mapNotNull { projection ->
        projection.type?.qualifiedTypeName()
    }

@PublishedApi
internal fun KType.qualifiedTypeName(): String {
    val classifier = classifier
    if (classifier !is KClass<*>) error(
        """
        |Type argument '$classifier' could not be resolved. This typically happens when using a generic schema function without 'inline reified'.
        |
        |To fix this, update your function signature from:
        |  fun <A> Schema.Companion.yourFunction(schema: Schema<A>): Schema<YourType<A>>
        |
        |To:
        |  inline fun <reified A> Schema.Companion.yourFunction(schema: Schema<A>): Schema<YourType<A>>
        """.trimMargin()
    )
    val metadata = classifier.toMetadata<Any?>()
    val base = metadata.qualifiedName()
    return arguments
        .mapNotNull { it.type?.qualifiedTypeName() }
        .fold(base) { acc, arg -> "$acc.of.$arg" }
}
