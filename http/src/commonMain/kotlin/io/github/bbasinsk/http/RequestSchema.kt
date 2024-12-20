package io.github.bbasinsk.http

import io.github.bbasinsk.schema.Schema


sealed interface RequestSchema<A> {
    data class Single<A>(val schema: Schema<A>) : RequestSchema<A>
    data class WithMetadata<A>(val schema: RequestSchema<A>, val metadata: Metadata<A>) : RequestSchema<A>

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

    fun example(description: String, value: A): RequestSchema<A> =
        WithMetadata(this, Metadata.Example(description, value))

    fun deprecated(message: String): RequestSchema<A> =
        WithMetadata(this, Metadata.Deprecated(message))

    fun description(description: String): RequestSchema<A> =
        WithMetadata(this, Metadata.Description(description))

    companion object {
        fun empty(): RequestSchema<Nothing?> = Single(Schema.empty())
        fun <A> schema(schema: Schema.Companion.() -> Schema<A>): RequestSchema<A> =
            Single(Schema.Companion.schema())
    }
}
