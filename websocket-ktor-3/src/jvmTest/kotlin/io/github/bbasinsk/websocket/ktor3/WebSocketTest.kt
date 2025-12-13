package io.github.bbasinsk.websocket.ktor3

import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.websocket.WebSocket
import io.github.bbasinsk.websocket.WebSocketMessage
import io.ktor.client.plugins.websocket.*
import io.ktor.server.testing.*
import io.ktor.websocket.*
import kotlinx.coroutines.cancel
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class WebSocketTest {

    @Test
    fun `test simple echo WebSocket with text messages`() = testApplication {
        val api = WebSocket.route { Root / "echo" }
            .input { json { string() } }
            .output { json { string() } }

        application {
            webSocketEndpoints {
                handle(api) { _, _ ->
                    incoming.collect { message ->
                        when (message) {
                            is WebSocketMessage.Text -> send(WebSocketMessage.text("Echo: ${message.data}"))
                            is WebSocketMessage.Close -> close()
                            else -> {}
                        }
                    }
                }
            }
        }

        val client = createClient {
            install(WebSockets)
        }

        client.webSocket("/echo") {
            send(Frame.Text("\"Hello\""))
            val response = incoming.receive() as Frame.Text
            assertEquals("\"Echo: Hello\"", response.readText())
            cancel()
        }
    }

    @Test
    fun `test WebSocket with JSON serialized messages`() = testApplication {
        data class Message(val text: String, val count: Int)

        val messageSchema = Schema.record(
            Schema.field(Schema.string(), "text") { text },
            Schema.field(Schema.int(), "count") { count },
            ::Message
        )

        val api = WebSocket.route { Root / "messages" }
            .input { json { messageSchema } }
            .output { json { messageSchema } }

        application {
            webSocketEndpoints {
                handle(api) { _, _ ->
                    incoming.collect { message ->
                        when (message) {
                            is WebSocketMessage.Text -> {
                                val input = message.data
                                send(WebSocketMessage.text(Message(input.text.uppercase(), input.count + 1)))
                            }
                            is WebSocketMessage.Close -> close()
                            else -> {}
                        }
                    }
                }
            }
        }

        val client = createClient {
            install(WebSockets)
        }

        client.webSocket("/messages") {
            send(Frame.Text("""{"text":"hello","count":5}"""))
            val response = incoming.receive() as Frame.Text
            val responseText = response.readText()
            assertTrue(responseText.contains("\"text\":\"HELLO\""))
            assertTrue(responseText.contains("\"count\":6"))
            cancel()
        }
    }

    @Test
    fun `test WebSocket with path parameters`() = testApplication {
        val api = WebSocket.route { Root / "rooms" / param("roomId") { string() } }
            .input { json { string() } }
            .output { json { string() } }

        application {
            webSocketEndpoints {
                handle(api) { roomId, _ ->
                    incoming.collect { message ->
                        when (message) {
                            is WebSocketMessage.Text -> send(WebSocketMessage.text("Room $roomId: ${message.data}"))
                            is WebSocketMessage.Close -> close()
                            else -> {}
                        }
                    }
                }
            }
        }

        val client = createClient {
            install(WebSockets)
        }

        client.webSocket("/rooms/test-room") {
            send(Frame.Text("\"Hi\""))
            val response = incoming.receive() as Frame.Text
            assertEquals("\"Room test-room: Hi\"", response.readText())
            cancel()
        }
    }

    @Test
    fun `test WebSocket with plain text messages`() = testApplication {
        val api = WebSocket.route { Root / "plain" }
            .input { plain { string() } }
            .output { plain { string() } }

        application {
            webSocketEndpoints {
                handle(api) { _, _ ->
                    incoming.collect { message ->
                        when (message) {
                            is WebSocketMessage.Text -> send(WebSocketMessage.text("Received: ${message.data}"))
                            is WebSocketMessage.Close -> close()
                            else -> {}
                        }
                    }
                }
            }
        }

        val client = createClient {
            install(WebSockets)
        }

        client.webSocket("/plain") {
            send(Frame.Text("Hello World"))
            val response = incoming.receive() as Frame.Text
            assertEquals("Received: Hello World", response.readText())
            cancel()
        }
    }

    @Test
    fun `test WebSocket multiple message exchange`() = testApplication {
        val api = WebSocket.route { Root / "counter" }
            .input { json { int() } }
            .output { json { int() } }

        application {
            webSocketEndpoints {
                handle(api) { _, _ ->
                    var total = 0
                    incoming.collect { message ->
                        when (message) {
                            is WebSocketMessage.Text -> {
                                total += message.data
                                send(WebSocketMessage.text(total))
                            }
                            is WebSocketMessage.Close -> close()
                            else -> {}
                        }
                    }
                }
            }
        }

        val client = createClient {
            install(WebSockets)
        }

        client.webSocket("/counter") {
            send(Frame.Text("5"))
            assertEquals("5", (incoming.receive() as Frame.Text).readText())

            send(Frame.Text("3"))
            assertEquals("8", (incoming.receive() as Frame.Text).readText())

            send(Frame.Text("2"))
            assertEquals("10", (incoming.receive() as Frame.Text).readText())

            cancel()
        }
    }

    @Test
    fun `test WebSocket close from server`() = testApplication {
        val api = WebSocket.route { Root / "close-test" }
            .input { json { string() } }
            .output { json { string() } }

        application {
            webSocketEndpoints {
                handle(api) { _, _ ->
                    incoming.collect { message ->
                        when (message) {
                            is WebSocketMessage.Text -> {
                                if (message.data == "close") {
                                    close(1000, "Goodbye")
                                } else {
                                    send(WebSocketMessage.text("OK"))
                                }
                            }
                            else -> {}
                        }
                    }
                }
            }
        }

        val client = createClient {
            install(WebSockets)
        }

        client.webSocket("/close-test") {
            send(Frame.Text("\"hello\""))
            assertEquals("\"OK\"", (incoming.receive() as Frame.Text).readText())

            send(Frame.Text("\"close\""))
            // After server closes, we may receive a Close frame or the channel may be closed
            val frame = try {
                incoming.receive()
            } catch (_: kotlinx.coroutines.channels.ClosedReceiveChannelException) {
                null // Channel closed - connection terminated
            }
            // Either we got a Close frame or the channel was closed - both are valid
            assertTrue(frame == null || frame is Frame.Close)
        }
    }
}
