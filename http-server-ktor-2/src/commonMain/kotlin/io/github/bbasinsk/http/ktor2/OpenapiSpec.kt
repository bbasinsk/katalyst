package io.github.bbasinsk.http.ktor2

import io.github.bbasinsk.http.Http
import io.github.bbasinsk.http.openapi.Info
import io.github.bbasinsk.http.openapi.OpenApiJson
import io.github.bbasinsk.http.openapi.Server
import io.github.bbasinsk.http.openapi.toOpenApiSpec
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import kotlinx.serialization.json.encodeToJsonElement

fun Route.openapiSpecJson(
    path: String,
    apis: List<Http<*, *, *, *, *>>,
    info: Info,
    servers: List<Server>
) {
    get(path) {
        call.respond(
            OpenApiJson.encodeToJsonElement(
                apis.toOpenApiSpec(info, servers)
            )
        )
    }
}
