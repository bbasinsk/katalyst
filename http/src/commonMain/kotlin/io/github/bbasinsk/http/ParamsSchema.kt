package io.github.bbasinsk.http

import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.decodePrimitiveString
import io.github.bbasinsk.schema.encodePrimitiveString
import kotlin.jvm.JvmName

// Marker interfaces for pattern matching
sealed interface PathSegment {
    val name: String
}

sealed interface PathParam {
    val param: ParamSchema<*>
}

// A ParamSchema is a description of a Path, Query Parameters, and Headers.
sealed interface ParamsSchema<A> {
    data class Combine<A, B>(val left: ParamsSchema<A>, val right: ParamsSchema<B>) : ParamsSchema<Pair<A, B>>
    // Combines EmptyPathSchema with query/header - type is B, not Pair<Unit, B>
    data class CombineEmpty<B>(val path: EmptyPathSchema, val other: ParamsSchema<B>) : ParamsSchema<B>
    data class HeaderSchema<A>(val param: ParamSchema<A>) : ParamsSchema<A>
    data class QuerySchema<A>(val param: ParamSchema<A>) : ParamsSchema<A>

    fun pathSchemas(): List<ParamsSchema<*>> =
        when (this) {
            is Combine<*, *> -> left.pathSchemas() + right.pathSchemas()
            is CombineEmpty<*> -> path.pathSchemas() + other.pathSchemas()
            is EmptyPathSchema.Root -> listOf()
            is EmptyPathSchema.Segment -> prefix.pathSchemas() + listOf(this)
            is NonEmptyPathSchema.First<*> -> prefix.pathSchemas() + listOf(this)
            is NonEmptyPathSchema.Segment<*> -> prefix.pathSchemas() + listOf(this)
            is NonEmptyPathSchema.Next<*, *> -> prefix.pathSchemas() + listOf(this)
            is HeaderSchema -> listOf()
            is QuerySchema -> listOf()
        }
}

// Base sealed interface for path schemas
sealed interface PathSchema<A> : ParamsSchema<A>

// Empty path state - no parameters yet (always Unit type)
sealed interface EmptyPathSchema : PathSchema<Unit> {
    data object Root : EmptyPathSchema
    data class Segment( val prefix: EmptyPathSchema, override val name: String) : EmptyPathSchema, PathSegment

    operator fun div(segment: String): EmptyPathSchema = Segment(this, segment)
    operator fun <B> div(param: ParamSchema<B>): NonEmptyPathSchema.First<B> = NonEmptyPathSchema.First(this, param)
}

// Non-empty path state - has at least one param
sealed interface NonEmptyPathSchema<A> : PathSchema<A> {
    data class First<A>(val prefix: EmptyPathSchema, override val param: ParamSchema<A>) : NonEmptyPathSchema<A>, PathParam
    data class Segment<A>(val prefix: NonEmptyPathSchema<A>, override val name: String) : NonEmptyPathSchema<A>, PathSegment
    data class Next<A, B>(val prefix: NonEmptyPathSchema<A>, override val param: ParamSchema<B>) : NonEmptyPathSchema<Pair<A, B>>, PathParam

    operator fun div(segment: String): NonEmptyPathSchema<A> = Segment(this, segment)
    operator fun <B> div(param: ParamSchema<B>): NonEmptyPathSchema<Pair<A, B>> = Next(this, param)
}

val Root: EmptyPathSchema = EmptyPathSchema.Root

fun <A> param(name: String, schema: Schema.Companion.() -> Schema<A>): ParamSchema<A> =
    ParamSchema.Single(name, Schema.Companion.schema())

object PathDsl {
    val Root: EmptyPathSchema = EmptyPathSchema.Root
    fun <A> param(name: String, schema: Schema.Companion.() -> Schema<A>): ParamSchema<A> =
        ParamSchema.Single(name, Schema.Companion.schema())
}

@JvmName("withQueryEmpty")
fun <B> ParamsSchema<Unit>.withQuery(right: ParamSchema<B>): ParamsSchema<B> =
    ParamsSchema.CombineEmpty(this as EmptyPathSchema, ParamsSchema.QuerySchema(right))

