package io.github.bbasinsk.http.ktor3

import io.github.bbasinsk.http.AuthHandler
import io.github.bbasinsk.http.AuthResult
import io.github.bbasinsk.http.AuthSchema
import io.github.bbasinsk.http.BodySchema
import io.github.bbasinsk.http.ContentType
import io.github.bbasinsk.http.Http
import io.github.bbasinsk.http.HttpEndpoint
import io.github.bbasinsk.http.HttpMethod
import io.github.bbasinsk.http.ParamsSchema
import io.github.bbasinsk.http.PathParam
import io.github.bbasinsk.http.PathSegment
import io.github.bbasinsk.http.Request
import io.github.bbasinsk.http.parseCatching
import io.github.bbasinsk.http.Response
import io.github.bbasinsk.http.ResponseSchema
import io.github.bbasinsk.http.SSEEvent
import io.github.bbasinsk.schema.*
import io.github.bbasinsk.schema.avro.BinaryDeserialization.deserializeIgnoringSchemaId
import io.github.bbasinsk.schema.avro.BinarySerialization.serialize
import io.github.bbasinsk.schema.json.InvalidJson
import io.github.bbasinsk.schema.json.kotlinx.decodeFromJsonElement
import io.github.bbasinsk.schema.json.kotlinx.decodeFromJsonString
import io.github.bbasinsk.schema.json.kotlinx.encodeToJsonBytes
import io.github.bbasinsk.schema.json.kotlinx.encodeToJsonString
import io.github.bbasinsk.validation.*
import io.ktor.http.*
import io.ktor.http.HttpStatusCode.Companion.UnprocessableEntity
import io.ktor.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.utils.io.*
import io.ktor.utils.io.core.*
import io.ktor.utils.io.jvm.javaio.*
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.io.readByteArray
import kotlinx.serialization.json.JsonElement
import java.io.Writer

fun <Path, Input, Error, Output, Auth> RoutingNode.httpEndpointToRoute(
    endpoint: HttpEndpoint<Path, Input, Error, Output, Auth, RoutingCall>
): Route =
    endpoint.api.toRoute(this)
        .withChildren(httpRoutingHandler(endpoint))

private fun RoutingNode.withChildren(handler: RoutingHandler): Route =
    parent?.withChildren(handler)?.createChild(selector)?.also { it.handle(handler) } ?: this

private fun <Params, Input, Error, Output, Auth> Http<Params, Input, Error, Output, Auth>.toRoute(parent: RoutingNode): RoutingNode =
    RoutingNode(
        parent = params.toRoute(parent),
        selector = HttpMethodRouteSelector(io.ktor.http.HttpMethod.parse(method.name)),
        environment = parent.environment,
        developmentMode = parent.developmentMode
    )

