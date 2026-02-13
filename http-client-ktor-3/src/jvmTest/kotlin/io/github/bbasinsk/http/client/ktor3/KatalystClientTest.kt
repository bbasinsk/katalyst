package io.github.bbasinsk.http.client.ktor3

import io.github.bbasinsk.http.AuthCredential
import io.github.bbasinsk.http.AuthResult
import io.github.bbasinsk.http.Http
import io.github.bbasinsk.http.HttpResult
import io.github.bbasinsk.http.Response
import io.github.bbasinsk.http.SSEEvent
import io.github.bbasinsk.http.auth
import io.github.bbasinsk.http.ktor3.endpoints
import io.github.bbasinsk.http.query
import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.tuple.tupleValues
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.testing.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import java.io.IOException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class KatalystClientTest {

    data class User(val name: String, val age: Int)

    val userSchema = Schema.record(
        Schema.field(Schema.string(), "name") { name },
        Schema.field(Schema.int(), "age") { age },
        ::User
    )

    @Test
    fun `GET with path params returns success`() = testApplication {
        val api = Http.get { Root / "users" / param("id") { int() } }
            .output { status(Ok) { json { userSchema } } }

        application {
            endpoints {
                handle(api) { request ->
                    val (id) = tupleValues(request.params)
                    Response.success(User("User-$id", id))
                }
            }
        }

        val katalystClient = KatalystClient(client)
        val result = katalystClient.call(api, 42, null)

        assertIs<HttpResult.Success<User>>(result)
        assertEquals(User("User-42", 42), result.value)
    }

    @Test
    fun `POST with JSON body returns success`() = testApplication {
        val api = Http.post { Root / "users" }
            .input { json { userSchema } }
            .output { status(Created) { json { userSchema } } }

        install(ContentNegotiation) { json() }

        application {
            endpoints {
                handle(api) { request ->
                    Response.success(request.input)
                }
            }
        }

        val katalystClient = KatalystClient(client)
        val input = User("Alice", 30)
        val result = katalystClient.call(api, Unit, input)

        assertIs<HttpResult.Success<User>>(result)
        assertEquals(input, result.value)
    }

    @Test
    fun `error response returns HttpResult Failure`() = testApplication {
        data class ErrorMessage(val message: String)

        val errorSchema = Schema.record(
            Schema.field(Schema.string(), "message") { message },
            ::ErrorMessage
        )

        val api = Http.get { Root / "fail" }
            .output { status(Ok) { json { userSchema } } }
            .error { status(NotFound) { json { errorSchema } } }

        application {
            endpoints {
                handle(api) {
                    Response.error(ErrorMessage("not found"))
                }
            }
        }

        val katalystClient = KatalystClient(client)
        val result = katalystClient.call(api, Unit, null)

        assertIs<HttpResult.Failure<ErrorMessage>>(result)
        assertEquals(404, result.status)
        assertEquals(ErrorMessage("not found"), result.error)
    }

    @Test
    fun `GET with query params`() = testApplication {
        val api = Http.get { Root / "search" }
            .query { schema("q") { string() } }
            .output { status(Ok) { plain { string() } } }

        application {
            endpoints {
                handle(api) { request ->
                    val (q) = tupleValues(request.params)
                    Response.success("Found: $q")
                }
            }
        }

        val katalystClient = KatalystClient(client)
        val result = katalystClient.call(api, "hello", null)

        assertIs<HttpResult.Success<String>>(result)
        assertEquals("Found: hello", result.value)
    }

    @Test
    fun `auth credential sends bearer token header`() = testApplication {
        val api = Http.get { Root / "protected" }
            .auth { bearer<String>() }
            .output { status(Ok) { plain { string() } } }

        application {
            endpoints {
                handle(api, { token -> AuthResult.Success(token ?: "none") }) { request ->
                    Response.success("auth=${request.auth}")
                }
            }
        }

        val katalystClient = KatalystClient(client)
        val result = katalystClient.call(
            api, Unit, null,
            auth = AuthCredential.BearerToken("my-token")
        )

        assertIs<HttpResult.Success<String>>(result)
        assertEquals("auth=my-token", result.value)
    }

    @Test
    fun `connection failure returns NetworkError`() = runTest {
        val mockEngine = MockEngine { throw IOException("Connection refused") }
        val client = HttpClient(mockEngine)

        val api = Http.get { Root / "users" }
            .output { status(Ok) { json { userSchema } } }

        val katalystClient = KatalystClient(client)
        val result = katalystClient.call(api, Unit, null)

        assertIs<HttpResult.NetworkError>(result)
        assertIs<IOException>(result.cause)
    }

    @Test
    fun `malformed response body returns NetworkError`() = runTest {
        val mockEngine = MockEngine {
            respond(
                content = "not valid json",
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
        val client = HttpClient(mockEngine)

        val api = Http.get { Root / "users" }
            .output { status(Ok) { json { userSchema } } }

        val katalystClient = KatalystClient(client)
        val result = katalystClient.call(api, Unit, null)

        assertIs<HttpResult.NetworkError>(result)
    }

    @Test
    fun `SSE streaming returns flow of events`() = testApplication {
        val api = Http.get { Root / "stream" }
            .output { sse { json { string() } } }

        application {
            endpoints {
                handle(api) {
                    Response.streamingSuccess(flow {
                        emit(SSEEvent.data("event-1"))
                        delay(10)
                        emit(SSEEvent.data("event-2"))
                        delay(10)
                        emit(SSEEvent.data("event-3"))
                    })
                }
            }
        }

        val katalystClient = KatalystClient(client)
        val events = katalystClient.stream(api, Unit).toList()

        assertEquals(3, events.size)
        assertEquals("event-1", events[0].data)
        assertEquals("event-2", events[1].data)
        assertEquals("event-3", events[2].data)
    }
}
