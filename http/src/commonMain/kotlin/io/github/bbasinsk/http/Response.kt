package io.github.bbasinsk.http

import kotlinx.coroutines.flow.Flow

sealed interface Response<Error, Output> {
    data class Error<E, A>(val value: E) : Response<E, A>
    data class Success<E, A>(val value: A) : Response<E, A>

    /**
     * A streaming error response using Server-Sent Events.
     */
    data class StreamingError<E, A>(val events: Flow<SSEEvent<E>>) : Response<E, A>

    /**
     * A streaming success response using Server-Sent Events.
     */
    data class StreamingSuccess<E, A>(val events: Flow<SSEEvent<A>>) : Response<E, A>

    companion object {
        fun <E, A> success(value: A): Response<E, A> = Success(value)
        fun <E, A> error(value: E): Response<E, A> = Error(value)
        fun <E> success(): Response<E, Nothing?> = Success(null)
        fun <A> error(): Response<Nothing?, A> = Error(null)

        /**
         * Creates a streaming success response from a flow of SSE events.
         */
        fun <E, A> streamingSuccess(events: Flow<SSEEvent<A>>): Response<E, A> = StreamingSuccess(events)

        /**
         * Creates a streaming error response from a flow of SSE events.
         */
        fun <E, A> streamingError(events: Flow<SSEEvent<E>>): Response<E, A> = StreamingError(events)

        /**
         * Creates a streaming success response from a flow of data values.
         * Automatically wraps each value in an SSEEvent.
         */
        fun <E, A> streamingSuccessData(data: Flow<A>): Response<E, A> =
            StreamingSuccess(kotlinx.coroutines.flow.map(data) { SSEEvent.data(it) })

        /**
         * Creates a streaming error response from a flow of error values.
         * Automatically wraps each value in an SSEEvent.
         */
        fun <E, A> streamingErrorData(errors: Flow<E>): Response<E, A> =
            StreamingError(kotlinx.coroutines.flow.map(errors) { SSEEvent.data(it) })
    }
}
