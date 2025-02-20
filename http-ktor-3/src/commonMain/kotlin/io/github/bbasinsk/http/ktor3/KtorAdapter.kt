package io.github.bbasinsk.http.ktor3

import io.github.bbasinsk.http.ContentType
import io.github.bbasinsk.http.Http
import io.github.bbasinsk.http.HttpEndpoint
import io.github.bbasinsk.http.HttpMethod
import io.github.bbasinsk.http.ParamsSchema
import io.github.bbasinsk.http.PathSchema
import io.github.bbasinsk.http.Request
import io.github.bbasinsk.http.BodySchema
import io.github.bbasinsk.http.Response
import io.github.bbasinsk.http.ResponseSchema
import io.github.bbasinsk.http.parseCatching
import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.avro.BinaryDeserialization.deserializeIgnoringSchemaId
import io.github.bbasinsk.schema.avro.BinarySerialization.serialize
import io.github.bbasinsk.schema.decodePrimitiveString
import io.github.bbasinsk.schema.encodeString
import io.github.bbasinsk.schema.json.InvalidJson
import io.github.bbasinsk.schema.json.kotlinx.decodeFromJsonElement
import io.github.bbasinsk.schema.json.kotlinx.encodeToJsonElement
import io.github.bbasinsk.schema.transform
import io.github.bbasinsk.validation.Validation
import io.github.bbasinsk.validation.andThen
import io.github.bbasinsk.validation.getOrElse
import io.github.bbasinsk.validation.mapInvalid
import io.github.bbasinsk.validation.orElse
import io.ktor.http.HttpStatusCode
import io.ktor.http.HttpStatusCode.Companion.UnprocessableEntity
import io.ktor.server.request.httpMethod
import io.ktor.server.request.path
import io.ktor.server.request.receive
import io.ktor.server.request.receiveText
import io.ktor.server.response.respond
import io.ktor.server.response.respondBytes
import io.ktor.server.response.respondText
import io.ktor.server.routing.HttpMethodRouteSelector
import io.ktor.server.routing.PathSegmentConstantRouteSelector
import io.ktor.server.routing.PathSegmentParameterRouteSelector
import io.ktor.server.routing.Route
import io.ktor.server.routing.RouteSelector
import io.ktor.server.routing.RoutingCall
import io.ktor.server.routing.RoutingHandler
import io.ktor.server.routing.RoutingNode
import io.ktor.util.flattenEntries
import kotlinx.serialization.json.JsonElement

fun <Path, Input, Error, Output> RoutingNode.httpEndpointToRoute(
    endpoint: HttpEndpoint<Path, Input, Error, Output, RoutingCall>
): Route =
    endpoint.api.toRoute(this)
        .withChildren(httpRoutingHandler(endpoint))

private fun RoutingNode.withChildren(handler: RoutingHandler): Route =
    parent?.withChildren(handler)?.createChild(selector)?.also { it.handle(handler) } ?: this

