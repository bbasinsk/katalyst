package io.github.bbasinsk.http.client.ktor3

import io.github.bbasinsk.http.AuthCredential
import io.github.bbasinsk.http.AuthSchema
import io.github.bbasinsk.http.BodySchema
import io.github.bbasinsk.http.ContentType
import io.github.bbasinsk.http.Http
import io.github.bbasinsk.http.HttpMethod
import io.github.bbasinsk.http.HttpResult
import io.github.bbasinsk.http.SSEEvent
import io.github.bbasinsk.http.render
import io.github.bbasinsk.schema.Field
import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.decodePrimitiveString
import io.github.bbasinsk.schema.encodePrimitiveString
import io.github.bbasinsk.schema.json.decodeFromJsonBytes
import io.github.bbasinsk.schema.json.encodeToJsonBytes
import io.github.bbasinsk.schema.json.encodeToJsonString
import io.github.bbasinsk.validation.fold
import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.forms.FormBuilder
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.http.content.PartData
import io.ktor.client.request.header
import io.ktor.client.request.prepareRequest
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsChannel
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.readRawBytes
import io.ktor.http.contentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.Parameters
import io.ktor.http.ParametersBuilder
import io.ktor.http.appendPathSegments
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.readLine
import kotlin.coroutines.cancellation.CancellationException
import kotlin.jvm.JvmName
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

