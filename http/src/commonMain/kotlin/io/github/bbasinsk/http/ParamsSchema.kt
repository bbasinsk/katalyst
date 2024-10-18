package io.github.bbasinsk.http

import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.StandardPrimitive

// TODO:
// Support using Schema<A> for other types.. ie: double, long, uuid, etc
// - separate Primitive transform from Schema transform?

sealed interface Metadata {
    data class Example<A>(val example: Pair<String, A>) : Metadata
    data class Deprecated(val reason: String) : Metadata
    data class Description(val description: String) : Metadata
}

// A ParamSchema is a description of a Path, Query Parameters, and Headers.
sealed interface ParamsSchema<A> {
    data class Combine<A, B>(val left: ParamsSchema<A>, val right: ParamsSchema<B>) : ParamsSchema<Pair<A, B>>

    operator fun <B> plus(right: ParamsSchema<B>): ParamsSchema<Pair<A, B>> = Combine(this, right)

    fun pathSchemas(): List<PathSchema<*>> =
        when (this) {
            is Combine<*, *> -> left.pathSchemas() + right.pathSchemas()
            is PathSchema.Combine<*, *> -> left.pathSchemas() + right.pathSchemas()
            is PathSchema.Parameter -> listOf(this)
            is PathSchema.Segment -> listOf(this)
            is PathSchema.RootSchema -> listOf()
            is PathSchema.WithMetadata -> listOf()
            is HeaderSchema -> listOf()
            is QuerySchema -> listOf()
        }
}

// A description of a header
// - Header: X-User-Id: 1
sealed interface HeaderSchema<A> : ParamsSchema<A> {
    data class Optional<A>(val schema: HeaderSchema<A>) : HeaderSchema<A?>
    data class Single<A>(val name: String, val schema: Schema.Primitive<A>) : HeaderSchema<A>
    data class WithMetadata<A>(val schema: HeaderSchema<A>, val metadata: Metadata) : HeaderSchema<A>

    fun optional(): HeaderSchema<A?> = Optional(this)
    fun example(description: String, value: A): HeaderSchema<A> = WithMetadata(this, Metadata.Example(description to value))
    fun deprecated(message: String): HeaderSchema<A> = WithMetadata(this, Metadata.Deprecated(message))
    fun description(description: String): HeaderSchema<A> = WithMetadata(this, Metadata.Description(description))

    companion object {
        fun int(name: String): HeaderSchema<Int> = Single(name, Schema.Primitive(StandardPrimitive.Int))
        fun string(name: String): HeaderSchema<String> = Single(name, Schema.Primitive(StandardPrimitive.String))
    }
}

// A description of a query parameter
// - Query: ?page=1&limit=10
sealed interface QuerySchema<A> : ParamsSchema<A> {
    data class Optional<A>(val schema: QuerySchema<A>) : QuerySchema<A?>
    data class Single<A>(val name: String, val schema: StandardPrimitive<A>) : QuerySchema<A>
    data class WithMetadata<A>(val schema: QuerySchema<A>, val metadata: Metadata) : QuerySchema<A>

    fun optional(): QuerySchema<A?> = Optional(this)
    fun example(description: String, value: A): QuerySchema<A> = WithMetadata(this, Metadata.Example(description to value))
    fun deprecated(message: String): QuerySchema<A> = WithMetadata(this, Metadata.Deprecated(message))
    fun description(description: String): QuerySchema<A> = WithMetadata(this, Metadata.Description(description))

    companion object {
        fun int(name: String): QuerySchema<Int> = Single(name, StandardPrimitive.Int)
        fun string(name: String): QuerySchema<String> = Single(name, StandardPrimitive.String)
    }
}

// A description of a path
// - Path: /v1/packages/:id
sealed interface PathSchema<A> : ParamsSchema<A> {
    data object RootSchema : PathSchema<Unit>
    data class Segment(val name: String) : PathSchema<Unit>
    data class Parameter<A>(val name: String, val schema: StandardPrimitive<A>) : PathSchema<A>
    data class Combine<A, B>(val left: PathSchema<A>, val right: PathSchema<B>) : PathSchema<Pair<A, B>>
    data class WithMetadata<A>(val schema: PathSchema<A>, val metadata: Metadata) : PathSchema<A>

    // `operator fun div` so that we can use the / operator
    //      ie: Root / "users" / int("userId")
    operator fun div(segment: String): PathSchema<Pair<A, Unit>> = Combine(this, Segment(segment))
    operator fun <B> div(other: PathSchema<B>): PathSchema<Pair<A, B>> = Combine(this, other)

    fun example(description: String, value: A): PathSchema<A> = WithMetadata(this, Metadata.Example(description to value))
    fun deprecated(message: String): PathSchema<A> = WithMetadata(this, Metadata.Deprecated(message))
    fun description(description: String): PathSchema<A> = WithMetadata(this, Metadata.Description(description))

    companion object {
        val Root: PathSchema<Unit> = RootSchema
        fun int(name: String): PathSchema<Int> = Parameter(name, StandardPrimitive.Int)
        fun string(name: String): PathSchema<String> = Parameter(name, StandardPrimitive.String)
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

// TODO: Validation<A, B>?
fun <A> ParamsSchema<A>.parse(
    rawPath: MutableList<String>,
    rawHeaders: Map<String, String>,
    rawQueryParams: Map<String, String>,
): A {
    return when (this) {
        is HeaderSchema.Optional<*> -> {
            runCatching { parse(rawPath, rawHeaders, rawQueryParams) }.getOrNull() as A
        }

        is HeaderSchema.WithMetadata ->
            schema.parse(rawPath, rawHeaders, rawQueryParams)

        is HeaderSchema.Single -> {
            val str = rawHeaders[name]
            if (str == null) {
                error("Missing header $name")
            } else {
                (schema.primitive.parse(str) ?: error("Failed to parse header $name"))
            }
        }

        is QuerySchema.Optional<*> -> {
            runCatching { schema.parse(rawPath, rawHeaders, rawQueryParams) }.getOrNull() as A
        }

        is QuerySchema.Single -> {
            val str = rawQueryParams[name]
            if (str == null) {
                error("Missing query param $name")
            } else {
                (schema.parse(str) ?: error("Failed to parse query param $name"))
            }
        }

        is QuerySchema.WithMetadata ->
            schema.parse(rawPath, rawHeaders, rawQueryParams)

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
                ?.let { rawValue -> schema.parse(rawValue) }
                ?: error("Expected parameter ${this.name}")
        }

        is PathSchema.Segment -> {
            if (rawPath.firstOrNull() == this.name) {
                rawPath.removeFirst()
                return Unit as A
            } else {
                error("Expected segment ${this.name} but found ${rawPath.firstOrNull()}")
            }
        }

        is PathSchema.WithMetadata ->
            schema.parse(rawPath, rawHeaders, rawQueryParams)
    }
}
