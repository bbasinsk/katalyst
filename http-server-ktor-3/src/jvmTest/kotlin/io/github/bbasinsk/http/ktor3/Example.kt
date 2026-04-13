@file:OptIn(ExperimentalUuidApi::class)

package io.github.bbasinsk.http.ktor3

import io.github.bbasinsk.http.ContentType
import io.github.bbasinsk.http.Http
import io.github.bbasinsk.http.HttpEndpointGroup
import io.github.bbasinsk.http.Response
import io.github.bbasinsk.http.openapi.Info
import io.github.bbasinsk.http.openapi.OpenapiDocs
import io.github.bbasinsk.http.openapi.Server
import io.github.bbasinsk.schema.Schema
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.http.HttpRequestLifecycle
import io.ktor.server.plugins.calllogging.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import kotlin.random.Random
import kotlin.random.nextInt
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid


fun main() {
    embeddedServer(CIO, port = 33333) {
        install(CallLogging)
        install(ContentNegotiation) {
            json()
        }
        install(HttpRequestLifecycle) {
            cancelCallOnClose = true
        }

        val domainService = {
            Person(
                id = PersonId(Uuid.random()),
                name = "John Doe",
                age = Random.nextInt(0..100)
            )
        }

        // Docs routes are plain Http definitions — compose with .auth/.tag like anything else.
        val openApiFile = Http.get { Root / "openapi.json" }
            .output { status(Ok) { bytes(ContentType.Json) } }
            .tag("docs")
        val swaggerUi = Http.get { Root / "swagger" }
            .output { status(Ok) { html() } }
            .tag("docs")
        val redocUi = Http.get { Root / "redoc" }
            .output { status(Ok) { html() } }
            .tag("docs")
        val stoplightUi = Http.get { Root / "stoplight" }
            .output { status(Ok) { html() } }
            .tag("docs")

        val docs = OpenapiDocs(
            spec = openApiFile,
            info = Info(
                title = "Person API",
                version = "1.0.0",
                description = "Example API."
            ),
            servers = listOf(Server(url = "http://localhost:33333")),
        )

        endpoints {
            multipartEndpoints()
            personEndpoints(domainService)
            sseEndpoints()

            handle(openApiFile) { _ -> Response.success(docs.specJson(apis())) }
            handle(swaggerUi)   { _ -> Response.success(docs.swaggerHtml()) }
            handle(redocUi)     { _ -> Response.success(docs.redocHtml()) }
            handle(stoplightUi) { _ -> Response.success(docs.stoplightHtml()) }
        }
    }.start(wait = true)
}

data class HeartbeatEvent(val count: Long, val timestamp: Long)

object SSEEndpoints : HttpEndpointGroup("SSE") {
    private val heartbeatSchema = Schema.record(
        Schema.field(Schema.long(), "count") { count },
        Schema.field(Schema.long(), "timestamp") { timestamp },
        ::HeartbeatEvent
    )

    val longLivedStream = http {
        get { Root / "sse" / "heartbeat" }
            .output { sse { json { heartbeatSchema } } }
    }
}

fun HttpEndpoints.sseEndpoints() {
    handle(SSEEndpoints.longLivedStream) {
        Response.streamingSuccessData(flow {
            var count = 0L
            while (currentCoroutineContext().isActive) {
                emit(HeartbeatEvent(count++, System.currentTimeMillis()))
                delay(1000)
            }
        })
    }
}
