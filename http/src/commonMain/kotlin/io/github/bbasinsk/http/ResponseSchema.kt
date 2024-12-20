package io.github.bbasinsk.http

import io.github.bbasinsk.schema.Schema

sealed interface ResponseSchema<A> {
    data object None : ResponseSchema<Nothing>
    data class Multiple<A>(val left: ResponseSchema<A>, val right: ResponseSchema<A>) : ResponseSchema<A>
    data class Single<A, B : A>(val status: ResponseStatus, val res: BodySchema<B>, val deconstruct: (A) -> B?) :
        ResponseSchema<A>

    fun schemasByStatus(): Map<ResponseStatus, Schema<*>> =
        flatten().mapValues { it.value.res.schema() }

    fun getStatus(value: A): ResponseStatus =
        flatten().entries.first { (_, res) -> res.deconstruct(value) != null }.key

    fun schemaByStatus(): Map<ResponseStatus, BodySchema<*>> =
        flatten().mapValues { it.value.res }

    private fun flatten(): Map<ResponseStatus, ResponseSchema.Single<A, *>> =
        when (this) {
            is ResponseSchema.Single<A, *> -> mapOf(status to this)
            is ResponseSchema.Multiple -> left.flatten() + right.flatten()
            is ResponseSchema.None -> emptyMap()
        }

    @Suppress("UNCHECKED_CAST")
    fun getSchema(value: A): BodySchema<A> =
        get(value).value.res as BodySchema<A>

    private fun get(value: A): Map.Entry<ResponseStatus, Single<A, *>> =
        flatten().entries.first { (_, res) -> res.deconstruct(value) != null }

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
        fun nothing(): ResponseSchema<Nothing> = None

        inline fun <A> oneOf(first: ResponseSchema<A>, vararg rest: ResponseSchema<A>): ResponseSchema<A> =
            rest.fold(first) { acc, schema -> Multiple(acc, schema) }

        inline fun <A, reified B : A> status(
            status: ResponseStatus,
            schema: BodySchema.Companion.() -> BodySchema<B>
        ): ResponseSchema<A> =
            Single<A, B>(status, BodySchema.schema()) { it as? B }
    }
}
