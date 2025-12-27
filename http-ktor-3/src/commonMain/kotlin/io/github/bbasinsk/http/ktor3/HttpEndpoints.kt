package io.github.bbasinsk.http.ktor3

import io.github.bbasinsk.http.AuthValidator
import io.github.bbasinsk.http.Http
import io.github.bbasinsk.http.HttpEndpoint
import io.github.bbasinsk.http.Request
import io.github.bbasinsk.http.Response
import io.github.bbasinsk.http.openapi.Info
import io.github.bbasinsk.http.openapi.Server
import io.ktor.server.routing.RoutingCall
import io.ktor.server.routing.RoutingRoot
import io.ktor.utils.io.KtorDsl

@Deprecated("Use HttpEndpointGroup instead")
fun HttpEndpoints.httpEndpoints(vararg tags: String, builder: HttpEndpoints.() -> Unit) =
    copy(tags = this.tags + tags.toList()).apply(builder)

data class HttpEndpoints(
    val underlying: MutableList<HttpEndpoint<*, *, *, *, *, RoutingCall>> = mutableListOf(),
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

    // Handle for endpoints with Auth = Unit (no auth required)
    @KtorDsl
    fun <Path, Input, Error, Output> handle(
        api: Http<Path, Input, Error, Output, Unit>,
        handler: suspend Response.Companion.(request: Request<Path, Input, Unit, RoutingCall>) -> Response<Error, Output>
    ): HttpEndpoint<Path, Input, Error, Output, Unit, RoutingCall> =
        HttpEndpoint(
            api = api.tag(*tags.toTypedArray()),
            validator = null,
            handle = handler
        ).also {
            underlying.add(it)
        }

    // Handle for endpoints with auth required - validator must be provided
    @KtorDsl
    fun <Path, Input, Error, Output, Auth> handle(
        api: Http<Path, Input, Error, Output, Auth>,
        validator: AuthValidator<Auth>,
        handler: suspend Response.Companion.(request: Request<Path, Input, Auth, RoutingCall>) -> Response<Error, Output>
    ): HttpEndpoint<Path, Input, Error, Output, Auth, RoutingCall> =
        HttpEndpoint(
            api = api.tag(*tags.toTypedArray()),
            validator = validator,
            handle = handler
        ).also {
            underlying.add(it)
        }

    // Handle for endpoints with optional auth - validator provides non-null type, framework makes it nullable
    @KtorDsl
    @JvmName("handleOptionalAuth")
    fun <Path, Input, Error, Output, Auth> handle(
        api: Http<Path, Input, Error, Output, Auth?>,
        validator: AuthValidator<Auth?>,
        handler: suspend Response.Companion.(request: Request<Path, Input, Auth?, RoutingCall>) -> Response<Error, Output>
    ): HttpEndpoint<Path, Input, Error, Output, Auth?, RoutingCall> =
        HttpEndpoint(
            api = api.tag(*tags.toTypedArray()),
            validator = validator,
            handle = handler
        ).also {
            underlying.add(it)
        }

    internal fun configure(): RoutingRoot.() -> Unit = {
        underlying.forEach { httpEndpointToRoute(it) }
    }

    internal fun apis(): List<Http<*, *, *, *, *>> =
        underlying.map { it.api }
}
