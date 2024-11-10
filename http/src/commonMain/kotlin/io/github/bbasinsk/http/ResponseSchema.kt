package io.github.bbasinsk.http

import io.github.bbasinsk.schema.Schema

data class ResponseStatus(val code: Int, val description: String) {
    fun description(description: String): ResponseStatus = copy(description = description)

    companion object {
        val Ok: ResponseStatus = ResponseStatus(200, "OK")
        val Created: ResponseStatus = ResponseStatus(201, "Created")
        val NoContent: ResponseStatus = ResponseStatus(204, "No Content")
        val BadRequest: ResponseStatus = ResponseStatus(400, "Bad Request")
        val Unauthorized: ResponseStatus = ResponseStatus(401, "Unauthorized")
        val Forbidden: ResponseStatus = ResponseStatus(403, "Forbidden")
        val NotFound: ResponseStatus = ResponseStatus(404, "Not Found")
        val Conflict: ResponseStatus = ResponseStatus(409, "Conflict")
    }
}

data class ResponseCase<A, B : A>(
    val schema: Schema<B>,
    val deconstruct: (A) -> B?,
    val examples: Map<String, B>
)

inline fun <A, reified B : A> responseCase(
    schema: Schema<B>,
    examples: Map<String, B> = emptyMap()
): ResponseCase<A, B> =
    ResponseCase(
        schema = schema,
        deconstruct = { it as? B },
        examples = examples
    )

sealed interface ResponseSchema<A> {

    data class Single<A>(val status: ResponseStatus, val case: ResponseCase<A, A>) : ResponseSchema<A>

    data class Multiple<A>(val schemaByStatus: Map<ResponseStatus, ResponseCase<A, *>>) : ResponseSchema<A>

    fun schemasByStatus(): Map<ResponseStatus, Schema<A>> =
        when (this) {
            is Single -> mapOf(status to case.schema)
            is Multiple -> schemaByStatus.mapValues { (_, case) -> case.schema as Schema<A> }
        }

    fun flatten(): Map<ResponseStatus, ResponseCase<A, *>> =
        when (this) {
            is Single -> mapOf(status to case)
            is Multiple -> schemaByStatus
        }

    fun getStatus(value: A): ResponseStatus =
        when (this) {
            is Single -> status
            is Multiple -> schemaByStatus.entries.first { (_, case) -> case.deconstruct(value) != null }.key
        }

    fun getSchema(value: A): Schema<A> =
        when (this) {
            is Single -> case.schema
            is Multiple -> schemaByStatus.entries.first { (_, case) -> case.deconstruct(value) != null }.value.schema as Schema<A>
        }

    companion object {

        fun <A> oneOf(vararg schemas: Pair<ResponseStatus, ResponseCase<A, *>>): ResponseSchema<A> =
            Multiple(schemas.toMap())

        fun <A> status(
            status: ResponseStatus,
            schema: Schema<A>,
            examples: Map<String, A> = emptyMap(),
        ): ResponseSchema<A> =
            Single(status, ResponseCase<A, A>(schema, { it }, examples))

        fun empty(): ResponseSchema<Nothing?> =
            status(ResponseStatus.NoContent.description("Empty"), Schema.empty())
    }
}
