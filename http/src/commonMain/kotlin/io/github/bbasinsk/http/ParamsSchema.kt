package io.github.bbasinsk.http

import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.decodePrimitiveString

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
    data class Segment(override val name: String, val prefix: EmptyPathSchema) : EmptyPathSchema, PathSegment

    operator fun div(segment: String): EmptyPathSchema = Segment(segment, this)
    operator fun <B> div(param: ParamSchema<B>): NonEmptyPathSchema.First<B> =
        NonEmptyPathSchema.First(param, this)
}

// Non-empty path state - has at least one param
sealed interface NonEmptyPathSchema<A> : PathSchema<A> {
    data class First<A>(override val param: ParamSchema<A>, val prefix: EmptyPathSchema) : NonEmptyPathSchema<A>, PathParam
    data class Segment<A>(override val name: String, val prefix: NonEmptyPathSchema<A>) : NonEmptyPathSchema<A>, PathSegment
    data class Next<A, B>(val prefix: NonEmptyPathSchema<A>, override val param: ParamSchema<B>) : NonEmptyPathSchema<Pair<A, B>>, PathParam

    operator fun div(segment: String): NonEmptyPathSchema<A> = Segment(segment, this)
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

// Extension functions for withQuery/withHeader - eliminates Pair<Unit, ...>
fun <B> EmptyPathSchema.withQuery(right: ParamSchema<B>): ParamsSchema<B> =
    ParamsSchema.CombineEmpty(this, ParamsSchema.QuerySchema(right))

fun <B> EmptyPathSchema.withHeader(right: ParamSchema<B>): ParamsSchema<B> =
    ParamsSchema.CombineEmpty(this, ParamsSchema.HeaderSchema(right))

fun <A, B> ParamsSchema<A>.withQuery(right: ParamSchema<B>): ParamsSchema<Pair<A, B>> =
    ParamsSchema.Combine(this, ParamsSchema.QuerySchema(right))

fun <A, B> ParamsSchema<A>.withHeader(right: ParamSchema<B>): ParamsSchema<Pair<A, B>> =
    ParamsSchema.Combine(this, ParamsSchema.HeaderSchema(right))

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
        is ParamSchema.Single -> when (schema) {
            is Schema.Collection<*> ->
                @Suppress("UNCHECKED_CAST")
                getValue(name).orEmpty().map { raw ->
                    schema.itemSchema.decodePrimitiveString(raw).getOrThrow()
                } as A

            else -> schema.decodePrimitiveString(getValue(name)?.firstOrNull() ?: "").getOrThrow()
        }
    }

// TODO: Validation<A, B>?
fun <A> ParamsSchema<A>.parse(
    rawPath: MutableList<String>,
    rawHeaders: Map<String, List<String>>,
    rawQueryParams: Map<String, List<String>>,
): A {
    return when (this) {
        is ParamsSchema.HeaderSchema -> this.param.parse(rawHeaders::get)
        is ParamsSchema.QuerySchema -> this.param.parse(rawQueryParams::get)

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
