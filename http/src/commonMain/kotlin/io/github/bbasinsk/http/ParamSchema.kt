package io.github.bbasinsk.http

import io.github.bbasinsk.schema.Schema


// A description of a header
// - Header: X-User-Id: 1

// A description of a query parameter
// - Query: ?page=1&limit=10

// A description of a path
// - Path: /v1/packages/:id
sealed interface ParamSchema<A> {
    data class Single<A>(val name: String, val schema: Schema<A>) : ParamSchema<A>
    data class WithMetadata<A>(val schema: ParamSchema<A>, val metadata: Metadata<A>) : ParamSchema<A>

    fun name(): String = when (this) {
        is Single -> name
        is WithMetadata -> schema.name()
    }

    fun schema(): Schema<A> = when (this) {
        is Single -> schema
        is WithMetadata -> schema.schema()
    }

    fun deprecatedReason(): String? = when (this) {
        is Single -> null
        is WithMetadata -> when (metadata) {
            is Metadata.Deprecated -> metadata.reason
            is Metadata.Description -> schema.deprecatedReason()
            is Metadata.Example<*> -> schema.deprecatedReason()
        }
    }

    fun description(): String? = when (this) {
        is Single -> null
        is WithMetadata -> when (metadata) {
            is Metadata.Description -> metadata.description
            is Metadata.Deprecated -> schema.description()
            is Metadata.Example<*> -> schema.description()
        }
    }

    fun examples(): Map<String, A> = when (this) {
        is Single -> emptyMap()
        is WithMetadata -> when (metadata) {
            is Metadata.Example -> schema.examples() + (metadata.description to metadata.value)
            is Metadata.Description -> schema.examples()
            is Metadata.Deprecated -> schema.examples()
        }
    }

    fun example(description: String, value: A): ParamSchema<A> =
        WithMetadata(this, Metadata.Example(description, value))

    fun deprecated(message: String): ParamSchema<A> =
        WithMetadata(this, Metadata.Deprecated(message))

    fun description(description: String): ParamSchema<A> =
        WithMetadata(this, Metadata.Description(description))

    companion object {
        fun <A> schema(name: String, schema: Schema.Companion.() -> Schema<A>): ParamSchema<A> =
            Single(name, Schema.Companion.schema())
    }
}
