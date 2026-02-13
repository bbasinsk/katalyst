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
import io.github.bbasinsk.schema.decodePrimitiveString
import io.github.bbasinsk.schema.encodePrimitiveString
import io.github.bbasinsk.schema.json.kotlinx.decodeFromJsonString
import io.github.bbasinsk.schema.json.kotlinx.encodeToJsonBytes
import io.github.bbasinsk.validation.fold
import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header
import io.ktor.client.request.prepareRequest
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsChannel
import io.ktor.client.statement.bodyAsText
import io.ktor.http.appendPathSegments
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.readLine
import kotlin.jvm.JvmName
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow

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
        } catch (e: Exception) {
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

    fun <P, I, E, O, A> stream(
        endpoint: Http<P, I, E, O, A>,
        params: P,
        auth: AuthCredential? = null
    ): Flow<SSEEvent<O>> = channelFlow {
        val rendered = endpoint.params.render(params)
        val eventStream = endpoint.output.findEventStream()
            ?: error("Endpoint output schema has no SSE EventStream variant")

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
            header("Accept", "text/event-stream")
        }.execute { response ->
            val channel: ByteReadChannel = response.bodyAsChannel()
            var eventType: String? = null
            var dataLines = mutableListOf<String>()
            var eventId: String? = null
            var retry: Long? = null
            var comment: String? = null

            while (!channel.isClosedForRead) {
                val line = channel.readLine() ?: break

                when {
                    line.isEmpty() -> {
                        if (dataLines.isNotEmpty() || comment != null) {
                            val data = if (dataLines.isNotEmpty()) {
                                val raw = dataLines.joinToString("\n")
                                decodeBody(eventStream.bodySchema, raw)
                            } else null

                            send(SSEEvent(
                                data = data,
                                event = eventType,
                                id = eventId,
                                retry = retry,
                                comment = comment
                            ))
                        }
                        eventType = null
                        dataLines = mutableListOf()
                        eventId = null
                        retry = null
                        comment = null
                    }
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
        }
    }

    @JvmName("streamNoParams")
    fun <I, E, O, A> stream(
        endpoint: Http<Unit, I, E, O, A>,
        auth: AuthCredential? = null
    ): Flow<SSEEvent<O>> = stream(endpoint, Unit, auth)
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
    }
}

@Suppress("UNCHECKED_CAST")
private fun <I> HttpRequestBuilder.applyBody(bodySchema: BodySchema<I>, value: I) {
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
            else -> {}
        }
    }
}

@Suppress("UNCHECKED_CAST")
private suspend fun <P, I, E, O, A> decodeResponse(
    endpoint: Http<P, I, E, O, A>,
    response: HttpResponse
): HttpResult<E, O> {
    val statusCode = response.status.value
    val body = response.bodyAsText()

    val outputByStatus = endpoint.output.schemaByStatus()
    val errorByStatus = endpoint.error.schemaByStatus()

    val matchedOutput = outputByStatus.entries.firstOrNull { it.key.code == statusCode }
    if (matchedOutput != null) {
        return runCatching { decodeBody(matchedOutput.value as BodySchema<O>, body) }
            .fold(onSuccess = { HttpResult.Success(it) }, onFailure = { HttpResult.NetworkError(it) })
    }

    val matchedError = errorByStatus.entries.firstOrNull { it.key.code == statusCode }
    if (matchedError != null) {
        return runCatching { decodeBody(matchedError.value as BodySchema<E>, body) }
            .fold(onSuccess = { HttpResult.Failure(statusCode, it) }, onFailure = { HttpResult.NetworkError(it) })
    }

    return HttpResult.NetworkError(
        IllegalStateException("Unexpected status $statusCode: $body")
    )
}

@Suppress("UNCHECKED_CAST")
private fun <A> decodeBody(bodySchema: BodySchema<A>, body: String): A =
    when (bodySchema) {
        is BodySchema.WithMetadata -> decodeBody(bodySchema.schema, body)
        is BodySchema.Single -> when (bodySchema.contentType) {
            ContentType.Json -> bodySchema.schema.decodeFromJsonString(body).fold(
                onValid = { it },
                onInvalid = { errors -> error("Failed to decode JSON response: ${errors.joinToString { it.reason() }}") }
            )
            ContentType.Plain, ContentType.Html ->
                bodySchema.schema.decodePrimitiveString(body).getOrThrow()
            else ->
                bodySchema.schema.decodePrimitiveString(body).getOrThrow()
        }
    }
