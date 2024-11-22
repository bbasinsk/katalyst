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
    val configure = httpEndpoints.configure()
    val routing = (pluginOrNull(Routing)?.apply(configure) ?: install(Routing, configure))

    return routing.apply {
        httpEndpoints.openApiBuilder?.let { openApi ->
            openapiSpecJson(
                path = openApi.jsonSpecPath,
                apis = httpEndpoints.apis(),
                info = openApi.info,
                servers = openApi.servers
            )
            stoplight(specPath = openApi.jsonSpecPath)
            swagger(specPath = openApi.jsonSpecPath)
            redoc(specPath = openApi.jsonSpecPath)
        }
    }
}
