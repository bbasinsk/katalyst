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
import io.github.bbasinsk.schema.transform
import io.github.bbasinsk.tuple.tupleValues
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.HttpTimeout
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.testing.*
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import java.io.IOException
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
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
        val result = katalystClient.call(api, 42)

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
        val result = katalystClient.call(api, input)

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
        val result = katalystClient.call(api)

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
        val result = katalystClient.call(api, "hello")

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
            api,
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
        val result = katalystClient.call(api)

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
        val result = katalystClient.call(api)

        assertIs<HttpResult.NetworkError>(result)
    }

    @Test
    fun `SSE streaming returns flow of events`() = testApplication {
        val api = Http.get { Root / "stream" }
            .output { sse(Ok) { json { string() } } }

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
        val result = assertIs<HttpResult.Success<Flow<SSEEvent<String>>>>(katalystClient.stream(api))
        val events = result.value.toList()

        assertEquals(3, events.size)
        assertEquals("event-1", events[0].data)
        assertEquals("event-2", events[1].data)
        assertEquals("event-3", events[2].data)
    }

    data class ChatError(val code: String, val message: String)

    val chatErrorSchema: Schema<ChatError> = Schema.record(
        Schema.field(Schema.string(), "code") { code },
        Schema.field(Schema.string(), "message") { message },
        ::ChatError
    )

    @Test
    fun `stream returns Failure on declared error status`() = runTest {
        val mockEngine = MockEngine {
            respond(
                content = """{"code":"CONFLICT","message":"already exists"}""",
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
        assertEquals(ChatError("CONFLICT", "already exists"), failure.error)
    }

    @Test
    fun `stream returns NetworkError on undeclared error status`() = runTest {
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
    fun `stream returns NetworkError on body decode failure for declared error status`() = runTest {
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
    fun `stream returns NetworkError on connection failure`() = runTest {
        val mockEngine = MockEngine { throw IOException("Connection refused") }
        val client = HttpClient(mockEngine)

        val api = Http.post { Root / "chat" }
            .output { sse(Ok) { json { Schema.string() } } }

        val networkError = assertIs<HttpResult.NetworkError>(KatalystClient(client).stream(api))
        assertIs<IOException>(networkError.cause)
    }

    @Test
    fun `stream CancellationException propagates unchanged`() = runTest {
        val mockEngine = MockEngine { throw CancellationException("cancelled") }
        val client = HttpClient(mockEngine)

        val api = Http.post { Root / "chat" }
            .output { sse(Ok) { json { Schema.string() } } }

        assertFailsWith<CancellationException> {
            KatalystClient(client).stream(api)
        }
    }

    @Test
    fun `SSE stream with keepalive heartbeats skips null data`() = testApplication {
        val api = Http.get { Root / "heartbeat" }
            .output { sse(Ok) { json { userSchema } } }

        application {
            endpoints {
                handle(api) {
                    Response.streamingSuccess(flow {
                        emit(SSEEvent.data(User("Alice", 30)))
                        delay(10)
                        emit(SSEEvent.keepalive("heartbeat"))
                        delay(10)
                        emit(SSEEvent.data(User("Bob", 25)))
                    })
                }
            }
        }

        val katalystClient = KatalystClient(client)
        val result = assertIs<HttpResult.Success<Flow<SSEEvent<User>>>>(katalystClient.stream(api))
        val events = result.value.toList()

        assertEquals(3, events.size)
        assertEquals(User("Alice", 30), events[0].data)
        assertEquals(null, events[1].data)
        assertEquals("heartbeat", events[1].comment)
        assertEquals(User("Bob", 25), events[2].data)
    }

    data class MultipartInput(val name: String, val age: Int, val nickname: String?)

    val multipartSchema: Schema<MultipartInput> = Schema.record(
        Schema.field(Schema.string(), "name") { name },
        Schema.field(Schema.int(), "age") { age },
        Schema.field(Schema.string().optional(), "nickname") { nickname },
        ::MultipartInput
    )

    @Test
    fun `POST with multipart form data round-trips through server`() = testApplication {
        val api = Http.post { Root / "multipart" }
            .input { multipart { multipartSchema } }
            .output { status(Ok) { plain { string() } } }

        application {
            endpoints {
                handle(api) { request ->
                    val (name, age, nickname) = request.input
                    Response.success("name=$name,age=$age,nickname=$nickname")
                }
            }
        }

        val katalystClient = KatalystClient(client)
        val result = katalystClient.call(api, MultipartInput("Alice", 30, "Ali"))

        assertIs<HttpResult.Success<String>>(result)
        assertEquals("name=Alice,age=30,nickname=Ali", result.value)
    }

    @Test
    fun `POST with multipart form data omits null optional fields`() = testApplication {
        val api = Http.post { Root / "multipart" }
            .input { multipart { multipartSchema } }
            .output { status(Ok) { plain { string() } } }

        application {
            endpoints {
                handle(api) { request ->
                    val (name, age, nickname) = request.input
                    Response.success("name=$name,age=$age,nickname=$nickname")
                }
            }
        }

        val katalystClient = KatalystClient(client)
        val result = katalystClient.call(api, MultipartInput("Bob", 25, null))

        assertIs<HttpResult.Success<String>>(result)
        assertEquals("name=Bob,age=25,nickname=null", result.value)
    }

    data class MultipartWithBytes(val file: ByteArray, val description: String)

    val multipartBytesSchema: Schema<MultipartWithBytes> = Schema.record(
        Schema.field(Schema.byteArray(), "file") { file },
        Schema.field(Schema.string(), "description") { description },
        ::MultipartWithBytes
    )

    @Test
    fun `POST with multipart bytes round-trips through server`() = testApplication {
        val api = Http.post { Root / "upload" }
            .input { multipart { multipartBytesSchema } }
            .output { status(Ok) { plain { string() } } }

        application {
            endpoints {
                handle(api) { request ->
                    val (file, description) = request.input
                    Response.success("desc=$description,size=${file.size}")
                }
            }
        }

        val katalystClient = KatalystClient(client)
        val fileContent = "hello world".toByteArray()
        val result = katalystClient.call(api, MultipartWithBytes(fileContent, "test file"))

        assertIs<HttpResult.Success<String>>(result)
        assertEquals("desc=test file,size=11", result.value)
    }

    data class FormInput(val username: String, val score: Int)

    val formSchema: Schema<FormInput> = Schema.record(
        Schema.field(Schema.string(), "username") { username },
        Schema.field(Schema.int(), "score") { score },
        ::FormInput
    )

    @Test
    fun `POST with form-url-encoded round-trips through server`() = testApplication {
        val api = Http.post { Root / "form" }
            .input { formUrlEncoded { formSchema } }
            .output { status(Ok) { plain { string() } } }

        application {
            endpoints {
                handle(api) { request ->
                    val (username, score) = request.input
                    Response.success("username=$username,score=$score")
                }
            }
        }

        val katalystClient = KatalystClient(client)
        val result = katalystClient.call(api, FormInput("alice", 100))

        assertIs<HttpResult.Success<String>>(result)
        assertEquals("username=alice,score=100", result.value)
    }

    @JvmInline
    value class Tag(val value: String)

    data class MultipartWithTransform(val name: String, val tag: Tag)

    val multipartTransformSchema: Schema<MultipartWithTransform> = Schema.record(
        Schema.field(Schema.string(), "name") { name },
        Schema.field(Schema.string().transform(::Tag) { it.value }, "tag") { tag },
        ::MultipartWithTransform
    )

    @Test
    fun `POST with multipart transform field round-trips through server`() = testApplication {
        val api = Http.post { Root / "multipart-transform" }
            .input { multipart { multipartTransformSchema } }
            .output { status(Ok) { plain { string() } } }

        application {
            endpoints {
                handle(api) { request ->
                    val (name, tag) = request.input
                    Response.success("name=$name,tag=${tag.value}")
                }
            }
        }

        val katalystClient = KatalystClient(client)
        val result = katalystClient.call(api, MultipartWithTransform("item", Tag("important")))

        assertIs<HttpResult.Success<String>>(result)
        assertEquals("name=item,tag=important", result.value)
    }

    data class MultipartWithList(val images: List<ByteArray>)

    val multipartListSchema: Schema<MultipartWithList> = Schema.record(
        Schema.field(Schema.list(Schema.byteArray()), "images") { images },
        ::MultipartWithList
    )

    @Test
    fun `POST with multipart collection field round-trips through server`() = testApplication {
        val api = Http.post { Root / "upload-multiple" }
            .input { multipart { multipartListSchema } }
            .output { status(Ok) { plain { string() } } }

        application {
            endpoints {
                handle(api) { request ->
                    val files = request.input.images
                    Response.success("count=${files.size},sizes=${files.map { it.size }}")
                }
            }
        }

        val katalystClient = KatalystClient(client)
        val img1 = byteArrayOf(1, 2, 3)
        val img2 = byteArrayOf(4, 5, 6, 7)
        val result = katalystClient.call(api, MultipartWithList(listOf(img1, img2)))

        assertIs<HttpResult.Success<String>>(result)
        assertEquals("count=2,sizes=[3, 4]", result.value)
    }

    @Test
    fun `POST with image body sends raw bytes`() = testApplication {
        val api = Http.post { Root / "upload-image" }
            .input { bytes(io.github.bbasinsk.http.ContentType.Image.Png) }
            .output { status(Ok) { plain { string() } } }

        application {
            endpoints {
                handle(api) { request ->
                    Response.success("size=${request.input.size}")
                }
            }
        }

        val katalystClient = KatalystClient(client)
        val pngBytes = byteArrayOf(0x89.toByte(), 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A)
        val result = katalystClient.call(api, pngBytes)

        assertIs<HttpResult.Success<String>>(result)
        assertEquals("size=8", result.value)
    }

    @Test
    fun `CancellationException is rethrown not swallowed`() = runTest {
        val mockEngine = MockEngine { throw CancellationException("coroutine cancelled") }
        val client = HttpClient(mockEngine)

        val api = Http.get { Root / "users" }
            .output { status(Ok) { json { userSchema } } }

        val katalystClient = KatalystClient(client)
        assertFailsWith<CancellationException> {
            katalystClient.call(api)
        }
    }

    @Test
    fun `request timeout returns NetworkError`() = runTest {
        val mockEngine = MockEngine {
            delay(500)
            respond(
                content = """{"name":"Alice","age":30}""",
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
        val client = HttpClient(mockEngine) {
            install(HttpTimeout) { requestTimeoutMillis = 50 }
        }

        val api = Http.get { Root / "users" }
            .output { status(Ok) { json { userSchema } } }

        val katalystClient = KatalystClient(client)
        val result = katalystClient.call(api)

        assertIs<HttpResult.NetworkError>(result)
    }
}
