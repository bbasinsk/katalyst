package io.github.bbasinsk.websocket.ktor3

import io.github.bbasinsk.http.*
import io.github.bbasinsk.schema.*
import io.github.bbasinsk.schema.avro.BinaryDeserialization.deserializeIgnoringSchemaId
import io.github.bbasinsk.schema.avro.BinarySerialization.serialize
import io.github.bbasinsk.schema.json.kotlinx.decodeFromJsonString
import io.github.bbasinsk.schema.json.kotlinx.encodeToJsonString
import io.github.bbasinsk.validation.*
import io.github.bbasinsk.websocket.WebSocket
import io.github.bbasinsk.websocket.WebSocketEndpoint
import io.github.bbasinsk.websocket.WebSocketMessage
import io.github.bbasinsk.websocket.WebSocketScope
import io.ktor.http.decodeURLPart
import io.ktor.server.request.path
import io.ktor.server.routing.Route
import io.ktor.server.routing.RoutingCall
import io.ktor.server.websocket.DefaultWebSocketServerSession
import io.ktor.server.websocket.webSocket
import io.ktor.websocket.CloseReason
import io.ktor.websocket.Frame
import io.ktor.websocket.close
import io.ktor.websocket.readBytes
import io.ktor.websocket.readReason
import io.ktor.websocket.readText
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun <Params, In, Out> Route.webSocketEndpointToRoute(
    endpoint: WebSocketEndpoint<Params, In, Out, RoutingCall>
) {
    val path = endpoint.api.params.toFormattedPath()
    webSocket(path) {
        val rawPath = call.request.path()
            .split("/")
            .filter { it.isNotBlank() }
            .map { it.decodeURLPart() }
        val headers = call.request.headers.entries().associate { it.key to it.value }
        val query = call.request.queryParameters.entries().associate { it.key to it.value }
        val params: Params = endpoint.api.params.parseCatching(rawPath.toMutableList(), headers, query).getOrThrow()

        val scope = KtorWebSocketScope(this, endpoint.api.input, endpoint.api.output)
        @Suppress("UNCHECKED_CAST")
        val routingCall = call as RoutingCall
        with(endpoint) {
            scope.handler(params, routingCall)
        }
    }
}

private fun ParamsSchema<*>.toFormattedPath(): String =
    pathSchemas().mapNotNull { schema ->
        when (schema) {
            is PathParam -> "{${schema.param.name()}}"
            is PathSegment -> schema.name
            else -> null
        }
    }.joinToString(separator = "/", prefix = "/")

private class KtorWebSocketScope<In, Out>(
    private val session: DefaultWebSocketServerSession,
    private val inputSchema: BodySchema<In>,
    private val outputSchema: BodySchema<Out>
) : WebSocketScope<In, Out> {

    override val incoming: Flow<WebSocketMessage<In>> = flow {
        try {
            for (frame in session.incoming) {
                deserializeFrame(inputSchema, frame)?.let { emit(it) }
            }
        } catch (_: ClosedReceiveChannelException) {
            // Channel closed, end the flow
        }
    }

    override suspend fun send(message: WebSocketMessage<Out>) {
        when (message) {
            is WebSocketMessage.Text -> {
                val serialized = serializeData(outputSchema, message.data)
                session.send(Frame.Text(serialized))
            }
            is WebSocketMessage.Binary -> {
                val serialized = serializeBinaryData(outputSchema, message.data)
                session.send(Frame.Binary(true, serialized))
            }
            is WebSocketMessage.Close -> {
                session.close(CloseReason(message.code.toShort(), message.reason ?: ""))
            }
        }
    }

    override suspend fun close(code: Int, reason: String?) {
        session.close(CloseReason(code.toShort(), reason ?: ""))
    }

    @Suppress("UNCHECKED_CAST")
    private fun deserializeFrame(schema: BodySchema<In>, frame: Frame): WebSocketMessage<In>? {
        return when (frame) {
            is Frame.Text -> {
                val text = frame.readText()
                val data = deserializeText(schema, text)
                WebSocketMessage.Text(data)
            }
            is Frame.Binary -> {
                val bytes = frame.readBytes()
                val data = deserializeBinary(schema, bytes)
                WebSocketMessage.Binary(data)
            }
            is Frame.Close -> {
                val reason = frame.readReason()
                WebSocketMessage.Close(reason?.code?.toInt() ?: 1000, reason?.message)
            }
            else -> null // Ignore Ping/Pong frames
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun deserializeText(schema: BodySchema<In>, text: String): In {
        return when (schema) {
            is BodySchema.WithMetadata -> deserializeText(schema.schema, text)
            is BodySchema.Single -> when (schema.contentType) {
                ContentType.Json -> schema.schema().decodeFromJsonString(text).getOrElse { errors ->
                    error("Failed to deserialize WebSocket message: ${errors.joinToString { it.reason() }}")
                }
                ContentType.Plain -> schema.schema().decodePrimitiveString(text).getOrElse { e ->
                    error("Failed to deserialize WebSocket message: ${e.message}")
                } as In
                else -> error("Unsupported content type for WebSocket text frame: ${schema.contentType}")
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun deserializeBinary(schema: BodySchema<In>, bytes: ByteArray): In {
        return when (schema) {
            is BodySchema.WithMetadata -> deserializeBinary(schema.schema, bytes)
            is BodySchema.Single -> when (schema.contentType) {
                ContentType.Avro -> schema.schema().deserializeIgnoringSchemaId(bytes).getOrElse { errors ->
                    error("Failed to deserialize WebSocket binary message: ${errors.joinToString { it.reason() }}")
                }
                else -> bytes as In // Assume raw bytes
            }
        }
    }

    private fun serializeData(schema: BodySchema<Out>, data: Out): String {
        return when (schema) {
            is BodySchema.WithMetadata -> serializeData(schema.schema, data)
            is BodySchema.Single -> when (schema.contentType) {
                ContentType.Json -> schema.schema().encodeToJsonString(data)
                ContentType.Plain -> schema.schema().encodePrimitiveString(data).getOrThrow()
                else -> error("Unsupported content type for WebSocket text frame: ${schema.contentType}")
            }
        }
    }

    private fun serializeBinaryData(schema: BodySchema<Out>, data: Out): ByteArray {
        return when (schema) {
            is BodySchema.WithMetadata -> serializeBinaryData(schema.schema, data)
            is BodySchema.Single -> when (schema.contentType) {
                ContentType.Avro -> schema.schema().serialize(1, data) ?: ByteArray(0)
                else -> error("Unsupported content type for WebSocket binary frame: ${schema.contentType}")
            }
        }
    }
}
