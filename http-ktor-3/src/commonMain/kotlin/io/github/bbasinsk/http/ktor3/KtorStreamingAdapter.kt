package io.github.bbasinsk.http.ktor3

import io.github.bbasinsk.http.BodySchema
import io.github.bbasinsk.http.ContentType
import io.github.bbasinsk.http.HttpMethod
import io.github.bbasinsk.http.ParamsSchema
import io.github.bbasinsk.http.PathSchema
import io.github.bbasinsk.http.Request
import io.github.bbasinsk.http.SSEEvent
import io.github.bbasinsk.http.StreamingHttp
import io.github.bbasinsk.http.StreamingHttpEndpoint
import io.github.bbasinsk.http.StreamingResponse
import io.github.bbasinsk.http.parseCatching
import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.encodePrimitiveString
import io.github.bbasinsk.schema.json.kotlinx.encodeToJsonElement
import io.github.bbasinsk.validation.getOrElse
import io.github.bbasinsk.validation.getOrThrow
import io.ktor.http.HttpStatusCode
import io.ktor.http.decodeURLPart
import io.ktor.server.request.httpMethod
import io.ktor.server.request.path
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.RouteSelector
import io.ktor.server.routing.HttpMethodRouteSelector
import io.ktor.server.routing.PathSegmentConstantRouteSelector
import io.ktor.server.routing.PathSegmentParameterRouteSelector
import io.ktor.server.routing.RoutingCall
import io.ktor.server.routing.RoutingHandler
import io.ktor.server.routing.RoutingNode
import io.ktor.server.sse.ServerSSESession
import io.ktor.server.sse.sse
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.serialization.json.JsonElement

/**
 * Converts a StreamingHttpEndpoint to a Ktor Route with SSE support.
 */
fun <Path, Input, Error, Output> RoutingNode.streamingHttpEndpointToRoute(
    endpoint: StreamingHttpEndpoint<Path, Input, Error, Output, RoutingCall>
): Route =
    endpoint.api.toRoute(this)
        .withChildren(streamingHttpRoutingHandler(endpoint))

private fun RoutingNode.withChildren(handler: RoutingHandler): Route =
    parent?.withChildren(handler)?.createChild(selector)?.also { it.handle(handler) } ?: this

private fun <Params, Input, Error, Output> StreamingHttp<Params, Input, Error, Output>.toRoute(parent: RoutingNode): RoutingNode =
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

private fun <Path, Input, Error, Output> streamingHttpRoutingHandler(
    endpoint: StreamingHttpEndpoint<Path, Input, Error, Output, RoutingCall>
): RoutingHandler = interceptor@{
    if (call.request.httpMethod != endpoint.api.method.toKtorMethod()) {
        return@interceptor call.respond(HttpStatusCode.MethodNotAllowed)
    }

    val rawPath = call.request.path()
        .split("/")
        .filter { it.isNotBlank() }
        .map { it.decodeURLPart() }
    val headers = call.request.headers.entries().associate { it.key to it.value }
    val query = call.request.queryParameters.entries().associate { it.key to it.value }
    val path: Path = endpoint.api.params.parseCatching(rawPath.toMutableList(), headers, query).getOrThrow()

    val input = call.receiveRequest(endpoint.api.input).getOrElse { errors ->
        errors.onEach { call.application.environment.log.warn(it.message) }
        return@interceptor call.respondJson(HttpStatusCode.UnprocessableEntity, Schema.list(SchemaError.schema), errors)
    }

    val streamingResponse = with(endpoint) {
        StreamingResponse.handle(Request(path, input, call))
    }

    return@interceptor when (streamingResponse) {
        is StreamingResponse.Error -> call.respondSSE(endpoint.api.error.bodySchema, streamingResponse.events)
        is StreamingResponse.Success -> call.respondSSE(endpoint.api.output.bodySchema, streamingResponse.events)
    }
}

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

/**
 * Responds with a Server-Sent Events (SSE) stream.
 */
private suspend fun <A> RoutingCall.respondSSE(
    bodySchema: BodySchema<A>,
    events: kotlinx.coroutines.flow.Flow<SSEEvent<A>>
) {
    sse {
        events.onEach { event ->
            sendSSEEvent(bodySchema, event)
        }.collect()
    }
}

/**
 * Sends a single SSE event through the ServerSSESession.
 */
private suspend fun <A> ServerSSESession.sendSSEEvent(
    bodySchema: BodySchema<A>,
    event: SSEEvent<A>
) {
    when {
        // Handle comment-only events (keepalive)
        event.comment != null && event.data == null -> {
            send(io.ktor.sse.ServerSentEvent(
                data = null,
                event = null,
                id = null,
                retry = null,
                comments = event.comment
            ))
        }
        // Handle regular events with data
        else -> {
            val serializedData = serializeEventData(bodySchema, event.data)
            send(io.ktor.sse.ServerSentEvent(
                data = serializedData,
                event = event.event,
                id = event.id,
                retry = event.retry,
                comments = event.comment
            ))
        }
    }
}

/**
 * Serializes event data according to the body schema.
 */
@Suppress("UNCHECKED_CAST")
private fun <A> serializeEventData(bodySchema: BodySchema<A>, data: A): String {
    return when (bodySchema) {
        is BodySchema.WithMetadata -> serializeEventData(bodySchema.schema, data)
        is BodySchema.Single -> when (bodySchema.contentType) {
            ContentType.Json -> serializeToJson(bodySchema.schema(), data)
            ContentType.Plain -> bodySchema.schema().encodePrimitiveString(data).getOrThrow()
            ContentType.Avro -> error("Avro serialization is not supported for SSE")
            is ContentType.Image -> error("Image content type is not supported for SSE")
            ContentType.MultipartFormData -> error("MultipartFormData is not supported for SSE")
            ContentType.EventStream -> error("EventStream content type should not be used for event data serialization")
        }
    }
}

/**
 * Serializes data to JSON string.
 */
@Suppress("UNCHECKED_CAST")
private fun <A> serializeToJson(schema: Schema<A>, value: A): String {
    return when (schema) {
        is Schema.Default -> TODO()
        is Schema.Optional<*> -> TODO()
        is Schema.Transform<*, *> -> TODO()
        is Schema.OrElse<A, *> -> schema.encodeToJsonElement(value).toString()
        is Schema.Collection<*> -> (schema as Schema<Any?>).encodeToJsonElement(value).toString()
        is Schema.StringMap<*> -> (schema as Schema<Any?>).encodeToJsonElement(value).toString()
        is Schema.Record -> schema.encodeToJsonElement(value).toString()
        is Schema.Union -> schema.encodeToJsonElement(value).toString()
        is Schema.Bytes -> error("Bytes schema cannot be serialized to JSON for SSE")
        is Schema.Empty -> ""
        is Schema.Lazy -> serializeToJson(with(schema) { schema() }, value)
        is Schema.Metadata -> serializeToJson(schema.schema, value)
        is Schema.Primitive -> value.toString()
    }
}
