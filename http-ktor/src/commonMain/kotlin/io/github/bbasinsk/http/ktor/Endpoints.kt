package io.github.bbasinsk.http.ktor

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.application.pluginOrNull
import io.ktor.server.routing.Routing
import io.ktor.util.KtorDsl

@KtorDsl
fun Application.endpoints(
    builder: HttpEndpoints.() -> Unit
): Routing {
    val httpEndpoints = HttpEndpoints().apply(builder)
    val jsonSpecPath = httpEndpoints.openApiBuilder.jsonSpecPath

    val configure = httpEndpoints.configure()
    val routing = (pluginOrNull(Routing)?.apply(configure) ?: install(Routing, configure))

    return routing.apply {
        openapiSpecJson(
            path = jsonSpecPath,
            apis = httpEndpoints.apis(),
            info = httpEndpoints.openApiBuilder.info,
            servers = httpEndpoints.openApiBuilder.servers
        )
        stoplight(specPath = jsonSpecPath)
        swagger(specPath = jsonSpecPath)
        redoc(specPath = jsonSpecPath)
    }
}
