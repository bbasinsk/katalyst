package io.github.bbasinsk.http.ktor3

import io.github.bbasinsk.http.AuthHandler
import io.github.bbasinsk.http.Http
import io.github.bbasinsk.http.HttpEndpoint
import io.github.bbasinsk.http.Request
import io.github.bbasinsk.http.Response
import io.github.bbasinsk.http.optional
import io.ktor.server.routing.RoutingCall
import io.ktor.server.routing.RoutingRoot
import io.ktor.utils.io.KtorDsl

@Deprecated("Use HttpEndpointGroup instead")
fun HttpEndpoints.httpEndpoints(vararg tags: String, builder: HttpEndpoints.() -> Unit) =
    copy(tags = this.tags + tags.toList()).apply(builder)

data class HttpEndpoints(
    val underlying: MutableList<HttpEndpoint<*, *, *, *, *, RoutingCall>> = mutableListOf(),
    val tags: List<String> = emptyList(),
) {

    // Handle for endpoints with Auth = Unit (no auth required)
    @KtorDsl
    fun <Path, Input, Error, Output> handle(
        api: Http<Path, Input, Error, Output, Unit>,
        handler: suspend Response.Companion.(request: Request<Path, Input, Unit, RoutingCall>) -> Response<Error, Output>
    ): HttpEndpoint<Path, Input, Error, Output, Unit, RoutingCall> =
        HttpEndpoint(
            api = api.tag(*tags.toTypedArray()),
            authHandler = null,
            handle = handler
        ).also {
            underlying.add(it)
        }

    // Handle for endpoints with auth required - authHandler must be provided
    @KtorDsl
    @JvmName("handleRequiredAuth")
    fun <Path, Input, Error, Output, Auth> handle(
        api: Http<Path, Input, Error, Output, Auth>,
        authHandler: AuthHandler<Auth>,
        handler: suspend Response.Companion.(request: Request<Path, Input, Auth, RoutingCall>) -> Response<Error, Output>
    ): HttpEndpoint<Path, Input, Error, Output, Auth, RoutingCall> =
        HttpEndpoint(
            api = api.tag(*tags.toTypedArray()),
            authHandler = authHandler,
            handle = handler
        ).also {
            underlying.add(it)
        }

    // Handle for endpoints with auth optional - authHandler must be provided
    @KtorDsl
    @JvmName("handleOptionalAuth")
    fun <Path, Input, Error, Output, Auth> handle(
        api: Http<Path, Input, Error, Output, Auth?>,
        authHandler: AuthHandler<Auth>,
        handler: suspend Response.Companion.(request: Request<Path, Input, Auth?, RoutingCall>) -> Response<Error, Output>
    ): HttpEndpoint<Path, Input, Error, Output, Auth?, RoutingCall> =
        HttpEndpoint(
            api = api.tag(*tags.toTypedArray()),
            authHandler = authHandler.optional(),
            handle = handler
        ).also {
            underlying.add(it)
        }

    internal fun configure(): RoutingRoot.() -> Unit = {
        underlying.forEach { httpEndpointToRoute(it) }
    }

    /**
     * Snapshot of every [Http] definition currently registered in this block. Safe to call
     * from inside a handler lambda — the list is captured at request time, so newly-added
     * endpoints are reflected if the block is still being built.
     *
     * Typical use is in an OpenAPI spec handler:
     * ```
     * handle(openApiFile, basicAuth) { _ ->
     *     Response.success(renderOpenapiJson(apis(), info, servers))
     * }
     * ```
     */
    fun apis(): List<Http<*, *, *, *, *>> =
        underlying.map { it.api }
}
