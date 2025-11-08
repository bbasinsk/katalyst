package io.github.bbasinsk.http.ktor3

import io.github.bbasinsk.http.Http
import io.github.bbasinsk.http.HttpEndpoint
import io.github.bbasinsk.http.Request
import io.github.bbasinsk.http.Response
import io.github.bbasinsk.http.SSEEvent
import io.github.bbasinsk.http.StreamingHttp
import io.github.bbasinsk.http.StreamingHttpEndpoint
import io.github.bbasinsk.http.StreamingResponse
import io.github.bbasinsk.http.openapi.Info
import io.github.bbasinsk.http.openapi.Server
import io.ktor.server.routing.RoutingCall
import io.ktor.server.routing.RoutingRoot
import io.ktor.utils.io.KtorDsl
import kotlinx.coroutines.flow.Flow

@Deprecated("Use HttpEndpointGroup instead")
fun HttpEndpoints.httpEndpoints(vararg tags: String, builder: HttpEndpoints.() -> Unit) =
    copy(tags = this.tags + tags.toList()).apply(builder)

data class HttpEndpoints(
    val underlying: MutableList<HttpEndpoint<*, *, *, *, RoutingCall>> = mutableListOf(),
    val streamingUnderlying: MutableList<StreamingHttpEndpoint<*, *, *, *, RoutingCall>> = mutableListOf(),
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

    /**
     * Registers a streaming SSE endpoint.
     */
    @KtorDsl
    fun <Path, Input, Error, Output> handleStreaming(
        api: StreamingHttp<Path, Input, Error, Output>,
        handler: suspend StreamingResponse.Companion.(request: Request<Path, Input, RoutingCall>) -> StreamingResponse<Error, Output>
    ): StreamingHttpEndpoint<Path, Input, Error, Output, RoutingCall> =
        StreamingHttpEndpoint(
            api = api.tag(*tags.toTypedArray()),
            handle = handler
        ).also {
            streamingUnderlying.add(it)
        }

    /**
     * Registers a streaming SSE endpoint that returns a Flow of SSE events.
     */
    @KtorDsl
    fun <Path, Input, Output> handleStream(
        api: StreamingHttp<Path, Input, *, Output>,
        handler: suspend (request: Request<Path, Input, RoutingCall>) -> Flow<SSEEvent<Output>>
    ): StreamingHttpEndpoint<Path, Input, Nothing, Output, RoutingCall> =
        StreamingHttpEndpoint(
            api = api.tag(*tags.toTypedArray()) as StreamingHttp<Path, Input, Nothing, Output>,
            handle = { request -> StreamingResponse.success(handler(request)) }
        ).also {
            streamingUnderlying.add(it)
        }

    /**
     * Registers a streaming SSE endpoint that returns a Flow of data (auto-wrapped in SSEEvent).
     */
    @KtorDsl
    fun <Path, Input, Output> handleStreamData(
        api: StreamingHttp<Path, Input, *, Output>,
        handler: suspend (request: Request<Path, Input, RoutingCall>) -> Flow<Output>
    ): StreamingHttpEndpoint<Path, Input, Nothing, Output, RoutingCall> =
        StreamingHttpEndpoint(
            api = api.tag(*tags.toTypedArray()) as StreamingHttp<Path, Input, Nothing, Output>,
            handle = { request -> StreamingResponse.successData(handler(request)) }
        ).also {
            streamingUnderlying.add(it)
        }

    internal fun configure(): RoutingRoot.() -> Unit = {
        underlying.forEach { httpEndpointToRoute(it) }
        streamingUnderlying.forEach { streamingHttpEndpointToRoute(it) }
    }

    internal fun apis(): List<Http<*, *, *, *>> =
        underlying.map { it.api }
}