@JvmName("withHeaderEmpty")
fun <B> ParamsSchema<Unit>.withHeader(right: ParamSchema<B>): ParamsSchema<B> =
    ParamsSchema.CombineEmpty(this as EmptyPathSchema, ParamsSchema.HeaderSchema(right))

fun <A, B> ParamsSchema<A>.withQuery(right: ParamSchema<B>): ParamsSchema<Pair<A, B>> =
    ParamsSchema.Combine(this, ParamsSchema.QuerySchema(right))

fun <A, B> ParamsSchema<A>.withHeader(right: ParamSchema<B>): ParamsSchema<Pair<A, B>> =
    ParamsSchema.Combine(this, ParamsSchema.HeaderSchema(right))

fun ParamsSchema<*>.renderPath(): String =
    pathSchemas().mapNotNull { schema ->
        when (schema) {
            is PathSegment -> schema.name
            is PathParam -> "{${schema.param.name()}}"
            else -> null
        }
    }.joinToString(separator = "/", prefix = "/")

fun <A> ParamsSchema<A>.parseCatching(
    path: List<String>,
    headers: Map<String, List<String>>,
    queryParams: Map<String, List<String>>
): Result<A> =
    runCatching {
        parse(path.toMutableList(), headers, queryParams)
    }

fun <A> ParamSchema<A>.parse(getValue: (String) -> List<String>?): A =
    when (this) {
        is ParamSchema.WithMetadata -> schema.parse(getValue)
        is ParamSchema.Single -> when (val itemSchema = schema.collectionItemSchema()) {
            null -> schema.decodePrimitiveString(getValue(name)?.firstOrNull()).getOrThrow()
            else -> {
                val values = getValue(name)
                @Suppress("UNCHECKED_CAST") when (values) {
                    null -> schema.decodePrimitiveString(null).getOrThrow()
                    else -> values.map { itemSchema.decodePrimitiveString(it).getOrThrow() } as A
                }
            }
        }
    }

// TODO: Validation<A, B>?
fun <A> ParamsSchema<A>.parse(
    rawPath: MutableList<String>,
    rawHeaders: Map<String, List<String>>,
    rawQueryParams: Map<String, List<String>>,
): A {
    return when (this) {
        is ParamsSchema.HeaderSchema -> this.param.parse { name -> rawHeaders[name] }
        is ParamsSchema.QuerySchema -> this.param.parse { name ->
            rawQueryParams[name].takeIf { it != listOf("") } // handle `?name=` as null
        }

        is ParamsSchema.Combine<*, *> -> {
            val left = this.left.parse(rawPath, rawHeaders, rawQueryParams)
            val right = this.right.parse(rawPath, rawHeaders, rawQueryParams)
            return Pair(left, right) as A
        }

        is ParamsSchema.CombineEmpty<*> -> {
            path.parse(rawPath, rawHeaders, rawQueryParams) // Returns Unit, discarded
            other.parse(rawPath, rawHeaders, rawQueryParams) as A
        }

        is EmptyPathSchema.Root -> Unit as A
        is EmptyPathSchema.Segment -> {
            prefix.parse(rawPath, rawHeaders, rawQueryParams)
            if (rawPath.firstOrNull() == name) {
                rawPath.removeFirst()
            } else {
                error("Expected segment $name but found ${rawPath.firstOrNull()}")
            }
            Unit as A
        }

        is NonEmptyPathSchema.First<*> -> {
            prefix.parse(rawPath, rawHeaders, rawQueryParams)
            (rawPath.removeFirstOrNull()
                ?.let { rawValue -> param.schema().decodePrimitiveString(rawValue).getOrThrow() }
                ?: error("Expected parameter ${param.name()}")) as A
        }

        is NonEmptyPathSchema.Segment<*> -> {
            val prefixValue = prefix.parse(rawPath, rawHeaders, rawQueryParams)
            if (rawPath.firstOrNull() == name) {
                rawPath.removeFirst()
            } else {
                error("Expected segment $name but found ${rawPath.firstOrNull()}")
            }
            prefixValue as A
        }

        is NonEmptyPathSchema.Next<*, *> -> {
            val prefixValue = prefix.parse(rawPath, rawHeaders, rawQueryParams)
            val nextValue = rawPath.removeFirstOrNull()
                ?.let { rawValue -> param.schema().decodePrimitiveString(rawValue).getOrThrow() }
                ?: error("Expected parameter ${param.name()}")
            Pair(prefixValue, nextValue) as A
        }
    }
}

