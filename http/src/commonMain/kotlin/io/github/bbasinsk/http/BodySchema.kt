package io.github.bbasinsk.http

import io.github.bbasinsk.schema.Schema


sealed interface BodySchema<A> {
    data class Single<A>(val schema: Schema<A>, val contentType: ContentType) : BodySchema<A>
    data class WithMetadata<A>(val schema: BodySchema<A>, val metadata: Metadata<A>) : BodySchema<A>

    fun schema(): Schema<A> = when (this) {
        is Single -> schema
        is WithMetadata -> schema.schema()
    }

    fun contentType(): ContentType = when (this) {
        is Single -> contentType
        is WithMetadata -> schema.contentType()
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

    fun example(description: String, value: A): BodySchema<A> =
        WithMetadata(this, Metadata.Example(description, value))

    fun deprecated(message: String): BodySchema<A> =
        WithMetadata(this, Metadata.Deprecated(message))

    fun description(description: String): BodySchema<A> =
        WithMetadata(this, Metadata.Description(description))

    companion object {
        fun empty(): BodySchema<Nothing?> = Single(Schema.empty(), ContentType.Plain)

        fun <A> plain(schema: Schema.Companion.() -> Schema<A>): BodySchema<A> =
            Single(Schema.Companion.schema(), ContentType.Plain)

        fun <A> multipart(record: Schema.Companion.() -> Schema<A>): BodySchema<A> =
            Single(Schema.Companion.record(), ContentType.MultipartFormData)

        fun <A> formUrlEncoded(record: Schema.Companion.() -> Schema<A>): BodySchema<A> =
            Single(Schema.Companion.record(), ContentType.FormUrlEncoded)

        fun bytes(contentType: ContentType): BodySchema<ByteArray> =
            Single(Schema.Bytes, contentType)

        fun html(): BodySchema<String> = Single(Schema.string(), ContentType.Html)

        // TODO: move out of http module into http-json, http-avro, etc.
        fun <A> json(schema: Schema.Companion.() -> Schema<A>): BodySchema<A> =
            Single(Schema.Companion.schema(), ContentType.Json)

        fun <A> avro(schema: Schema.Companion.() -> Schema<A>): BodySchema<A> =
            Single(Schema.Companion.schema(), ContentType.Avro)
    }
}
