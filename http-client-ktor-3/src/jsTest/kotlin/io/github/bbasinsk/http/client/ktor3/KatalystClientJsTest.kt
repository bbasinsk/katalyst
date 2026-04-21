package io.github.bbasinsk.http.client.ktor3

import io.github.bbasinsk.http.Http
import io.github.bbasinsk.http.HttpResult
import io.github.bbasinsk.schema.Schema
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import kotlin.coroutines.cancellation.CancellationException
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertIs

class KatalystClientJsTest {

    @Test
    fun throwable_from_engine_returns_NetworkError() = runTest {
        val mockEngine = MockEngine { throw Error("JS fetch TypeError simulation") }
        val client = HttpClient(mockEngine)

        val api = Http.get { Root / "test" }
            .output { status(Ok) { json { Schema.string() } } }

        val result = KatalystClient(client).call(api)

        assertIs<HttpResult.NetworkError>(result)
    }

    data class ChatError(val code: String)

    private val chatErrorSchema = Schema.record(
        Schema.field(Schema.string(), "code") { code },
        ::ChatError
    )

    @Test
    fun stream_returns_Failure_on_declared_error_status() = runTest {
        val mockEngine = MockEngine {
            respond(
                content = """{"code":"CONFLICT"}""",
                status = HttpStatusCode.Conflict,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
        val client = HttpClient(mockEngine)

        val api = Http.post { Root / "chat" }
            .output { sse(Ok) { json { Schema.string() } } }
            .error { status(Conflict) { json { chatErrorSchema } } }

        val failure = assertIs<HttpResult.Failure<ChatError>>(KatalystClient(client).stream(api))
        assertEquals(409, failure.status)
        assertEquals(ChatError("CONFLICT"), failure.error)
    }

    @Test
    fun stream_returns_NetworkError_on_undeclared_error_status() = runTest {
        val mockEngine = MockEngine {
            respond(
                content = "boom",
                status = HttpStatusCode.InternalServerError,
                headers = headersOf(HttpHeaders.ContentType, "text/plain")
            )
        }
        val client = HttpClient(mockEngine)

        val api = Http.post { Root / "chat" }
            .output { sse(Ok) { json { Schema.string() } } }
            .error { status(Conflict) { json { chatErrorSchema } } }

        val networkError = assertIs<HttpResult.NetworkError>(KatalystClient(client).stream(api))
        assertIs<IllegalStateException>(networkError.cause)
        assertEquals("Unexpected status 500: boom", networkError.cause.message)
    }

    @Test
    fun stream_returns_NetworkError_on_body_decode_failure_for_declared_error_status() = runTest {
        val mockEngine = MockEngine {
            respond(
                content = "not valid json",
                status = HttpStatusCode.Conflict,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
        val client = HttpClient(mockEngine)

        val api = Http.post { Root / "chat" }
            .output { sse(Ok) { json { Schema.string() } } }
            .error { status(Conflict) { json { chatErrorSchema } } }

        assertIs<HttpResult.NetworkError>(KatalystClient(client).stream(api))
    }

    @Test
    fun stream_returns_NetworkError_on_connection_failure() = runTest {
        val mockEngine = MockEngine { throw RuntimeException("Connection refused") }
        val client = HttpClient(mockEngine)

        val api = Http.post { Root / "chat" }
            .output { sse(Ok) { json { Schema.string() } } }

        val networkError = assertIs<HttpResult.NetworkError>(KatalystClient(client).stream(api))
        assertIs<RuntimeException>(networkError.cause)
    }

    @Test
    fun stream_CancellationException_propagates_unchanged() = runTest {
        val mockEngine = MockEngine { throw CancellationException("cancelled") }
        val client = HttpClient(mockEngine)

        val api = Http.post { Root / "chat" }
            .output { sse(Ok) { json { Schema.string() } } }

        assertFailsWith<CancellationException> {
            KatalystClient(client).stream(api)
        }
    }
}
