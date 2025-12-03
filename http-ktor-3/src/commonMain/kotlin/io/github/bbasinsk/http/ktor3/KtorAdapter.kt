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
import io.github.bbasinsk.schema.Field
import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.avro.BinaryDeserialization.deserializeIgnoringSchemaId
import io.github.bbasinsk.schema.avro.BinarySerialization.serialize
import io.github.bbasinsk.schema.decodePrimitiveString
import io.github.bbasinsk.schema.encodePrimitiveString
import io.github.bbasinsk.schema.json.InvalidJson
import io.github.bbasinsk.schema.json.kotlinx.decodeFromJsonElement
import io.github.bbasinsk.schema.json.kotlinx.decodeFromJsonString
import io.github.bbasinsk.schema.json.kotlinx.encodeToJsonElement
import io.github.bbasinsk.schema.transform
import io.github.bbasinsk.validation.Validation
import io.github.bbasinsk.validation.andThen
import io.github.bbasinsk.validation.getOrElse
import io.github.bbasinsk.validation.mapInvalid
import io.github.bbasinsk.validation.mapValid
import io.github.bbasinsk.validation.orElse
import io.ktor.http.HttpStatusCode
import io.ktor.http.HttpStatusCode.Companion.UnprocessableEntity
import io.ktor.http.content.PartData
import io.ktor.http.content.forEachPart
import io.ktor.server.request.httpMethod
import io.ktor.server.request.path
import io.ktor.server.request.receive
import io.ktor.server.request.receiveMultipart
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
import io.ktor.server.routing.RoutingCall
import io.ktor.server.routing.RoutingHandler
import io.ktor.server.routing.RoutingNode
import io.ktor.http.decodeURLPart
import io.ktor.utils.io.availableForRead
import io.ktor.utils.io.core.remaining
import io.ktor.utils.io.jvm.javaio.toInputStream
import kotlinx.io.readByteArray
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

    val rawPath = call.request.path()
        .split("/")
        .filter { it.isNotBlank() }
        .map { it.decodeURLPart() }
    val headers = call.request.headers.entries().associate { it.key to it.value }
    val query = call.request.queryParameters.entries().associate { it.key to it.value }
    val path: Path = endpoint.api.params.parseCatching(rawPath.toMutableList(), headers, query).getOrThrow()

    val input = call.receiveRequest(endpoint.api.input).getOrElse { errors ->
        errors.onEach { call.application.environment.log.warn(it.message) }
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
                InvalidJson.FieldError(expected = schema.metadata.name, found = it.toString(), path = emptyList())
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
        is Schema.Primitive -> receiveText().let { raw ->
            Validation.fromResult(schema.decodePrimitiveString(raw)) {
                InvalidJson.FieldError(expected = schema.name, found = raw, path = emptyList())
            }
        }

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
        }
    }
}

private fun ContentType.toKtorContentType(): io.ktor.http.ContentType =
    io.ktor.http.ContentType(this.contentType, this.contentSubtype)

@Suppress("UNCHECKED_CAST")
private suspend fun <A> RoutingCall.respondJson(status: HttpStatusCode, schema: Schema<A>, value: A) {
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
        is Schema.Lazy -> respondJson(status, with(schema) { schema() }, value)
        is Schema.Metadata -> respondJson(status, schema.schema, value)
        is Schema.Primitive -> respond(status, schema.encodeToJsonElement(value))
    }
}

private suspend fun <A> RoutingCall.respondAvro(status: HttpStatusCode, schema: Schema<A>, value: A) {
    when (schema) {
        is Schema.Default -> TODO()
        is Schema.Optional<*> -> TODO()
        is Schema.Transform<*, *> -> TODO()
        is Schema.OrElse<A, *> -> respond(status, schema.serialize(1, value)!!)
        is Schema.Collection<*> -> respond(status, (schema as Schema<A>).serialize(1, value)!!)
        is Schema.StringMap<*> -> respond(status, (schema as Schema<A>).serialize(1, value)!!)
        is Schema.Record -> respond(status, schema.serialize(1, value)!!)
        is Schema.Union -> respond(status, schema.serialize(1, value)!!)
        is Schema.Bytes -> respondBytes(value as ByteArray, null, status)
        is Schema.Empty -> respond(status)
        is Schema.Lazy -> respondJson(status, with(schema) { schema() }, value)
        is Schema.Metadata -> respondJson(status, schema.schema, value)
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