data class RenderedParams(
    val pathSegments: List<String>,
    val queryParams: Map<String, List<String>>,
    val headers: Map<String, List<String>>
) {
    fun toUrlPath(): String = buildString {
        append("/")
        append(pathSegments.joinToString("/"))
        if (queryParams.isNotEmpty()) {
            append("?")
            append(
                queryParams.flatMap { (name, values) ->
                    values.map { "$name=$it" }
                }.joinToString("&")
            )
        }
    }
}

@Suppress("UNCHECKED_CAST")
fun <A> ParamSchema<A>.render(value: A): Pair<String, List<String>> =
    when (this) {
        is ParamSchema.WithMetadata -> schema.render(value)
        is ParamSchema.Single -> {
            val itemSchema = schema.collectionItemSchema()
            when {
                itemSchema != null && value is List<*> ->
                    name to value.map { (itemSchema as Schema<Any?>).encodePrimitiveString(it).getOrThrow() }
                else ->
                    name to listOf(schema.encodePrimitiveString(value).getOrThrow())
            }
        }
    }

@Suppress("UNCHECKED_CAST")
fun <A> ParamsSchema<A>.render(value: A): RenderedParams =
    when (this) {
        is EmptyPathSchema.Root -> RenderedParams(emptyList(), emptyMap(), emptyMap())

        is EmptyPathSchema.Segment -> {
            val prefixRendered = prefix.render(Unit)
            prefixRendered.copy(pathSegments = prefixRendered.pathSegments + name)
        }

        is NonEmptyPathSchema.First<*> -> {
            val prefixRendered = prefix.render(Unit)
            val encoded = (param as ParamSchema<Any?>).render(value).second.first()
            prefixRendered.copy(pathSegments = prefixRendered.pathSegments + encoded)
        }

        is NonEmptyPathSchema.Segment<*> -> {
            val prefixRendered = (prefix as ParamsSchema<A>).render(value)
            prefixRendered.copy(pathSegments = prefixRendered.pathSegments + name)
        }

        is NonEmptyPathSchema.Next<*, *> -> {
            val pair = value as Pair<Any?, Any?>
            val prefixRendered = (prefix as ParamsSchema<Any?>).render(pair.first)
            val encoded = (param as ParamSchema<Any?>).render(pair.second).second.first()
            prefixRendered.copy(pathSegments = prefixRendered.pathSegments + encoded)
        }

        is ParamsSchema.QuerySchema -> {
            val (name, values) = param.render(value)
            RenderedParams(emptyList(), mapOf(name to values), emptyMap())
        }

        is ParamsSchema.HeaderSchema -> {
            val (name, values) = param.render(value)
            RenderedParams(emptyList(), emptyMap(), mapOf(name to values))
        }

        is ParamsSchema.Combine<*, *> -> {
            val pair = value as Pair<Any?, Any?>
            val leftRendered = (left as ParamsSchema<Any?>).render(pair.first)
            val rightRendered = (right as ParamsSchema<Any?>).render(pair.second)
            RenderedParams(
                pathSegments = leftRendered.pathSegments + rightRendered.pathSegments,
                queryParams = leftRendered.queryParams + rightRendered.queryParams,
                headers = leftRendered.headers + rightRendered.headers
            )
        }

        is ParamsSchema.CombineEmpty<*> -> {
            val pathRendered = path.render(Unit)
            val otherRendered = (other as ParamsSchema<A>).render(value)
            RenderedParams(
                pathSegments = pathRendered.pathSegments + otherRendered.pathSegments,
                queryParams = pathRendered.queryParams + otherRendered.queryParams,
                headers = pathRendered.headers + otherRendered.headers
            )
        }
    }
