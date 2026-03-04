package io.github.bbasinsk.http.ktor3

import io.github.bbasinsk.http.Http
import io.github.bbasinsk.http.Response
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.coroutines.runBlocking
import java.net.HttpURLConnection
import java.net.URI
import kotlin.test.Test
import kotlin.test.assertEquals

class H2cTest {

    private fun withH2cServer(block: (port: Int) -> Unit) {
        val api = Http.get { Root / "health" }
            .output { status(Ok) { plain { string() } } }

        val server = embeddedServer(Netty, configure = {
            connectors.add(EngineConnectorBuilder().apply { port = 0 })
            enableHttp2 = true
            enableH2c = true
        }) {
            endpoints {
                handle(api) {
                    Response.success("OK")
                }
            }
        }.start(wait = false)

        try {
            val port = runBlocking { server.engine.resolvedConnectors().first().port }
            block(port)
        } finally {
            server.stop(0, 0)
        }
    }

    @Test
    fun `GET with empty body works over HTTP 1_1`() = withH2cServer { port ->
        val connection = URI("http://localhost:$port/health").toURL()
            .openConnection() as HttpURLConnection
        connection.connectTimeout = 5_000
        connection.readTimeout = 5_000
        connection.requestMethod = "GET"

        assertEquals(200, connection.responseCode)
        assertEquals("OK", connection.inputStream.bufferedReader().readText())
    }

    @Test
    fun `GET with empty body works over h2c prior knowledge`() = withH2cServer { port ->
        val process = ProcessBuilder(
            "curl", "-s", "-w", "%{http_code}",
            "--max-time", "5",
            "--http2-prior-knowledge",
            "http://localhost:$port/health"
        ).redirectErrorStream(true).start()

        val output = process.inputStream.bufferedReader().readText().trim()
        val exitCode = process.waitFor()

        assertEquals(0, exitCode, "curl timed out or failed (exit=$exitCode, output=$output)")
        assertEquals("OK200", output)
    }

    @Test
    fun `GET with empty body works over h2c upgrade`() = withH2cServer { port ->
        val process = ProcessBuilder(
            "curl", "-s", "-w", "%{http_code}",
            "--max-time", "5",
            "--http2",
            "http://localhost:$port/health"
        ).redirectErrorStream(true).start()

        val output = process.inputStream.bufferedReader().readText().trim()
        val exitCode = process.waitFor()

        assertEquals(0, exitCode, "curl timed out or failed (exit=$exitCode, output=$output)")
        assertEquals("OK200", output)
    }
}