class KatalystClient(
    val httpClient: HttpClient
) {
    suspend fun <P, I, E, O, A> call(
        endpoint: Http<P, I, E, O, A>,
        params: P,
        input: I,
        auth: AuthCredential? = null
    ): HttpResult<E, O> =
        try {
            val rendered = endpoint.params.render(params)

            val response: HttpResponse = httpClient.request {
                method = endpoint.method.toKtorMethod()
                url { appendPathSegments(rendered.pathSegments) }
                rendered.queryParams.forEach { (name, values) ->
                    values.forEach { value -> url.parameters.append(name, value) }
                }
                rendered.headers.forEach { (name, values) ->
                    values.forEach { value -> header(name, value) }
                }
                applyAuth(endpoint.auth, auth)
                applyBody(endpoint.input, input)
            }

            decodeResponse(endpoint, response)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Throwable) {
            HttpResult.NetworkError(e)
        }

    @JvmName("callNoParamsNoInput")
    suspend fun <E, O, A> call(
        endpoint: Http<Unit, Nothing?, E, O, A>,
        auth: AuthCredential? = null
    ): HttpResult<E, O> = call(endpoint, Unit, null, auth)

    @JvmName("callNoInput")
    suspend fun <P, E, O, A> call(
        endpoint: Http<P, Nothing?, E, O, A>,
        params: P,
        auth: AuthCredential? = null
    ): HttpResult<E, O> = call(endpoint, params, null, auth)

    @JvmName("callNoParams")
    suspend fun <I, E, O, A> call(
        endpoint: Http<Unit, I, E, O, A>,
        input: I,
        auth: AuthCredential? = null
    ): HttpResult<E, O> = call(endpoint, Unit, input, auth)

    /**
     * Opens an SSE stream. Suspends until the server returns response headers,
     * then returns a typed outcome:
     *  - [HttpResult.Failure] — server responded with a declared error status and body
     *  - [HttpResult.NetworkError] — transport, undeclared status, decode failure, or
     *    pre-flight cancellation
     *  - [HttpResult.Success] — the inner [Flow] yields SSE events until the connection
     *    closes. Mid-stream transport failures surface as exceptions on the flow (use
     *    [kotlinx.coroutines.flow.catch]).
     *
     * The returned flow is single-consumer. It **must be collected** (or the enclosing
     * coroutine cancelled) for the connection to be released — if a caller receives
     * [HttpResult.Success] and never subscribes, the request stays open until the caller's
     * coroutine scope is cancelled.
     */
    suspend fun <P, I, E, O, A> stream(
        endpoint: Http<P, I, E, O, A>,
        params: P,
        input: I,
        auth: AuthCredential? = null
    ): HttpResult<E, Flow<SSEEvent<O>>> {
        val rendered = endpoint.params.render(params)
        val events = Channel<SSEEvent<O>>(Channel.BUFFERED)
        val outcome = CompletableDeferred<HttpResult<E, Unit>>()
        val scope = CoroutineScope(currentCoroutineContext())

        val job = scope.launch {
            try {
                httpClient.prepareRequest {
                    method = endpoint.method.toKtorMethod()
                    url { appendPathSegments(rendered.pathSegments) }
                    rendered.queryParams.forEach { (name, values) ->
                        values.forEach { value -> url.parameters.append(name, value) }
                    }
                    rendered.headers.forEach { (name, values) ->
                        values.forEach { value -> header(name, value) }
                    }
                    applyAuth(endpoint.auth, auth)
                    applyBody(endpoint.input, input)
                    header(HttpHeaders.Accept, "text/event-stream")
                    header(HttpHeaders.CacheControl, "no-store")
                }.execute { response ->
                    val statusCode = response.status.value
                    val isEventStream = response.isEventStream()
                    when (val match = matchStatusSchema(endpoint, statusCode, isEventStream)) {
                        is StatusMatch.EventStream<O> -> {
                            outcome.complete(HttpResult.Success(Unit))
                            try {
                                streamEvents(response.bodyAsChannel(), match.schema, events)
                                events.close()
                            } catch (e: CancellationException) {
                                events.close(e)
                                throw e
                            } catch (e: Throwable) {
                                events.close(e)
                            }
                        }

                        is StatusMatch.Error<E> -> {
                            outcome.complete(decodeFailure(match.schema, statusCode, response.readRawBytes()))
                            events.close()
                        }

                        is StatusMatch.Output<O> -> {
                            outcome.complete(HttpResult.NetworkError(
                                IllegalStateException("Stream endpoint returned non-SSE success $statusCode")
                            ))
                            events.close()
                        }

                        StatusMatch.Unmatched -> {
                            val bytes = response.readRawBytes()
                            outcome.complete(HttpResult.NetworkError(
                                IllegalStateException("Unexpected status $statusCode: ${bytes.decodeToString()}")
                            ))
                            events.close()
                        }
                    }
                }
            } catch (e: CancellationException) {
                if (!outcome.isCompleted) outcome.completeExceptionally(e)
                events.close(e)
                throw e
            } catch (e: Throwable) {
                if (!outcome.isCompleted) outcome.complete(HttpResult.NetworkError(e))
                events.close(e)
            }
        }

        val result = try {
            outcome.await()
        } catch (e: Throwable) {
            job.cancel()
            throw e
        }

        return when (result) {
            is HttpResult.Success -> HttpResult.Success(
                flow { events.consumeEach { emit(it) } }
                    .onCompletion { job.cancel() }
            )
            is HttpResult.Failure -> result
            is HttpResult.NetworkError -> result
        }
    }

    @JvmName("streamNoParamsNoInput")
    suspend fun <E, O, A> stream(
        endpoint: Http<Unit, Nothing?, E, O, A>,
        auth: AuthCredential? = null
    ): HttpResult<E, Flow<SSEEvent<O>>> = stream(endpoint, Unit, null, auth)

    @JvmName("streamNoInput")
    suspend fun <P, E, O, A> stream(
        endpoint: Http<P, Nothing?, E, O, A>,
        params: P,
        auth: AuthCredential? = null
    ): HttpResult<E, Flow<SSEEvent<O>>> = stream(endpoint, params, null, auth)

    @JvmName("streamNoParams")
    suspend fun <I, E, O, A> stream(
        endpoint: Http<Unit, I, E, O, A>,
        input: I,
        auth: AuthCredential? = null
    ): HttpResult<E, Flow<SSEEvent<O>>> = stream(endpoint, Unit, input, auth)
}

private fun HttpResponse.isEventStream(): Boolean =
    contentType()?.match(io.ktor.http.ContentType.Text.EventStream) == true

