package io.github.bbasinsk.http

import kotlinx.coroutines.flow.Flow

/**
 * Represents a streaming HTTP endpoint definition with Server-Sent Events (SSE) support.
 *
 * StreamingHttp is similar to Http but designed for endpoints that stream data to clients
 * using Server-Sent Events. Instead of a single response, the server sends a flow of events.
 *
 * @param Params Type of path, query, and header parameters
 * @param Input Type of request body (if any)
 * @param Error Type of error events that can be streamed
 * @param Output Type of success events that are streamed
 */
data class StreamingHttp<Params, Input, Error, Output>(
    val method: HttpMethod,
    val params: ParamsSchema<Params>,
    val input: BodySchema<Input>,
    val error: SSEResponseSchema<Error>,
    val output: SSEResponseSchema<Output>,
    val metadata: HttpMetadata
) {

    fun summary(summary: String): StreamingHttp<Params, Input, Error, Output> =
        copy(metadata = metadata.copy(summary = summary))

    fun deprecated(reason: String): StreamingHttp<Params, Input, Error, Output> =
        copy(metadata = metadata.copy(deprecatedReason = reason))

    fun tag(vararg tag: String): StreamingHttp<Params, Input, Error, Output> =
        copy(metadata = metadata.copy(tags = metadata.tags + tag.toList()))

    fun <Params2> query(query: ParamSchema.Companion.() -> ParamSchema<Params2>): StreamingHttp<Pair<Params, Params2>, Input, Error, Output> =
        StreamingHttp(
            method = method,
            params = params.withQuery(ParamSchema.query()),
            input = input,
            error = error,
            output = output,
            metadata = metadata
        )

    fun <Params2> header(header: ParamSchema.Companion.() -> ParamSchema<Params2>): StreamingHttp<Pair<Params, Params2>, Input, Error, Output> =
        StreamingHttp(
            method = method,
            params = params.withHeader(ParamSchema.header()),
            input = input,
            error = error,
            output = output,
            metadata = metadata
        )

    fun <Input2> input(input: BodySchema.Companion.() -> BodySchema<Input2>): StreamingHttp<Params, Input2, Error, Output> =
        StreamingHttp(
            method = method,
            params = params,
            input = BodySchema.input(),
            error = error,
            output = output,
            metadata = metadata
        )

    fun <Output2> output(output: SSEResponseSchema<Output2>): StreamingHttp<Params, Input, Error, Output2> =
        StreamingHttp(
            method = method,
            params = params,
            input = input,
            error = error,
            output = output,
            metadata = metadata
        )

    fun <Error2> error(error: SSEResponseSchema<Error2>): StreamingHttp<Params, Input, Error2, Output> =
        StreamingHttp(
            method = method,
            params = params,
            input = input,
            error = error,
            output = output,
            metadata = metadata
        )

    companion object {

        fun <Path> get(path: PathSchema.Companion.() -> PathSchema<Path>): StreamingHttp<Path, Nothing?, Nothing, Nothing> =
            StreamingHttp(
                method = HttpMethod.GET,
                params = PathSchema.path(),
                input = BodySchema.empty(),
                error = SSEResponseSchema(BodySchema.empty()),
                output = SSEResponseSchema(BodySchema.empty()),
                metadata = HttpMetadata()
            )

        fun <Path> post(path: PathSchema.Companion.() -> PathSchema<Path>): StreamingHttp<Path, Nothing?, Nothing, Nothing> =
            StreamingHttp(
                method = HttpMethod.POST,
                params = PathSchema.path(),
                input = BodySchema.empty(),
                error = SSEResponseSchema(BodySchema.empty()),
                output = SSEResponseSchema(BodySchema.empty()),
                metadata = HttpMetadata()
            )

        fun <Path> put(path: PathSchema.Companion.() -> PathSchema<Path>): StreamingHttp<Path, Nothing?, Nothing, Nothing> =
            StreamingHttp(
                method = HttpMethod.PUT,
                params = PathSchema.path(),
                input = BodySchema.empty(),
                error = SSEResponseSchema(BodySchema.empty()),
                output = SSEResponseSchema(BodySchema.empty()),
                metadata = HttpMetadata()
            )

        fun <Path> delete(path: PathSchema.Companion.() -> PathSchema<Path>): StreamingHttp<Path, Nothing?, Nothing, Nothing> =
            StreamingHttp(
                method = HttpMethod.DELETE,
                params = PathSchema.path(),
                input = BodySchema.empty(),
                error = SSEResponseSchema(BodySchema.empty()),
                output = SSEResponseSchema(BodySchema.empty()),
                metadata = HttpMetadata()
            )
    }
}

