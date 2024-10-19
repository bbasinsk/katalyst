package io.github.bbasinsk.http.ktor

import io.github.bbasinsk.http.Http
import io.github.bbasinsk.http.HttpEndpoint
import io.github.bbasinsk.http.Request
import io.github.bbasinsk.http.Response
import io.ktor.server.application.ApplicationCall
import io.ktor.server.routing.Routing
import io.ktor.util.KtorDsl

fun HttpEndpoints.httpEndpoints(builder: HttpEndpoints.() -> Unit) = apply(builder)

data class HttpEndpoints(
    val underlying: MutableList<HttpEndpoint<*, *, *, *, ApplicationCall>> = mutableListOf(),
    val openApiBuilder: OpenApiBuilder = OpenApiBuilder()
) {

    @KtorDsl
    fun openApi(builder: OpenApiBuilder.() -> Unit) {
        openApiBuilder.apply(builder)
    }

    @KtorDsl
    fun <Path, Input, Error, Output> handle(
        api: Http<Path, Input, Error, Output>,
        handler: suspend Response.Companion.(request: Request<Path, Input, ApplicationCall>) -> Response<Error, Output>
    ): HttpEndpoint<Path, Input, Error, Output, ApplicationCall> =
        HttpEndpoint(api, handler).also { underlying.add(it) }

    internal fun configure(): Routing.() -> Unit = {
        underlying.forEach { httpEndpointToRoute(it) }
    }

    internal fun apis(): List<Http<*, *, *, *>> =
        underlying.map { it.api }
}