private fun <Params, Input, Error, Output> Http<Params, Input, Error, Output>.toRoute(parent: RoutingNode): RoutingNode =
    RoutingNode(
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

private fun ParamsSchema<*>.toRoute(parent: RoutingNode): RoutingNode =
    pathSchemas().mapNotNull { it.toSelector() }.fold(parent) { route, selector ->
        RoutingNode(
            parent = route,
            selector = selector,
            environment = parent.environment,
            developmentMode = parent.developmentMode
        )
    }

private fun <Path, Input, Error, Output> httpRoutingHandler(
    endpoint: HttpEndpoint<Path, Input, Error, Output, RoutingCall>
): RoutingHandler = interceptor@{
    if (call.request.httpMethod != endpoint.api.method.toKtorMethod()) {
        return@interceptor call.respond(HttpStatusCode.MethodNotAllowed)
    }

    val rawPath = call.request.path().split("/").filter { it.isNotBlank() }
    val headers = call.request.headers.flattenEntries().toMap()
    val query = call.request.queryParameters.flattenEntries().toMap()
    val path: Path = endpoint.api.params.parseCatching(rawPath.toMutableList(), headers, query).getOrThrow()

    val input = call.receiveRequest(endpoint.api.input).getOrElse { errors ->
        return@interceptor call.respondJson(UnprocessableEntity, Schema.list(SchemaError.schema), errors)
    }

    val response = with(endpoint) {
        Response.handle(Request(path, input, call))
    }

    return@interceptor when (response) {
        is Response.Error -> call.respondSchema(endpoint.api.error, response.value)
        is Response.Success -> call.respondSchema(endpoint.api.output, response.value)
    }
}

private suspend fun <A> RoutingCall.respondSchema(response: ResponseSchema<A>, value: A) =
    respondSchema(HttpStatusCode.fromValue(response.getStatus(value).code), response.getSchema(value), value)

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

private suspend fun <A> RoutingCall.receiveRequest(request: BodySchema<A>): Validation<SchemaError, A> =
    when (request) {
        is BodySchema.WithMetadata -> receiveRequest(request.schema)
        is BodySchema.Single -> when (request.contentType) {
            ContentType.Avro -> receiveAvro(request.schema())
            ContentType.Json -> receiveJson(request.schema()).mapInvalid { SchemaError(it.message()) }
            ContentType.Plain -> Validation.fromResult(request.schema().decodePrimitiveString(receiveText())) {
                SchemaError(it.message ?: "Error decoding")
            }
        }
    }

private suspend fun <A> RoutingCall.receiveAvro(schema: Schema<A>): Validation<SchemaError, A> =
    Validation.runCatching { receive<ByteArray>() }
        .mapInvalid { SchemaError(it.toString()) }
        .andThen { bytes -> schema.deserializeIgnoringSchemaId(bytes).mapInvalid { SchemaError(it.reason()) } }

@Suppress("UNCHECKED_CAST")
private suspend fun <A> RoutingCall.receiveJson(schema: Schema<A>): Validation<InvalidJson, A> =
    when (schema) {
        is Schema.Optional<*> -> receiveJson(schema.schema).orElse { Validation.valid(null) } as Validation<InvalidJson, A>
        is Schema.Transform<A, *> -> receiveJson((schema as Schema.Transform<A, Any?>)).andThen {
            Validation.fromResult(schema.decode(it)) { _ ->
                InvalidJson(expected = schema.metadata.name, found = it.toString(), path = emptyList())
            }
        }

        is Schema.Default -> receiveJson(schema.schema).orElse { Validation.valid(schema.default) }
        is Schema.OrElse -> receiveJson(schema.preferred).orElse { receiveJson(schema.fallback) }
        is Schema.Bytes -> Validation.valid(receive<ByteArray>() as A)
        is Schema.Empty -> Validation.valid(null as A)
        is Schema.Lazy -> receiveJson(with(schema) { schema() })
        is Schema.Primitive -> receiveText().let { raw ->
            Validation.fromResult(schema.decodePrimitiveString(raw)) {
                InvalidJson(expected = schema.name, found = raw, path = emptyList())
            }
        }

        is Schema.Record -> schema.decodeFromJsonElement(receive<JsonElement>())
        is Schema.Union -> schema.decodeFromJsonElement(receive<JsonElement>())
        is Schema.Collection<*> -> (schema as Schema<A>).decodeFromJsonElement(receive<JsonElement>())
        is Schema.StringMap<*> -> (schema as Schema<A>).decodeFromJsonElement(receive<JsonElement>())
    }

private suspend fun <A> RoutingCall.respondSchema(status: HttpStatusCode, schema: BodySchema<A>, value: A) {
    when (schema) {
        is BodySchema.WithMetadata -> respondSchema(status, schema.schema, value)
        is BodySchema.Single -> when (schema.contentType) {
            ContentType.Json -> respondJson(status, schema.schema(), value)
            ContentType.Avro -> respondAvro(status, schema.schema(), value)
            ContentType.Plain -> respondPlain(status, schema.schema(), value)
        }
    }
}

private suspend fun <A> RoutingCall.respondJson(status: HttpStatusCode, schema: Schema<A>, value: A) {
    when (schema) {
        is Schema.Default -> TODO()
        is Schema.Optional<*> -> TODO()
        is Schema.Transform<*, *> -> TODO()
        is Schema.OrElse<*> -> TODO()
        is Schema.Collection<*> -> respond(status, (schema as Schema<Any?>).encodeToJsonElement(value))
        is Schema.StringMap<*> -> respond(status, (schema as Schema<Any?>).encodeToJsonElement(value))
        is Schema.Record -> respond(status, schema.encodeToJsonElement(value))
        is Schema.Union -> respond(status, schema.encodeToJsonElement(value))
        is Schema.Bytes -> respondBytes(value as ByteArray, null, status)
        is Schema.Empty -> respond(status)
        is Schema.Lazy -> respondJson(status, with(schema) { schema() }, value)
        is Schema.Primitive -> respondText(value.toString(), status = status)
    }
}

private suspend fun <A> RoutingCall.respondAvro(status: HttpStatusCode, schema: Schema<A>, value: A) {
    when (schema) {
        is Schema.Default -> TODO()
        is Schema.Optional<*> -> TODO()
        is Schema.Transform<*, *> -> TODO()
        is Schema.OrElse<*> -> TODO()
        is Schema.Collection<*> -> respond(status, (schema as Schema<Any?>).encodeToJsonElement(value))
        is Schema.StringMap<*> -> respond(status, (schema as Schema<Any?>).encodeToJsonElement(value))
        is Schema.Record -> respond(status, schema.serialize(1, value)!!)
        is Schema.Union -> respond(status, schema.encodeToJsonElement(value))
        is Schema.Bytes -> respondBytes(value as ByteArray, null, status)
        is Schema.Empty -> respond(status)
        is Schema.Lazy -> respondJson(status, with(schema) { schema() }, value)
        is Schema.Primitive -> respondText(value.toString(), status = status)
    }
}

private suspend fun <A> RoutingCall.respondPlain(status: HttpStatusCode, schema: Schema<A>, value: A) {
    respondText(schema.encodeString(value).getOrThrow(), io.ktor.http.ContentType.Text.Plain, status = status)
}


data class SchemaError(
    val message: String
) {
    companion object {
        val schema = Schema.string().transform({ SchemaError(it) }) { it.message }
    }
}
