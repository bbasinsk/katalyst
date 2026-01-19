package io.github.bbasinsk.http.ktor3

import io.github.bbasinsk.http.Http
import io.github.bbasinsk.http.Response
import io.github.bbasinsk.http.SSEEvent
import io.github.bbasinsk.schema.Schema
import io.ktor.client.plugins.sse.*
import io.ktor.server.testing.*
import io.ktor.sse.*
import io.ktor.util.cio.ChannelWriteException
import io.ktor.utils.io.ClosedWriteChannelException
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import java.nio.channels.ClosedChannelException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class SSETest {

    @Test
    fun `test simple SSE stream with data events`() = testApplication {
        data class Message(val text: String, val timestamp: Long)

        val messageSchema = Schema.record(
            Schema.field(Schema.string(), "text") { text },
            Schema.field(Schema.long(), "timestamp") { timestamp },
            ::Message
        )

        val api = Http.get { Root / "stream" }
            .output { sse { json { messageSchema } } }

        application {
            endpoints {
                handle(api) {
                    Response.streamingSuccessData(flow {
                        repeat(5) { i ->
                            emit(Message("Message $i", System.currentTimeMillis()))
                            delay(10)
                        }
                    })
                }
            }
        }

        val client = createClient {
            install(SSE)
        }

        val receivedEvents = mutableListOf<ServerSentEvent>()

        client.sse("/stream") {
            incoming.collect { event ->
                receivedEvents.add(event)
                if (receivedEvents.size >= 5) {
                    cancel()
                }
            }
        }

        assertEquals(5, receivedEvents.size)
        receivedEvents.forEachIndexed { index, event ->
            assertNotNull(event.data)
            assertTrue(event.data!!.contains("\"text\":\"Message $index\""))
        }
    }

    @Test
    fun `test SSE stream with custom event types`() = testApplication {
        val api = Http.get { Root / "events" }
            .output { sse { json { string() } } }

        application {
            endpoints {
                handle(api) {
                    Response.streamingSuccess(flow {
                        emit(SSEEvent.typed("greeting", "Hello"))
                        delay(10)
                        emit(SSEEvent.typed("farewell", "Goodbye"))
                    })
                }
            }
        }

        val client = createClient {
            install(SSE)
        }

        val receivedEvents = mutableListOf<ServerSentEvent>()

        client.sse("/events") {
            incoming.collect { event ->
                receivedEvents.add(event)
                if (receivedEvents.size >= 2) {
                    cancel()
                }
            }
        }

        assertEquals(2, receivedEvents.size)
        assertEquals("greeting", receivedEvents[0].event)
        assertEquals("\"Hello\"", receivedEvents[0].data)
        assertEquals("farewell", receivedEvents[1].event)
        assertEquals("\"Goodbye\"", receivedEvents[1].data)
    }

    @Test
    fun `test SSE stream with event IDs`() = testApplication {
        val api = Http.get { Root / "numbered" }
            .output { sse { json { Schema.int() } } }

        application {
            endpoints {
                handle(api) {
                    Response.streamingSuccess(flow {
                        repeat(3) { i ->
                            emit(SSEEvent(
                                data = i * 10,
                                id = "event-$i"
                            ))
                            delay(10)
                        }
                    })
                }
            }
        }

        val client = createClient {
            install(SSE)
        }

        val receivedEvents = mutableListOf<ServerSentEvent>()

        client.sse("/numbered") {
            incoming.collect { event ->
                receivedEvents.add(event)
                if (receivedEvents.size >= 3) {
                    cancel()
                }
            }
        }

        assertEquals(3, receivedEvents.size)
        assertEquals("event-0", receivedEvents[0].id)
        assertEquals("0", receivedEvents[0].data)
        assertEquals("event-1", receivedEvents[1].id)
        assertEquals("10", receivedEvents[1].data)
        assertEquals("event-2", receivedEvents[2].id)
        assertEquals("20", receivedEvents[2].data)
    }

    @Test
    fun `test SSE stream with retry configuration`() = testApplication {
        val api = Http.get { Root / "retry" }
            .output { sse { json { string() } } }

        application {
            endpoints {
                handle(api) {
                    Response.streamingSuccess(flow {
                        emit(SSEEvent(
                            data = "test",
                            retry = 5000L
                        ))
                    })
                }
            }
        }

        val client = createClient {
            install(SSE)
        }

        val receivedEvents = mutableListOf<ServerSentEvent>()

        client.sse("/retry") {
            incoming.collect { event ->
                receivedEvents.add(event)
                cancel()
            }
        }

        assertEquals(1, receivedEvents.size)
        assertEquals(5000L, receivedEvents[0].retry)
    }

    @Test
    fun `test SSE stream with comments`() = testApplication {
        val api = Http.get { Root / "comments" }
            .output { sse { json { string() } } }

        application {
            endpoints {
                handle(api) {
                    Response.streamingSuccess(flow {
                        emit(SSEEvent(
                            data = "first",
                            comment = "This is a comment"
                        ))
                        delay(10)
                        emit(SSEEvent(
                            data = "second"
                        ))
                    })
                }
            }
        }

        val client = createClient {
            install(SSE)
        }

        val receivedEvents = mutableListOf<ServerSentEvent>()

        client.sse("/comments") {
            incoming.collect { event ->
                receivedEvents.add(event)
                if (receivedEvents.size >= 2) {
                    cancel()
                }
            }
        }

        assertEquals(2, receivedEvents.size)
        assertEquals("This is a comment", receivedEvents[0].comments)
        assertEquals("\"first\"", receivedEvents[0].data)
    }

    @Test
    fun `test SSE stream with plain text content`() = testApplication {
        val api = Http.get { Root / "plain" }
            .output { sse { plain { string() } } }

        application {
            endpoints {
                handle(api) {
                    Response.streamingSuccessData(flow {
                        emit("Plain text message 1")
                        delay(10)
                        emit("Plain text message 2")
                    })
                }
            }
        }

        val client = createClient {
            install(SSE)
        }

        val receivedEvents = mutableListOf<ServerSentEvent>()

        client.sse("/plain") {
            incoming.collect { event ->
                receivedEvents.add(event)
                if (receivedEvents.size >= 2) {
                    cancel()
                }
            }
        }

        assertEquals(2, receivedEvents.size)
        assertEquals("Plain text message 1", receivedEvents[0].data)
        assertEquals("Plain text message 2", receivedEvents[1].data)
    }

    @Test
    fun `test SSE stream with complex data structures`() = testApplication {
        data class User(val id: Int, val name: String, val email: String)

        val userSchema = Schema.record(
            Schema.field(Schema.int(), "id") { id },
            Schema.field(Schema.string(), "name") { name },
            Schema.field(Schema.string(), "email") { email },
            ::User
        )

        val api = Http.get { Root / "users" }
            .output { sse { json { userSchema } } }

        application {
            endpoints {
                handle(api) {
                    Response.streamingSuccessData(flow {
                        emit(User(1, "Alice", "alice@example.com"))
                        delay(10)
                        emit(User(2, "Bob", "bob@example.com"))
                        delay(10)
                        emit(User(3, "Charlie", "charlie@example.com"))
                    })
                }
            }
        }

        val client = createClient {
            install(SSE)
        }

        val receivedEvents = mutableListOf<ServerSentEvent>()

        client.sse("/users") {
            incoming.collect { event ->
                receivedEvents.add(event)
                if (receivedEvents.size >= 3) {
                    cancel()
                }
            }
        }

        assertEquals(3, receivedEvents.size)
        assertTrue(receivedEvents[0].data!!.contains("\"name\":\"Alice\""))
        assertTrue(receivedEvents[1].data!!.contains("\"name\":\"Bob\""))
        assertTrue(receivedEvents[2].data!!.contains("\"name\":\"Charlie\""))
    }

    @Test
    fun `test SSE keepalive with comment-only events`() = testApplication {
        val api = Http.get { Root / "keepalive" }
            .output { sse { json { string() } } }

        application {
            endpoints {
                handle(api) {
                    Response.streamingSuccess(flow {
                        emit(SSEEvent.data("start"))
                        delay(10)
                        emit(SSEEvent.keepalive("heartbeat"))
                        delay(10)
                        emit(SSEEvent.data("end"))
                    })
                }
            }
        }

        val client = createClient {
            install(SSE)
        }

        val receivedEvents = mutableListOf<ServerSentEvent>()

        client.sse("/keepalive") {
            incoming.collect { event ->
                receivedEvents.add(event)
                if (receivedEvents.size >= 3) {
                    cancel()
                }
            }
        }

        // All 3 events should be received
        assertEquals(3, receivedEvents.size)
        // Verify keepalive event structure
        assertEquals("heartbeat", receivedEvents[1].comments)
        assertEquals("", receivedEvents[1].data) // Empty data line for keepalive
    }

    @Test
    fun `test SSE streaming error response`() = testApplication {
        data class ErrorInfo(val code: Int, val message: String)

        val errorSchema = Schema.record(
            Schema.field(Schema.int(), "code") { code },
            Schema.field(Schema.string(), "message") { message },
            ::ErrorInfo
        )

        val api = Http.get { Root / "error-stream" }
            .output { status(Ok) { json { Schema.string() } } }
            .error { sse { json { errorSchema } } }

        application {
            endpoints {
                handle(api) {
                    Response.streamingError<ErrorInfo, String>(flow {
                        emit(SSEEvent.data(ErrorInfo(500, "First error")))
                        delay(10)
                        emit(SSEEvent.typed("critical", ErrorInfo(503, "Service unavailable")))
                    })
                }
            }
        }

        val client = createClient {
            install(SSE)
        }

        val receivedEvents = mutableListOf<ServerSentEvent>()

        client.sse("/error-stream") {
            incoming.collect { event ->
                receivedEvents.add(event)
                if (receivedEvents.size >= 2) {
                    cancel()
                }
            }
        }

        assertEquals(2, receivedEvents.size)
        assertTrue(receivedEvents[0].data!!.contains("\"code\":500"))
        assertTrue(receivedEvents[0].data!!.contains("\"message\":\"First error\""))
        assertEquals("critical", receivedEvents[1].event)
        assertTrue(receivedEvents[1].data!!.contains("\"code\":503"))
    }

    @Test
    fun `test SSE exception handling sends error event`() = testApplication {
        val api = Http.get { Root / "exception-stream" }
            .output { sse { json { Schema.string() } } }

        application {
            endpoints {
                handle(api) {
                    Response.streamingSuccessData(flow {
                        emit("before error")
                        delay(10)
                        throw RuntimeException("Multi-line\nerror\nmessage")
                    })
                }
            }
        }

        val client = createClient {
            install(SSE)
        }

        val receivedEvents = mutableListOf<ServerSentEvent>()

        client.sse("/exception-stream") {
            incoming.collect { event ->
                receivedEvents.add(event)
                if (receivedEvents.size >= 2) {
                    cancel()
                }
            }
        }

        assertEquals(2, receivedEvents.size)
        assertEquals("\"before error\"", receivedEvents[0].data)
        assertEquals("error", receivedEvents[1].event)
        // Error message is sanitized to prevent information leakage
        assertEquals("An error occurred", receivedEvents[1].data)
    }

    @Test
    fun `test oneOf with regular and streaming responses`() = testApplication {
        data class Result(val value: String)

        val resultSchema = Schema.record(
            Schema.field(Schema.string(), "value") { value },
            ::Result
        )

        val api = Http.get { Root / "hybrid-streaming" }
            .output {
                oneOf(
                    status(Ok) { json { resultSchema } },
                    sse { json { resultSchema } }
                )
            }

        application {
            endpoints {
                handle(api) {
                    Response.streamingSuccessData(flow {
                        emit(Result("streamed-1"))
                        delay(10)
                        emit(Result("streamed-2"))
                    })
                }
            }
        }

        val client = createClient {
            install(SSE)
        }

        val receivedEvents = mutableListOf<ServerSentEvent>()

        client.sse("/hybrid-streaming") {
            incoming.collect { event ->
                receivedEvents.add(event)
                if (receivedEvents.size >= 2) {
                    cancel()
                }
            }
        }

        assertEquals(2, receivedEvents.size)
        assertTrue(receivedEvents[0].data!!.contains("\"value\":\"streamed-1\""))
        assertTrue(receivedEvents[1].data!!.contains("\"value\":\"streamed-2\""))
    }

    @Test
    fun `test early client disconnect is handled gracefully`() = testApplication {
        val api = Http.get { Root / "long-stream" }
            .output { sse { json { Schema.int() } } }

        application {
            endpoints {
                handle(api) {
                    Response.streamingSuccessData(flow {
                        repeat(100) { i ->
                            emit(i)
                            delay(50)
                        }
                    })
                }
            }
        }

        val client = createClient {
            install(SSE)
        }

        val receivedEvents = mutableListOf<ServerSentEvent>()

        // Client disconnects after receiving just 2 events
        client.sse("/long-stream") {
            incoming.collect { event ->
                receivedEvents.add(event)
                if (receivedEvents.size >= 2) {
                    cancel() // Simulate client disconnect
                }
            }
        }

        // Verify we got the expected events before disconnect
        assertEquals(2, receivedEvents.size)
        assertEquals("0", receivedEvents[0].data)
        assertEquals("1", receivedEvents[1].data)
        // The test passes if no exception is thrown - server handles disconnect gracefully
    }

    @Test
    fun `test isChannelClosedException detects ChannelWriteException`() {
        val exception = ChannelWriteException("test")
        assertTrue(exception.isChannelClosedException())
    }

    @Test
    fun `test isChannelClosedException detects ClosedWriteChannelException`() {
        val exception = ClosedWriteChannelException("test")
        assertTrue(exception.isChannelClosedException())
    }

    @Test
    fun `test isChannelClosedException detects ClosedChannelException`() {
        val exception = ClosedChannelException()
        assertTrue(exception.isChannelClosedException())
    }

    @Test
    fun `test isChannelClosedException detects nested channel exceptions`() {
        val rootCause = ClosedChannelException()
        val wrappedException = RuntimeException("wrapper", rootCause)
        assertTrue(wrappedException.isChannelClosedException())
    }

    @Test
    fun `test isChannelClosedException returns false for unrelated exceptions`() {
        val exception = IllegalStateException("not a channel exception")
        assertFalse(exception.isChannelClosedException())
    }

    @Test
    fun `test isChannelClosedException handles deep cause chain`() {
        val rootCause = ClosedWriteChannelException("root")
        val level1 = RuntimeException("level1", rootCause)
        val level2 = IllegalStateException("level2", level1)
        val level3 = Exception("level3", level2)
        assertTrue(level3.isChannelClosedException())
    }
}