private suspend fun <O> streamEvents(
    channel: ByteReadChannel,
    bodySchema: BodySchema<O>,
    events: SendChannel<SSEEvent<O>>
) {
    var eventType: String? = null
    val dataLines = mutableListOf<String>()
    var eventId: String? = null
    var retry: Long? = null
    var comment: String? = null

    suspend fun flushEvent() {
        if (dataLines.isEmpty() && comment == null) return
        val data = if (dataLines.isNotEmpty()) {
            val raw = dataLines.joinToString("\n")
            if (raw.isBlank() || raw == "null") null else decodeBody(bodySchema, raw.encodeToByteArray())
        } else null

        events.send(
            SSEEvent(
                data = data,
                event = eventType,
                id = eventId,
                retry = retry,
                comment = comment
            )
        )
        eventType = null
        dataLines.clear()
        eventId = null
        retry = null
        comment = null
    }

    while (!channel.isClosedForRead) {
        val line = channel.readLine() ?: break

        when {
            line.isEmpty() -> flushEvent()

            line.startsWith(":") -> {
                comment = line.removePrefix(": ").takeIf { it.isNotBlank() }
                    ?: line.removePrefix(":").takeIf { it.isNotBlank() }
            }

            line.startsWith("event:") -> eventType = line.removePrefix("event:").removePrefix(" ")
            line.startsWith("data:") -> dataLines.add(line.removePrefix("data:").removePrefix(" "))
            line.startsWith("id:") -> eventId = line.removePrefix("id:").removePrefix(" ")
            line.startsWith("retry:") -> retry = line.removePrefix("retry:").removePrefix(" ").toLongOrNull()
        }
    }

    flushEvent()
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

private fun HttpRequestBuilder.applyAuth(schema: AuthSchema<*>, credential: AuthCredential?) {
    if (credential == null) return
    when (schema) {
        is AuthSchema.None -> {}
        is AuthSchema.Bearer<*> -> when (credential) {
            is AuthCredential.BearerToken -> header("Authorization", "Bearer ${credential.token}")
            else -> error("Expected BearerToken credential for Bearer auth")
        }

        is AuthSchema.Basic<*> -> when (credential) {
            is AuthCredential.BasicCredentials -> header("Authorization", "Basic ${credential.encoded}")
            else -> error("Expected BasicCredentials credential for Basic auth")
        }

        is AuthSchema.ApiKeyHeader<*> -> when (credential) {
            is AuthCredential.ApiKey -> header(schema.headerName, credential.key)
            else -> error("Expected ApiKey credential for ApiKeyHeader auth")
        }

        is AuthSchema.Cookie<*> -> when (credential) {
            is AuthCredential.CookieValue -> header("Cookie", "${schema.cookieName}=${credential.value}")
            else -> error("Expected CookieValue credential for Cookie auth")
        }

        is AuthSchema.Optional<*> -> applyAuth(schema.inner, credential)
        is AuthSchema.OneOf<*> -> {
            val matched = schema.schemes.firstOrNull { scheme ->
                when (scheme) {
                    is AuthSchema.Bearer<*> -> credential is AuthCredential.BearerToken
                    is AuthSchema.Basic<*> -> credential is AuthCredential.BasicCredentials
                    is AuthSchema.ApiKeyHeader<*> -> credential is AuthCredential.ApiKey
                    is AuthSchema.Cookie<*> -> credential is AuthCredential.CookieValue
                    // Unreachable: OneOf.of() validates these cannot be nested
                    is AuthSchema.None, is AuthSchema.Optional<*>, is AuthSchema.OneOf<*> -> false
                }
            }
            if (matched != null) {
                applyAuth(matched, credential)
            } else {
                error("No scheme in OneOf matches credential type ${credential::class.simpleName}")
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
private fun <I> HttpRequestBuilder.applyBody(bodySchema: BodySchema<I>, value: I) {
    if (bodySchema.schema() is Schema.Empty) return
    when (bodySchema) {
        is BodySchema.WithMetadata -> applyBody(bodySchema.schema, value)
        is BodySchema.Single -> when (bodySchema.contentType) {
            ContentType.Json -> {
                header("Content-Type", "application/json")
                setBody(bodySchema.schema.encodeToJsonBytes(value))
            }

            ContentType.Plain, ContentType.Html -> {
                header("Content-Type", bodySchema.contentType.mimeType)
                setBody(bodySchema.schema.encodePrimitiveString(value).getOrThrow())
            }

            ContentType.MultipartFormData -> {
                val record = bodySchema.schema as? Schema.Record<I> ?: error("MultipartFormData requires a Record schema")
                setBody(MultiPartFormDataContent(encodeMultipart(record, value)))
            }

            ContentType.FormUrlEncoded -> {
                val record = bodySchema.schema as? Schema.Record<I> ?: error("FormUrlEncoded requires a Record schema")
                setBody(FormDataContent(encodeFormUrlEncoded(record, value)))
            }

            is ContentType.Image -> {
                header("Content-Type", bodySchema.contentType.mimeType)
                setBody(value as ByteArray)
            }

            ContentType.Avro -> {
                header("Content-Type", bodySchema.contentType.mimeType)
                setBody(value as ByteArray)
            }

            ContentType.EventStream -> {
                error("EventStream is not supported as a request body content type")
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
private fun <I> encodeMultipart(record: Schema.Record<I>, value: I): List<PartData> =
    formData {
        for (field in record.unsafeFields) {
            val fieldValue = (field as Field<I, Any?>).extract(value)
            encodeMultipartField(field.name, field.schema, fieldValue)
        }
    }

@Suppress("UNCHECKED_CAST")
private fun FormBuilder.encodeMultipartField(name: String, schema: Schema<Any?>, value: Any?) {
    when (schema) {
        is Schema.Optional<*> -> if (value != null) encodeMultipartField(name, schema.schema as Schema<Any?>, value)
        is Schema.Default -> encodeMultipartField(name, schema.schema, value)
        is Schema.Metadata -> encodeMultipartField(name, schema.schema, value)
        is Schema.Lazy -> encodeMultipartField(name, schema.schema(), value)
        is Schema.Transform<Any?, *> -> encodeMultipartField(name, schema.schema as Schema<Any?>, schema.encode(value))
        is Schema.OrElse<Any?, *> -> encodeMultipartField(name, schema.preferred, value)
        is Schema.Bytes -> append(name, value as ByteArray, Headers.build {
            append(HttpHeaders.ContentDisposition, "filename=\"$name\"")
        })

        is Schema.Primitive -> append(name, schema.encodePrimitiveString(value).getOrThrow())
        is Schema.Empty -> {}
        is Schema.Collection<*> -> {
            val items = value as? List<*> ?: return
            for (item in items) encodeMultipartField(name, schema.itemSchema as Schema<Any?>, item)
        }

        is Schema.Dynamic, is Schema.Record, is Schema.Union, is Schema.StringMap<*> ->
            append(name, schema.encodeToJsonString(value))
    }
}

@Suppress("UNCHECKED_CAST")
private fun <I> encodeFormUrlEncoded(record: Schema.Record<I>, value: I): Parameters =
    Parameters.build {
        for (field in record.unsafeFields) {
            val fieldValue = (field as Field<I, Any?>).extract(value)
            encodeFormField(field.name, field.schema, fieldValue)
        }
    }

@Suppress("UNCHECKED_CAST")
private fun ParametersBuilder.encodeFormField(name: String, schema: Schema<Any?>, value: Any?) {
    when (schema) {
        is Schema.Optional<*> -> if (value != null) encodeFormField(name, schema.schema as Schema<Any?>, value)
        is Schema.Default -> encodeFormField(name, schema.schema, value)
        is Schema.Metadata -> encodeFormField(name, schema.schema, value)
        is Schema.Lazy -> encodeFormField(name, schema.schema(), value)
        is Schema.Transform<Any?, *> -> encodeFormField(name, schema.schema as Schema<Any?>, schema.encode(value))
        is Schema.OrElse<Any?, *> -> encodeFormField(name, schema.preferred, value)
        is Schema.Bytes -> error("Cannot encode Bytes as form-url-encoded field")
        is Schema.Primitive -> append(name, schema.encodePrimitiveString(value).getOrThrow())
        is Schema.Empty -> {}
        is Schema.Collection<*> -> {
            val items = value as? List<*> ?: return
            for (item in items) encodeFormField(name, schema.itemSchema as Schema<Any?>, item)
        }

        is Schema.Dynamic, is Schema.Record, is Schema.Union, is Schema.StringMap<*> ->
            append(name, schema.encodeToJsonString(value))
    }
}

private sealed interface StatusMatch<out E, out O> {
    data class Output<O>(val schema: BodySchema<O>) : StatusMatch<Nothing, O>
    data class EventStream<O>(val schema: BodySchema<O>) : StatusMatch<Nothing, O>
    data class Error<E>(val schema: BodySchema<E>) : StatusMatch<E, Nothing>
    data object Unmatched : StatusMatch<Nothing, Nothing>
}

@Suppress("UNCHECKED_CAST")
private fun <P, I, E, O, A> matchStatusSchema(
    endpoint: Http<P, I, E, O, A>,
    statusCode: Int,
    isEventStream: Boolean
): StatusMatch<E, O> {
    val matchedOutput = endpoint.output.schemaByStatus().entries
        .firstOrNull { it.key.code == statusCode }
    val matchedEventStream = endpoint.output.findEventStream()
        ?.takeIf { it.status.code == statusCode }

    if (isEventStream) {
        matchedEventStream?.let { return StatusMatch.EventStream(it.bodySchema) }
        matchedOutput?.let { return StatusMatch.Output(it.value as BodySchema<O>) }
    } else {
        matchedOutput?.let { return StatusMatch.Output(it.value as BodySchema<O>) }
        matchedEventStream?.let { return StatusMatch.EventStream(it.bodySchema) }
    }

    endpoint.error.schemaByStatus().entries
        .firstOrNull { it.key.code == statusCode }
        ?.let { return StatusMatch.Error(it.value as BodySchema<E>) }

    return StatusMatch.Unmatched
}

private suspend fun <P, I, E, O, A> decodeResponse(
    endpoint: Http<P, I, E, O, A>,
    response: HttpResponse
): HttpResult<E, O> {
    val statusCode = response.status.value
    val bytes = response.readRawBytes()
    val isEventStream = response.isEventStream()

    return when (val match = matchStatusSchema(endpoint, statusCode, isEventStream)) {
        is StatusMatch.Output<O> -> runCatching { decodeBody(match.schema, bytes) }
            .fold(onSuccess = { HttpResult.Success(it) }, onFailure = { HttpResult.NetworkError(it) })

        is StatusMatch.EventStream<O> -> HttpResult.NetworkError(
            IllegalStateException("Endpoint returned an event stream; use stream() instead of call()")
        )

        is StatusMatch.Error<E> -> decodeFailure(match.schema, statusCode, bytes)

        StatusMatch.Unmatched -> HttpResult.NetworkError(
            IllegalStateException("Unexpected status $statusCode: ${bytes.decodeToString()}")
        )
    }
}

private fun <E> decodeFailure(
    schema: BodySchema<E>,
    statusCode: Int,
    bytes: ByteArray
): HttpResult<E, Nothing> =
    runCatching { decodeBody(schema, bytes) }
        .fold(
            onSuccess = { HttpResult.Failure(statusCode, it) },
            onFailure = { HttpResult.NetworkError(it) }
        )

@Suppress("UNCHECKED_CAST")
private fun <A> decodeBody(bodySchema: BodySchema<A>, bytes: ByteArray): A =
    when (bodySchema) {
        is BodySchema.WithMetadata -> decodeBody(bodySchema.schema, bytes)
        is BodySchema.Single -> when (bodySchema.contentType) {
            ContentType.Json -> bodySchema.schema.decodeFromJsonBytes(bytes).fold(
                onValid = { it },
                onInvalid = { errors -> error("Failed to decode JSON response: ${errors.joinToString { it.reason() }}") }
            )

            else -> bodySchema.schema.decodePrimitiveString(bytes.decodeToString()).getOrThrow()
        }
    }
