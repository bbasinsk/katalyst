package io.github.bbasinsk.http.ktor3

import io.github.bbasinsk.http.Http
import io.github.bbasinsk.http.HttpEndpoint
import io.github.bbasinsk.http.Request
import io.github.bbasinsk.http.Response
import io.github.bbasinsk.http.openapi.Info
import io.github.bbasinsk.http.openapi.Server
import io.ktor.server.routing.RoutingCall
import io.ktor.server.routing.RoutingRoot
import io.ktor.utils.io.KtorDsl

fun HttpEndpoints.httpEndpoints(vararg tags: String, builder: HttpEndpoints.() -> Unit) =
    copy(tags = this.tags + tags.toList()).apply(builder)

data class HttpEndpoints(
    val underlying: MutableList<HttpEndpoint<*, *, *, *, RoutingCall>> = mutableListOf(),
    var openApiBuilder: OpenApiBuilder? = null,
    val tags: List<String> = emptyList(),
) {

    fun openApi(
        info: Info,
        servers: List<Server> = emptyList(),
        jsonSpecPath: String = "/openapi.json"
    ) {
        openApiBuilder = OpenApiBuilder(jsonSpecPath, info, servers)
    }

    @KtorDsl
    fun <Path, Input, Error, Output> handle(
        api: Http<Path, Input, Error, Output>,
        handler: suspend Response.Companion.(request: Request<Path, Input, RoutingCall>) -> Response<Error, Output>
    ): HttpEndpoint<Path, Input, Error, Output, RoutingCall> =
        HttpEndpoint(
            api = api.tag(*tags.toTypedArray()),
            handle = handler
        ).also {
            underlying.add(it)
        }

    internal fun configure(): RoutingRoot.() -> Unit = {
        underlying.forEach { httpEndpointToRoute(it) }
    }

    internal fun apis(): List<Http<*, *, *, *>> =
        underlying.map { it.api }
}
