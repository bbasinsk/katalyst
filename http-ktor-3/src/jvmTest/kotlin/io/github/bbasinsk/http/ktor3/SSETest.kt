package io.github.bbasinsk.http.ktor3

import io.github.bbasinsk.http.BodySchema
import io.github.bbasinsk.http.PathSchema
import io.github.bbasinsk.http.SSEEvent
import io.github.bbasinsk.http.SSEResponseSchema
import io.github.bbasinsk.http.StreamingHttp
import io.github.bbasinsk.schema.Schema
import io.ktor.client.plugins.sse.*
import io.ktor.client.request.*
import io.ktor.server.testing.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlin.test.Test
import kotlin.test.assertEquals
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

        val api = StreamingHttp.get { PathSchema.Root / "stream" }
            .output(SSEResponseSchema(BodySchema.json { messageSchema }))

        application {
            endpoints {
                handleStreamData(api) {
                    flow {
                        repeat(5) { i ->
                            emit(Message("Message $i", System.currentTimeMillis()))
                            delay(10)
                        }
                    }
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
                    close()
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
        val api = StreamingHttp.get { PathSchema.Root / "events" }
            .output(SSEResponseSchema(BodySchema.json { Schema.string() }))

        application {
            endpoints {
                handleStream(api) {
                    flow {
                        emit(SSEEvent.typed("greeting", "Hello"))
                        delay(10)
                        emit(SSEEvent.typed("farewell", "Goodbye"))
                    }
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
                    close()
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
        val api = StreamingHttp.get { PathSchema.Root / "numbered" }
            .output(SSEResponseSchema(BodySchema.json { Schema.int() }))

        application {
            endpoints {
                handleStream(api) {
                    flow {
                        repeat(3) { i ->
                            emit(SSEEvent(
                                data = i * 10,
                                id = "event-$i"
                            ))
                            delay(10)
                        }
                    }
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
                    close()
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
        val api = StreamingHttp.get { PathSchema.Root / "retry" }
            .output(SSEResponseSchema(BodySchema.json { Schema.string() }, defaultRetry = 3000))

        application {
            endpoints {
                handleStream(api) {
                    flow {
                        emit(SSEEvent(
                            data = "test",
                            retry = 5000L
                        ))
                    }
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
                close()
            }
        }

        assertEquals(1, receivedEvents.size)
        assertEquals(5000L, receivedEvents[0].retry)
    }

    @Test
    fun `test SSE stream with comments`() = testApplication {
        val api = StreamingHttp.get { PathSchema.Root / "comments" }
            .output(SSEResponseSchema(BodySchema.json { Schema.string() }))

        application {
            endpoints {
                handleStream(api) {
                    flow {
                        emit(SSEEvent(
                            data = "first",
                            comment = "This is a comment"
                        ))
                        delay(10)
                        emit(SSEEvent(
                            data = "second"
                        ))
                    }
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
                    close()
                }
            }
        }

        assertEquals(2, receivedEvents.size)
        assertEquals("This is a comment", receivedEvents[0].comments)
        assertEquals("\"first\"", receivedEvents[0].data)
    }

    @Test
    fun `test SSE stream with plain text content`() = testApplication {
        val api = StreamingHttp.get { PathSchema.Root / "plain" }
            .output(SSEResponseSchema(BodySchema.plain { Schema.string() }))

        application {
            endpoints {
                handleStreamData(api) {
                    flow {
                        emit("Plain text message 1")
                        delay(10)
                        emit("Plain text message 2")
                    }
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
                    close()
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

        val api = StreamingHttp.get { PathSchema.Root / "users" }
            .output(SSEResponseSchema(BodySchema.json { userSchema }))

        application {
            endpoints {
                handleStreamData(api) {
                    flow {
                        emit(User(1, "Alice", "alice@example.com"))
                        delay(10)
                        emit(User(2, "Bob", "bob@example.com"))
                        delay(10)
                        emit(User(3, "Charlie", "charlie@example.com"))
                    }
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
                    close()
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
        val api = StreamingHttp.get { PathSchema.Root / "keepalive" }
            .output(SSEResponseSchema(BodySchema.json { Schema.string() }))

        application {
            endpoints {
                handleStream(api) {
                    flow {
                        emit(SSEEvent.data("start"))
                        delay(10)
                        emit(SSEEvent.keepalive("heartbeat"))
                        delay(10)
                        emit(SSEEvent.data("end"))
                    }
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
                    close()
                }
            }
        }

        // All 3 events should be received
        assertEquals(3, receivedEvents.size)
    }
}
