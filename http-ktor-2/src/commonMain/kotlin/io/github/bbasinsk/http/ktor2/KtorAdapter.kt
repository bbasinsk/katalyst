package io.github.bbasinsk.http.ktor2

import io.github.bbasinsk.http.BodySchema
import io.github.bbasinsk.http.ContentType
import io.github.bbasinsk.http.Http
import io.github.bbasinsk.http.HttpEndpoint
import io.github.bbasinsk.http.HttpMethod
import io.github.bbasinsk.http.ParamsSchema
import io.github.bbasinsk.http.PathSchema
import io.github.bbasinsk.http.Request
import io.github.bbasinsk.http.Response
import io.github.bbasinsk.http.ResponseSchema
import io.github.bbasinsk.http.parseCatching
import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.decodePrimitiveString
import io.github.bbasinsk.schema.json.InvalidJson
import io.github.bbasinsk.schema.json.kotlinx.decodeFromJsonElement
import io.github.bbasinsk.schema.json.kotlinx.encodeToJsonElement
import io.github.bbasinsk.schema.transform
import io.github.bbasinsk.validation.*
import io.ktor.http.HttpStatusCode
import io.ktor.http.HttpStatusCode.Companion.UnprocessableEntity
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.request.httpMethod
import io.ktor.server.request.path
import io.ktor.server.request.receive
import io.ktor.server.request.receiveParameters
import io.ktor.server.request.receiveText
import io.ktor.server.response.respond
import io.ktor.server.response.respondBytes
import io.ktor.server.response.respondText
import io.ktor.server.routing.HttpMethodRouteSelector
import io.ktor.server.routing.PathSegmentConstantRouteSelector
import io.ktor.server.routing.PathSegmentParameterRouteSelector
import io.ktor.server.routing.Route
import io.ktor.server.routing.RouteSelector
import io.ktor.util.flattenEntries
import io.ktor.util.pipeline.PipelineInterceptor
import kotlinx.serialization.json.JsonElement
import kotlin.also
import kotlin.collections.filter
import kotlin.collections.fold
import kotlin.collections.mapNotNull
import kotlin.collections.toMap
import kotlin.collections.toMutableList
import kotlin.getOrThrow
import kotlin.let
import kotlin.text.isNotBlank
import kotlin.text.split

fun <Path, Input, Error, Output> Route.httpEndpointToRoute(
    endpoint: HttpEndpoint<Path, Input, Error, Output, ApplicationCall>
): Route =
    endpoint.api.toRoute(this)
        .withChildren(httpPipelineInterceptor(endpoint))

private fun Route.withChildren(handler: PipelineInterceptor<Unit, ApplicationCall>): Route =
    parent?.withChildren(handler)?.createChild(selector)?.also { it.handle(handler) } ?: this

private fun <Params, Input, Error, Output> Http<Params, Input, Error, Output>.toRoute(parent: Route): Route =
    Route(
        parent = params.toRoute(parent),
        selector = HttpMethodRouteSelector(io.ktor.http.HttpMethod.parse(method.name)),
        environment = parent.environment,
        developmentMode = parent.developmentMode
    )

private fun ParamsSchema<*>.toSelector(): RouteSelector? =
    when (this) {
        is PathSchema.Parameter -> PathSegmentParameterRouteSelector(param.name())
        is PathSchema.Segment -> PathSegmentConstantRouteSelector(name)
        else -> null
    }

private fun ParamsSchema<*>.toRoute(parent: Route): Route =
    pathSchemas().mapNotNull { it.toSelector() }.fold(parent) { route, selector ->
        Route(
            parent = route,
            selector = selector,
            environment = parent.environment,
            developmentMode = parent.developmentMode
        )
    }

private fun <Path, Input, Error, Output> httpPipelineInterceptor(
    endpoint: HttpEndpoint<Path, Input, Error, Output, ApplicationCall>
): PipelineInterceptor<Unit, ApplicationCall> =
    interceptor@{
        if (context.request.httpMethod != endpoint.api.method.toKtorMethod()) {
            call.respond(HttpStatusCode.MethodNotAllowed)
            return@interceptor
        }

        val rawPath = context.request.path().split("/").filter { it.isNotBlank() }
        val headers = context.request.headers.entries().associate { it.key to it.value }
        val query = context.request.queryParameters.entries().associate { it.key to it.value }
        val path: Path = endpoint.api.params.parseCatching(rawPath.toMutableList(), headers, query).getOrThrow()

        val input = call.receiveRequest(endpoint.api.input)
            .getOrElse { errors ->
                errors.onEach { call.application.environment.log.warn(it.message) }
                return@interceptor call.respondSchema(UnprocessableEntity, Schema.list(SchemaError.schema), errors)
            }

        val response = with(endpoint) {
            Response.handle(Request(path, input, context))
        }

        return@interceptor when (response) {
            is Response.Error -> call.respondSchema(endpoint.api.error, response.value)
            is Response.Success -> call.respondSchema(endpoint.api.output, response.value)
        }
    }

private suspend fun <A> ApplicationCall.respondSchema(schema: ResponseSchema<A>, value: A) =
    respondSchema(HttpStatusCode.fromValue(schema.getStatus(value).code), schema.getSchema(value).schema(), value)

private fun HttpMethod.toKtorMethod(): io.ktor.http.HttpMethod =
    when (this) {
        HttpMethod.GET -> io.ktor.http.HttpMethod.Get
        HttpMethod.POST -> io.ktor.http.HttpMethod.Post
        HttpMethod.PUT -> io.ktor.http.HttpMethod.Put
        HttpMethod.DELETE -> io.ktor.http.HttpMethod.Delete
        HttpMethod.PATCH -> io.ktor.http.HttpMethod.Patch
        HttpMethod.HEAD -> io.ktor.http.HttpMethod.Head
        HttpMethod.OPTIONS -> io.ktor.http.HttpMethod.Options
    }

