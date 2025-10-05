@file:OptIn(ExperimentalUuidApi::class)

package io.github.bbasinsk.http.ktor3

import io.github.bbasinsk.http.openapi.Info
import io.github.bbasinsk.http.openapi.Server
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.plugins.calllogging.*
import io.ktor.server.plugins.contentnegotiation.*
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
        }
    }.start(wait = true)
}
