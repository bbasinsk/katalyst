package io.github.bbasinsk.websocket.ktor3

import io.github.bbasinsk.websocket.WebSocket
import io.github.bbasinsk.websocket.WebSocketEndpoint
import io.github.bbasinsk.websocket.WebSocketScope
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.application.pluginOrNull
import io.ktor.server.routing.Routing
import io.ktor.server.routing.RoutingCall
import io.ktor.server.routing.RoutingRoot
import io.ktor.server.websocket.WebSockets
import io.ktor.utils.io.KtorDsl

data class WebSocketEndpoints(
    val underlying: MutableList<WebSocketEndpoint<*, *, *, RoutingCall>> = mutableListOf(),
    val tags: List<String> = emptyList()
) {
    @KtorDsl
    fun <Params, In, Out> handle(
        api: WebSocket<Params, In, Out>,
        handler: suspend WebSocketScope<In, Out>.(Params, RoutingCall) -> Unit
    ): WebSocketEndpoint<Params, In, Out, RoutingCall> =
        WebSocketEndpoint(
            api = api.tag(*tags.toTypedArray()),
            handler = handler
        ).also {
            underlying.add(it)
        }

    internal fun configure(): RoutingRoot.() -> Unit = {
        underlying.forEach { webSocketEndpointToRoute(it) }
    }
}

@KtorDsl
fun Application.webSocketEndpoints(
    builder: WebSocketEndpoints.() -> Unit
): Routing {
    val endpoints = WebSocketEndpoints().apply(builder)

    // Install WebSockets plugin if not already installed
    pluginOrNull(WebSockets) ?: install(WebSockets)

    val configure = endpoints.configure()
    @Suppress("UNCHECKED_CAST")
    return pluginOrNull(RoutingRoot)?.apply(configure) ?: install(RoutingRoot, configure as Routing.() -> Unit)
}