@Suppress("UNCHECKED_CAST")
private suspend fun <A> ApplicationCall.receiveRequest(request: BodySchema<A>): Validation<SchemaError, A> =
    when (request) {
        is BodySchema.WithMetadata -> receiveRequest(request.schema)
        is BodySchema.Single -> when (request.contentType) {
            ContentType.Json -> receiveJson(request.schema()).mapInvalid { SchemaError(it.reason()) }
            ContentType.FormUrlEncoded -> Validation.requireNotNull(request.schema() as? Schema.Record<A>) {
                SchemaError("FormUrlEncoded must be Schema.Record, found ${request.schema()::class.simpleName}")
            }.andThen {
                receiveFormUrlEncoded(it)
            }
            ContentType.Plain -> Validation.fromResult(request.schema().decodePrimitiveString(receiveText())) {
                SchemaError(it.message ?: "Error decoding")
            }
            ContentType.Html -> Validation.fromResult(request.schema().decodePrimitiveString(receiveText())) {
                SchemaError(it.message ?: "Error decoding")
            }
            is ContentType.Image -> Validation.valid(receive<ByteArray>()) as Validation<SchemaError, A>
            ContentType.Avro -> TODO("Avro not supported in Ktor 2 adapter")
            ContentType.MultipartFormData -> TODO("MultipartFormData not supported in Ktor 2 adapter")
        }
    }

@Suppress("UNCHECKED_CAST")
private suspend fun <A> ApplicationCall.receiveFormUrlEncoded(schema: Schema.Record<A>): Validation<SchemaError, A> {
    val params = receiveParameters()
    val schemaFields = schema.unsafeFields()
    val fieldValues = schemaFields.map { field ->
        val values = params.getAll(field.name)
        when (val fieldSchema = field.schema) {
            is Schema.Collection<*> -> values?.map { v ->
                fieldSchema.itemSchema.decodePrimitiveString(v).getOrElse { e ->
                    return Validation.invalid(SchemaError("Error decoding field '${field.name}': ${e.message}"))
                }
            } ?: emptyList<Any?>()
            else -> values?.firstOrNull().let { value ->
                fieldSchema.decodePrimitiveString(value).getOrElse { e ->
                    return Validation.invalid(SchemaError("Error decoding field '${field.name}': ${e.message}"))
                }
            }
        }
    }
    return Validation.valid(schema.unsafeConstruct(fieldValues))
}

@Suppress("UNCHECKED_CAST")
private suspend fun <A> ApplicationCall.receiveJson(schema: Schema<A>): Validation<InvalidJson, A> =
    when (schema) {
        is Schema.Default -> TODO()
        is Schema.Optional<*> -> TODO()
        is Schema.Transform<*, *> -> TODO()
        is Schema.OrElse<A, *> -> receiveJson(schema.preferred).orElse { preferredErrors ->
            receiveJson(schema.fallback).andThen { b ->
                Validation.fromResult(schema.unsafeDecode(b)) {
                    InvalidJson.FieldError(it.message ?: "unable to decode", found = b.toString(), path = emptyList())
                }
            }.orElse { fallbackErrors ->
                Validation.invalid(InvalidJson.Or(preferredErrors, fallbackErrors))
            }
        }
        is Schema.Bytes -> Validation.valid(receive<ByteArray>() as A)
        is Schema.Empty -> Validation.valid(null as A)
        is Schema.Lazy -> receiveJson(with(schema) { schema() })
        is Schema.Metadata -> receiveJson(schema.schema)
        is Schema.Primitive -> receiveText().let { raw ->
            Validation.fromResult(schema.decodePrimitiveString(raw)) {
                InvalidJson.FieldError(expected = schema.name, found = raw, path = emptyList())
            }
        }

        is Schema.Collection<*> -> (schema as Schema<A>).decodeFromJsonElement(receive<JsonElement>())
        is Schema.StringMap<*> -> (schema as Schema<A>).decodeFromJsonElement(receive<JsonElement>())
        is Schema.Record<*> -> (schema as Schema<A>).decodeFromJsonElement(receive<JsonElement>())
        is Schema.Union<*> -> (schema as Schema<A>).decodeFromJsonElement(receive<JsonElement>())
    }

@Suppress("UNCHECKED_CAST")
private suspend fun <A> ApplicationCall.respondSchema(status: HttpStatusCode, schema: Schema<A>, value: A) {
    when (schema) {
        is Schema.Default -> TODO()
        is Schema.Optional<*> -> TODO()
        is Schema.Transform<*, *> -> TODO()
        is Schema.OrElse<A, *> -> respond(status, schema.encodeToJsonElement(value))
        is Schema.Collection<*> -> respond(status, (schema as Schema<Any?>).encodeToJsonElement(value))
        is Schema.StringMap<*> -> respond(status, (schema as Schema<Any?>).encodeToJsonElement(value))
        is Schema.Record -> respond(status, schema.encodeToJsonElement(value))
        is Schema.Union -> respond(status, schema.encodeToJsonElement(value))
        is Schema.Bytes -> respondBytes(value as ByteArray, null, status)
        is Schema.Empty -> respond(status)
        is Schema.Lazy -> respondSchema(status, with(schema) { schema() }, value)
        is Schema.Metadata -> respondSchema(status, schema.schema, value)
        is Schema.Primitive -> respondText(value.toString(), status = status)
    }
}

data class SchemaError(
    val message: String
) {
    companion object {
        val schema = Schema.string().transform({ SchemaError(it) }) { it.message }
    }
}