/**
 * Represents a streaming response that can either be a flow of success events or error events.
 *
 * @param Error Type of error events
 * @param Output Type of success events
 */
sealed interface StreamingResponse<Error, Output> {
    /**
     * A stream of error events.
     */
    data class Error<E, A>(val events: Flow<SSEEvent<E>>) : StreamingResponse<E, A>

    /**
     * A stream of success events.
     */
    data class Success<E, A>(val events: Flow<SSEEvent<A>>) : StreamingResponse<E, A>

    companion object {
        /**
         * Creates a success streaming response from a flow of events.
         */
        fun <E, A> success(events: Flow<SSEEvent<A>>): StreamingResponse<E, A> = Success(events)

        /**
         * Creates an error streaming response from a flow of events.
         */
        fun <E, A> error(events: Flow<SSEEvent<E>>): StreamingResponse<E, A> = Error(events)

        /**
         * Creates a success streaming response from a flow of data values.
         * Automatically wraps each value in an SSEEvent.
         */
        fun <E, A> successData(events: Flow<A>): StreamingResponse<E, A> =
            Success(kotlinx.coroutines.flow.map(events) { SSEEvent.data(it) })

        /**
         * Creates an error streaming response from a flow of error values.
         * Automatically wraps each value in an SSEEvent.
         */
        fun <E, A> errorData(events: Flow<E>): StreamingResponse<E, A> =
            Error(kotlinx.coroutines.flow.map(events) { SSEEvent.data(it) })
    }
}

/**
 * A streaming HTTP endpoint with a handler that returns a flow of SSE events.
 *
 * @param Params Type of path, query, and header parameters
 * @param Input Type of request body
 * @param Error Type of error events
 * @param Output Type of success events
 * @param Context Type of request context
 */
data class StreamingHttpEndpoint<Params, Input, Error, Output, Context>(
    val api: StreamingHttp<Params, Input, Error, Output>,
    val handle: suspend StreamingResponse.Companion.(request: Request<Params, Input, Context>) -> StreamingResponse<Error, Output>
)

/**
 * Extension function to create a streaming endpoint from a StreamingHttp definition.
 */
fun <Params, Input, Error, Output, Context> StreamingHttp<Params, Input, Error, Output>.endpoint(
    handle: suspend StreamingResponse.Companion.(request: Request<Params, Input, Context>) -> StreamingResponse<Error, Output>
): StreamingHttpEndpoint<Params, Input, Error, Output, Context> =
    StreamingHttpEndpoint(api = this, handle = handle)

/**
 * Extension function to create a streaming endpoint that only returns success events.
 */
fun <Params, Input, Output, Context> StreamingHttp<Params, Input, *, Output>.streamEndpoint(
    handle: suspend (request: Request<Params, Input, Context>) -> Flow<SSEEvent<Output>>
): StreamingHttpEndpoint<Params, Input, Nothing, Output, Context> =
    StreamingHttpEndpoint(
        api = this as StreamingHttp<Params, Input, Nothing, Output>,
        handle = { request -> StreamingResponse.success(handle(request)) }
    )

/**
 * Extension function to create a streaming endpoint that returns data events (auto-wrapped in SSEEvent).
 */
fun <Params, Input, Output, Context> StreamingHttp<Params, Input, *, Output>.streamDataEndpoint(
    handle: suspend (request: Request<Params, Input, Context>) -> Flow<Output>
): StreamingHttpEndpoint<Params, Input, Nothing, Output, Context> =
    StreamingHttpEndpoint(
        api = this as StreamingHttp<Params, Input, Nothing, Output>,
        handle = { request -> StreamingResponse.successData(handle(request)) }
    )
