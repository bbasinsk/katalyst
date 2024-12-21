package io.github.bbasinsk.http

import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.decodeString


// A ParamSchema is a description of a Path, Query Parameters, and Headers.
sealed interface ParamsSchema<A> {
    data class Combine<A, B>(val left: ParamsSchema<A>, val right: ParamsSchema<B>) : ParamsSchema<Pair<A, B>>

    data class HeaderSchema<A>(val param: ParamSchema<A>) : ParamsSchema<A>
    data class QuerySchema<A>(val param: ParamSchema<A>) : ParamsSchema<A>

    fun <B> withHeader(right: ParamSchema<B>): ParamsSchema<Pair<A, B>> = Combine(this, HeaderSchema(right))
    fun <B> withQuery(right: ParamSchema<B>): ParamsSchema<Pair<A, B>> = Combine(this, QuerySchema(right))

    fun pathSchemas(): List<PathSchema<*>> =
        when (this) {
            is Combine<*, *> -> left.pathSchemas() + right.pathSchemas()
            is PathSchema.Combine<*, *> -> left.pathSchemas() + right.pathSchemas()
            is PathSchema.Parameter -> listOf(this)
            is PathSchema.Segment -> listOf(this)
            is PathSchema.RootSchema -> listOf()
            is HeaderSchema -> listOf()
            is QuerySchema -> listOf()
        }
}

// A description of a path
// - Path: /v1/packages/:id
sealed interface PathSchema<A> : ParamsSchema<A> {
    data object RootSchema : PathSchema<Unit>
    data class Segment(val name: String) : PathSchema<Unit>
    data class Parameter<A>(val param: ParamSchema<A>) : PathSchema<A>
    data class Combine<A, B>(val left: PathSchema<A>, val right: PathSchema<B>) : PathSchema<Pair<A, B>>

    // `operator fun div` so that we can use the / operator
    //      ie: Root / "users" / param("userId") { int() }
    operator fun div(segment: String): PathSchema<Pair<A, Unit>> = Combine(this, Segment(segment))
    operator fun <B> div(param: ParamSchema<B>): PathSchema<Pair<A, B>> = Combine(this, Parameter(param))

    companion object {
        val Root: PathSchema<Unit> = RootSchema
        fun <A> param(name: String, schema: Schema.Companion.() -> Schema<A>): ParamSchema<A> =
            ParamSchema.Single(name, Schema.Companion.schema())
    }
}

fun <A> ParamsSchema<A>.parseCatching(
    path: List<String>,
    headers: Map<String, String>,
    queryParams: Map<String, String>
): Result<A> =
    runCatching {
        parse(path.toMutableList(), headers, queryParams)
    }

fun <A> ParamSchema<A>.parse(getValue: (String) -> String?): A =
    when (this) {
        is ParamSchema.Single -> {
            val str = getValue(name)
            if (str == null) {
                error("Missing param $name")
            } else {
                schema.decodeString(str).getOrThrow()
            }
        }

        is ParamSchema.WithMetadata -> schema.parse(getValue)
    }

// TODO: Validation<A, B>?
fun <A> ParamsSchema<A>.parse(
    rawPath: MutableList<String>,
    rawHeaders: Map<String, String>,
    rawQueryParams: Map<String, String>,
): A {
    return when (this) {
        is ParamsSchema.HeaderSchema -> this.param.parse(rawHeaders::get)
        is ParamsSchema.QuerySchema -> this.param.parse(rawQueryParams::get)

        is ParamsSchema.Combine<*, *> -> {
            val left = this.left.parse(rawPath, rawHeaders, rawQueryParams)
            val right = this.right.parse(rawPath, rawHeaders, rawQueryParams)
            return Pair(left, right) as A
        }

        is PathSchema.RootSchema -> Unit as A
        is PathSchema.Combine<*, *> -> {
            val left = this.left.parse(rawPath, rawHeaders, rawQueryParams)
            val right = this.right.parse(rawPath, rawHeaders, rawQueryParams)
            return Pair(left, right) as A
        }

        is PathSchema.Parameter -> {
            rawPath.removeFirstOrNull()
                ?.let { rawValue -> this.param.schema().decodeString(rawValue).getOrThrow() }
                ?: error("Expected parameter ${this.param.name()}")
        }

        is PathSchema.Segment -> {
            if (rawPath.firstOrNull() == this.name) {
                rawPath.removeFirst()
                return Unit as A
            } else {
                error("Expected segment ${this.name} but found ${rawPath.firstOrNull()}")
            }
        }
    }
}
