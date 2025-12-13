package io.github.bbasinsk.http

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

sealed interface Response<Error, Output> {
    data class CompletionError<E, A>(val value: E) : Response<E, A>
    data class CompletionSuccess<E, A>(val value: A) : Response<E, A>

    /**
     * A streaming error response using Server-Sent Events.
     */
    data class StreamingError<E, A>(val events: Flow<SSEEvent<E>>) : Response<E, A>

    /**
     * A streaming success response using Server-Sent Events.
     */
    data class StreamingSuccess<E, A>(val events: Flow<SSEEvent<A>>) : Response<E, A>

    companion object {
        fun <E, A> success(value: A): Response<E, A> = CompletionSuccess(value)
        fun <E, A> error(value: E): Response<E, A> = CompletionError(value)
        fun <E> success(): Response<E, Nothing?> = CompletionSuccess(null)
        fun <A> error(): Response<Nothing?, A> = CompletionError(null)

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
        fun <E, A> streamingSuccessData(data: Flow<A>): Response<E, A> = StreamingSuccess(data.map { SSEEvent.data(it) })

        /**
         * Creates a streaming error response from a flow of error values.
         * Automatically wraps each value in an SSEEvent.
         */
        fun <E, A> streamingErrorData(errors: Flow<E>): Response<E, A> = StreamingError(errors.map { SSEEvent.data(it) })
    }
}