private fun ParamsSchema<*>.toSelector(): RouteSelector? =
    when (this) {
        is PathParam -> PathSegmentParameterRouteSelector(param.name())
        is PathSegment -> PathSegmentConstantRouteSelector(name)
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

private fun <Path, Input, Error, Output, Auth> httpRoutingHandler(
    endpoint: HttpEndpoint<Path, Input, Error, Output, Auth, RoutingCall>
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

    val auth: Auth = when (val result = handleAuth(endpoint.api.auth, endpoint.authHandler, call.request.headers, call.request.cookies, query)) {
        is AuthResult.Success -> result.principal
        is AuthResult.Unauthorized -> return@interceptor call.respond(HttpStatusCode.Unauthorized)
        is AuthResult.Redirect -> return@interceptor call.respondRedirect(result.location)
    }

    val input = call.receiveRequest(endpoint.api.input).getOrElse { errors ->
        errors.onEach { call.application.environment.log.warn(it.message) }
        return@interceptor call.respondJson(UnprocessableEntity, Schema.list(SchemaError.schema), errors)
    }

    return@interceptor try {
        val response = with(endpoint) {
            Response.handle(Request(path, input, auth, call))
        }

        when (response) {
            is Response.CompletionError -> call.respondSchema(endpoint.api.error, response.value)
            is Response.CompletionSuccess -> call.respondSchema(endpoint.api.output, response.value)
            is Response.StreamingError -> {
                val sseSchema = endpoint.api.error.findEventStream() ?: error(
                    "Handler returned StreamingError but error schema has no SSE variant. " +
                            "Add sse { ... } to your error schema, or use oneOf(..., sse { ... })."
                )
                call.respondSSE(sseSchema.bodySchema, response.events)
            }

            is Response.StreamingSuccess -> {
                val sseSchema = endpoint.api.output.findEventStream() ?: error(
                    "Handler returned StreamingSuccess but output schema has no SSE variant. " +
                            "Add sse { ... } to your output schema, or use oneOf(..., sse { ... })."
                )
                call.respondSSE(sseSchema.bodySchema, response.events)
            }
        }
    } catch (e: CancellationException) {
        call.application.environment.log.info("Request was cancelled")
    } catch (e: Exception) {
        call.application.environment.log.error("Unhandled exception in endpoint handler", e)
        call.respondText(
            text = "Internal Server Error",
            contentType = io.ktor.http.ContentType.Text.Plain,
            status = HttpStatusCode.InternalServerError
        )
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

@Suppress("UNCHECKED_CAST")
private suspend fun <A> RoutingCall.receiveRequest(request: BodySchema<A>): Validation<SchemaError, A> =
    when (request) {
        is BodySchema.WithMetadata -> receiveRequest(request.schema)
        is BodySchema.Single -> when (request.contentType) {
            is ContentType.Image -> Validation.valid(receive<ByteArray>()) as Validation<SchemaError, A>
            ContentType.Avro -> receiveAvro(request.schema())
            ContentType.Json -> receiveJson(request.schema()).mapInvalid { SchemaError(it.reason()) }
            ContentType.MultipartFormData -> Validation.requireNotNull(request.schema() as? Schema.Record<A>) {
                SchemaError("MultipartFormData must be Schema.Record, found ${request.schema()::class.simpleName}")
            }.andThen {
                receiveMultipart(it)
            }

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

            ContentType.EventStream -> Validation.invalid(
                SchemaError("EventStream cannot be used as request content type")
            )
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
        is Schema.Transform<A, *> -> receiveJson(schema.schema).andThen { b ->
            Validation.fromResult(schema.unsafeDecode(b)) { e ->
                InvalidJson.FieldError(expected = schema.metadata.name, found = b.toString(), path = emptyList())
            }
        }

        is Schema.Default -> receiveJson(schema.schema).orElse { Validation.valid(schema.default) }
        is Schema.OrElse<A, *> -> receiveJson(schema.preferred).orElse { preferredErrors ->
            receiveJson(schema.fallback).andThen { b ->
                Validation.fromResult(schema.unsafeDecode(b)) {
                    InvalidJson.FieldError(expected = it.message ?: "unable to decode", found = b.toString(), path = emptyList())
                }
            }.orElse { fallbackErrors ->
                Validation.invalid(InvalidJson.Or(preferredErrors, fallbackErrors))
            }
        }

        is Schema.Bytes -> Validation.valid(receive<ByteArray>() as A)
        is Schema.Empty -> Validation.valid(null as A)
        is Schema.Lazy -> receiveJson(with(schema) { schema() })
        is Schema.Metadata -> receiveJson(schema.schema)
        is Schema.Primitive -> receiveJsonElement().andThen { schema.decodeFromJsonElement(it) }

        is Schema.Record -> receiveJsonElement().andThen { schema.decodeFromJsonElement(it) }
        is Schema.Union -> receiveJsonElement().andThen { schema.decodeFromJsonElement(it) }
        is Schema.Collection<*> -> receiveJsonElement().andThen { (schema as Schema<A>).decodeFromJsonElement(it) }
        is Schema.StringMap<*> -> receiveJsonElement().andThen { (schema as Schema<A>).decodeFromJsonElement(it) }
    }

private suspend fun RoutingCall.receiveJsonElement(): Validation<InvalidJson, JsonElement> =
    Validation.runCatching { receive<JsonElement>() }
        .mapInvalid { InvalidJson.FieldError(expected = "JsonElement", found = it.toString(), path = emptyList()) }

private suspend fun <A> RoutingCall.receiveMultipart(schema: Schema.Record<A>): Validation<SchemaError, A> {
    val schemaFields = schema.unsafeFields()
    val partValues = buildMap<Field<A, *>, Any?> {
        receiveMultipart().forEachPart { part ->
            val field = schemaFields.find { it.name == part.name } ?: return@forEachPart
            when (val fieldSchema = field.schema) {
                is Schema.Collection<*> -> {
                    val prev = get(field) as? List<Any?> ?: emptyList()
                    part.receivePart(fieldSchema.itemSchema).mapValid { put(field, prev.plus(it)) }
                }

                else -> part.receivePart(fieldSchema).mapValid { put(field, it) }
            }
        }
    }
    return Validation.valid(schema.unsafeConstruct(schemaFields.map { partValues[it] }))
}

@Suppress("UNCHECKED_CAST")
private suspend fun <A> RoutingCall.receiveFormUrlEncoded(schema: Schema.Record<A>): Validation<SchemaError, A> {
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

            else -> fieldSchema.decodePrimitiveString(values?.firstOrNull()).getOrElse { e ->
                return Validation.invalid(SchemaError("Error decoding field '${field.name}': ${e.message}"))
            }
        }
    }

    return Validation.valid(schema.unsafeConstruct(fieldValues))
}

@Suppress("UNCHECKED_CAST")
private fun <A> PartData?.receivePart(schema: Schema<A>): Validation<String, A> {
    return when (schema) {
        is Schema.Bytes -> {
            when (this) {
                is PartData.FileItem -> Validation.valid(provider().toInputStream().readBytes()) as Validation<String, A>
                is PartData.BinaryItem -> Validation.valid(provider().readByteArray()) as Validation<String, A>
                is PartData.BinaryChannelItem -> Validation.valid(provider().toInputStream().readBytes()) as Validation<String, A>
                is PartData.FormItem -> Validation.invalid("Found part of type '${this::class.simpleName}', expected FileItem or BinaryItem for Bytes schema")
                null -> Validation.invalid("Missing required part for schema")
            }
        }

        is Schema.Default -> receivePart(schema.schema)
        is Schema.Empty -> Validation.valid(null as A)
        is Schema.Lazy -> receivePart(schema.schema())
        is Schema.Metadata -> receivePart(schema.schema)
        is Schema.Primitive -> when (this) {
            is PartData.FormItem -> Validation.fromResult(schema.decodePrimitiveString(value)) { e -> e.message ?: "Error decoding part value" }
            null -> Validation.invalid("Missing required part for schema")
            else -> Validation.invalid("Found part of type '${this::class.simpleName}', expected FormItem for primitive schema")
        }

        is Schema.Union, is Schema.Record, is Schema.StringMap<*> -> when (this) {
            is PartData.FormItem -> schema.decodeFromJsonString(value).mapInvalid { it.reason() }
            null -> Validation.invalid("Missing required part for schema")
            else -> Validation.invalid("Found part of type '${this::class.simpleName}', expected FormItem for primitive schema")
        }

        is Schema.Collection<*> -> when (this) {
            is PartData.FormItem -> TODO()
            null -> Validation.invalid("Missing required part for schema")
            else -> Validation.invalid("Found part of type '${this::class.simpleName}', expected FormItem for primitive schema")
        }

        is Schema.Optional<*> -> when {
            isNullOrEmpty() -> Validation.valid(null as A)
            else -> this.receivePart(schema.schema) as Validation<String, A>
        }

        is Schema.OrElse<A, *> -> receivePart(schema.preferred)
        is Schema.Transform<A, *> -> receivePart(schema.schema).andThen {
            Validation.fromResult(schema.unsafeDecode(it)) { e ->
                e.message ?: "Error decoding part value"
            }
        }
    }
}

private fun PartData?.isNullOrEmpty(): Boolean =
    when (this) {
        null -> true
        is PartData.FileItem -> provider().availableForRead == 0
        is PartData.BinaryItem -> provider().remaining == 0L
        is PartData.BinaryChannelItem -> provider().availableForRead == 0
        is PartData.FormItem -> value.isEmpty()
    }

private suspend fun <A> RoutingCall.respondSchema(status: HttpStatusCode, schema: BodySchema<A>, value: A) {
    when (schema) {
        is BodySchema.WithMetadata -> respondSchema(status, schema.schema, value)
        is BodySchema.Single -> when (schema.contentType) {
            is ContentType.Image -> respondBytes(contentType = schema.contentType.toKtorContentType(), status = status, bytes = value as ByteArray)
            ContentType.Json -> respondJson(status, schema.schema(), value)
            ContentType.Avro -> respondAvro(status, schema.schema(), value)
            ContentType.Plain -> respondText(schema.schema.encodePrimitiveString(value).getOrThrow(), schema.contentType.toKtorContentType(), status)
            ContentType.Html -> respondText(schema.schema.encodePrimitiveString(value).getOrThrow(), schema.contentType.toKtorContentType(), status)
            ContentType.MultipartFormData -> error("MultipartFormData is not supported as response type")
            ContentType.FormUrlEncoded -> error("FormUrlEncoded is not supported as response type")
            ContentType.EventStream -> error("EventStream responses should use Response.StreamingSuccess/StreamingError")
        }
    }
}

private fun ContentType.toKtorContentType(): io.ktor.http.ContentType =
    io.ktor.http.ContentType(this.contentType, this.contentSubtype)

private suspend fun <A> RoutingCall.respondJson(status: HttpStatusCode, schema: Schema<A>, value: A) {
    respondBytes(schema.encodeToJsonBytes(value), io.ktor.http.ContentType.Application.Json, status)
}

private suspend fun <A> RoutingCall.respondAvro(status: HttpStatusCode, schema: Schema<A>, value: A) =
    when (val bytes = schema.serialize(1, value)) {
        null -> respond(status)
        else -> respondBytes(bytes, io.ktor.http.ContentType.Application.OctetStream, status)
    }

data class SchemaError(
    val message: String
) {
    companion object {
        val schema = Schema.string().transform({ SchemaError(it) }) { it.message }
    }
}

/**
 * Responds with a Server-Sent Events (SSE) stream.
 * Includes error handling - if the flow throws, an error event is sent before closing.
 * Gracefully handles client disconnections without logging errors.
 */
private suspend fun <A> RoutingCall.respondSSE(bodySchema: BodySchema<A>, events: Flow<SSEEvent<A>>) {
    try {
        respondTextWriter(contentType = io.ktor.http.ContentType.Text.EventStream) {
            try {
                events.collect { event ->
                    writeSSEEvent(bodySchema, event)
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                if (e.isChannelClosedException()) return@respondTextWriter

                appendLine("event: error")
                appendLine("data: An error occurred")
                appendLine()
                flush()
                application.environment.log.error("An error occurred writing an SSE event", e)
            }
        }
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        if (e.isChannelClosedException()) {
            application.environment.log.debug("SSE client disconnected")
            return
        }
        throw e
    }
}

/**
 * Checks if the exception (or its cause chain) indicates a closed channel,
 * which typically means the client disconnected.
 */
internal expect fun Throwable.isChannelClosedException(): Boolean

/**
 * Writes a single SSE event to the response writer.
 * Handles multi-line data by prefixing each line with "data: " as per SSE spec.
 */
private fun <A> Writer.writeSSEEvent(bodySchema: BodySchema<A>, event: SSEEvent<A>) {
    event.comment?.let { appendLine(": $it") }
    event.id?.let { appendLine("id: $it") }
    event.event?.let { appendLine("event: $it") }
    event.retry?.let { appendLine("retry: $it") }
    val data = event.data
    when {
        data != null -> {
            val serialized = serializeEventData(bodySchema, data)
            // SSE protocol requires each line of data to have "data: " prefix
            serialized.lines().forEach { line ->
                appendLine("data: $line")
            }
        }
        event.comment != null -> appendLine("data:") // Empty data for keepalive to trigger client event
    }
    appendLine()
    flush()
}

/**
 * Serializes event data according to the body schema.
 */
@Suppress("UNCHECKED_CAST")
private fun <A> serializeEventData(bodySchema: BodySchema<A>, data: A): String {
    return when (bodySchema) {
        is BodySchema.WithMetadata -> serializeEventData(bodySchema.schema, data)
        is BodySchema.Single -> when (bodySchema.contentType) {
            ContentType.Json -> bodySchema.schema().encodeToJsonString(data)
            ContentType.Plain -> bodySchema.schema().encodePrimitiveString(data).getOrThrow()
            ContentType.Html -> bodySchema.schema().encodePrimitiveString(data).getOrThrow()
            ContentType.Avro -> error("Avro serialization is not supported for SSE")
            is ContentType.Image -> error("Image content type is not supported for SSE")
            ContentType.MultipartFormData -> error("MultipartFormData is not supported for SSE")
            ContentType.FormUrlEncoded -> error("FormUrlEncoded is not supported for SSE")
            ContentType.EventStream -> error("EventStream content type should not be used for event data serialization")
        }
    }
}

/**
 * Handles authentication based on the auth schema and handler.
 */
@Suppress("UNCHECKED_CAST")
private suspend fun <A> handleAuth(
    schema: AuthSchema<A>,
    authHandler: AuthHandler<*>?,
    headers: io.ktor.http.Headers,
    cookies: RequestCookies,
    queryParams: Map<String, List<String>>
): AuthResult<A> = when (schema) {
    is AuthSchema.None -> AuthResult.Success(Unit as A)

    is AuthSchema.Bearer -> {
        val token = extractBearerToken(headers)
        invokeHandler(authHandler, token)
    }

    is AuthSchema.Basic -> {
        val credentials = extractBasicCredentials(headers)
        invokeHandler(authHandler, credentials)
    }

    is AuthSchema.ApiKeyHeader -> {
        val key = headers[schema.headerName]
        invokeHandler(authHandler, key)
    }

    is AuthSchema.Cookie -> {
        val value = cookies[schema.cookieName]
        invokeHandler(authHandler, value)
    }

    is AuthSchema.Optional<*> -> {
        // For optional auth, any failure (Unauthorized or Redirect) is treated as unauthenticated (null).
        // This allows pages to serve public content while optionally personalizing for logged-in users.
        val innerResult = handleAuth(schema.inner, authHandler, headers, cookies, queryParams)
        AuthResult.Success((innerResult as? AuthResult.Success)?.principal as A)
    }
}

@Suppress("UNCHECKED_CAST")
private suspend fun <A> invokeHandler(handler: AuthHandler<*>?, token: String?): AuthResult<A> {
    val typedHandler = handler as? AuthHandler<A> ?: return AuthResult.Unauthorized
    return typedHandler.handle(token)
}

private fun extractBearerToken(headers: io.ktor.http.Headers): String? =
    headers["Authorization"]
        ?.takeIf { it.startsWith("Bearer ", ignoreCase = true) }
        ?.substring(7)

private fun extractBasicCredentials(headers: io.ktor.http.Headers): String? =
    headers["Authorization"]
        ?.takeIf { it.startsWith("Basic ", ignoreCase = true) }
        ?.substring(6)
