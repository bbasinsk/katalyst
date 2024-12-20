package io.github.bbasinsk.http

import io.github.bbasinsk.schema.Schema

sealed interface ResponsesSchema<A> {
    data object None : ResponsesSchema<kotlin.Nothing>
    data class Single<A>(val status: ResponseStatus, val case: ResponseCase<A>) : ResponsesSchema<A>
    data class Multiple<A>(val schemaByStatus: Map<ResponseStatus, ResponseCase<A>>) : ResponsesSchema<A>

    fun schemasByStatus(): Map<ResponseStatus, Schema<A>> =
        when (this) {
            is Single -> mapOf(status to case.schema())
            is Multiple -> schemaByStatus.mapValues { (_, case) -> case.schema() }
            is None -> emptyMap()
        }

    fun flatten(): Map<ResponseStatus, ResponseCase<A>> =
        when (this) {
            is Single -> mapOf(status to case)
            is Multiple -> schemaByStatus
            is None -> emptyMap()
        }

    fun getStatus(value: A): ResponseStatus =
        when (this) {
            is Single -> status
            is Multiple -> schemaByStatus.entries.first { (_, case) -> case.deconstruct(value) != null }.key
            is None -> NoContent
        }

    fun getSchema(value: A): Schema<A> =
        when (this) {
            is Single -> case.schema()
            is Multiple -> schemaByStatus.entries.first { (_, case) -> case.deconstruct(value) != null }.value.schema() as Schema<A>
            is None -> error("No schema for empty response")
        }

    fun example(description: String, value: A): ResponsesSchema<A> =
        when(this) {
            is None -> this
            is Multiple<*> -> this
            is Single -> copy(case = case.example(description, value))
        }

    companion object {
        // Status Codes
        val Ok: ResponseStatus = ResponseStatus(200, "OK")
        val Created: ResponseStatus = ResponseStatus(201, "Created")
        val NoContent: ResponseStatus = ResponseStatus(204, "No Content")
        val BadRequest: ResponseStatus = ResponseStatus(400, "Bad Request")
        val Unauthorized: ResponseStatus = ResponseStatus(401, "Unauthorized")
        val Forbidden: ResponseStatus = ResponseStatus(403, "Forbidden")
        val NotFound: ResponseStatus = ResponseStatus(404, "Not Found")
        val Conflict: ResponseStatus = ResponseStatus(409, "Conflict")

        // Response Schemas
        // TODO: disallow 0 responses
        fun <A> oneOf(vararg schemas: Pair<ResponseStatus, ResponseCase<A>>): ResponsesSchema<A> =
            Multiple(schemas.toMap())

        inline fun <A, reified B : A> status(
            status: ResponseStatus,
            schema: Schema.Companion.() -> Schema<B>,
        ): ResponsesSchema<A> =
            Single(status, responseCase<A, B>(Schema.schema()))

        fun nothing(): ResponsesSchema<Nothing> = None
        fun empty(): ResponsesSchema<Nothing?> =
            Single(NoContent.description("Empty"), ResponseCase.empty())
    }
}

sealed interface ResponseCase<A> {
    data class Case<A, B : A>(val schema: Schema<B>, val deconstruct: (A) -> B?) : ResponseCase<A>
    data class WithMetadata<A>(val case: ResponseCase<A>, val metadata: Metadata<A>) : ResponseCase<A>

    fun deconstruct(value: A): A? = when (this) {
        is Case<*, *> -> deconstruct(value)
        is WithMetadata -> case.deconstruct(value)
    }

    @Suppress("UNCHECKED_CAST")
    fun schema(): Schema<A> = when (this) {
        is Case<A, *> -> schema as Schema<A>
        is WithMetadata -> case.schema()
    }

    fun examples(): Map<String, A> = when (this) {
        is Case<*, *> -> emptyMap()
        is WithMetadata -> when (metadata) {
            is Metadata.Deprecated -> case.examples()
            is Metadata.Description -> case.examples()
            is Metadata.Example -> case.examples() + (metadata.description to metadata.value)
        }
    }

    fun example(description: String, value: A): ResponseCase<A> =
        WithMetadata(this, Metadata.Example(description, value))

    companion object {
        fun empty(): ResponseCase<Nothing?> = Case(Schema.empty()) { it }
    }
}

inline fun <A, reified B : A> responseCase(schema: Schema<B>): ResponseCase<A> =
    ResponseCase.Case(
        schema = schema,
        deconstruct = { it as? B },
    )
