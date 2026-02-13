@file:OptIn(ExperimentalUuidApi::class)

package io.github.bbasinsk.http.ktor3

import io.github.bbasinsk.http.Http
import io.github.bbasinsk.http.HttpEndpointGroup
import io.github.bbasinsk.http.Response
import io.github.bbasinsk.http.openapi.Info
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

        endpoints {
            openApi(
                jsonSpecPath = "/openapi.json",
                info = Info(
                    title = "Person API",
                    version = "1.0.0",
                    description = "Molestiae occaecati molestiae at aut et consequatur ut facere commodi et eos fugiat. Vitae et labore est ex. Facere sed numquam blanditiis vel dolores sint sint ut quae officiis et. Alias dolorem sit odit aut animi ut eos consequatur sit quod suscipit est eum. Modi quo aliquid impedit architecto et laborum odit non non est et. Possimus in animi sunt maxime impedit ex ut odio qui odit laborum consequatur autem omnis quaerat. Nam dicta commodi rerum fuga ducimus et quas aut molestias illum. Ex asperiores perspiciatis facere voluptate est architecto non est ex quos unde adipisci vel ratione. Aut a ea placeat inventore atque id quisquam velit facere."
                ),
                servers = listOf(
                    Server(url = "http://localhost:33333")
                )
            )
            multipartEndpoints()
            personEndpoints(domainService)
            sseEndpoints()
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
