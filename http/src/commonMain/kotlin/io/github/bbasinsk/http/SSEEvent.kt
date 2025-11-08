package io.github.bbasinsk.http

/**
 * Represents a Server-Sent Event (SSE) message.
 *
 * SSE events are sent from the server to the client and can contain:
 * - data: The payload of the event
 * - event: Custom event type (defaults to "message")
 * - id: Event ID for tracking and resuming streams
 * - retry: Reconnection time in milliseconds
 * - comment: Comments (not processed by the client)
 *
 * @param A The type of the data payload
 */
data class SSEEvent<A>(
    /**
     * The data payload of the event. Will be serialized according to the schema.
     */
    val data: A,

    /**
     * Optional event type. If null, defaults to "message" event.
     * Custom event types allow clients to listen to specific event types.
     */
    val event: String? = null,

    /**
     * Optional event ID. Used by clients to track the last received event
     * and resume streams from a specific point using the Last-Event-ID header.
     */
    val id: String? = null,

    /**
     * Optional retry time in milliseconds. Instructs the client how long to wait
     * before attempting to reconnect if the connection is lost.
     */
    val retry: Long? = null,

    /**
     * Optional comment. Comments are sent to the client but ignored by EventSource.
     * Useful for keeping connections alive or debugging.
     */
    val comment: String? = null
) {
    companion object {
        /**
         * Creates a simple SSE event with just data.
         */
        fun <A> data(data: A): SSEEvent<A> = SSEEvent(data = data)

        /**
         * Creates an SSE event with data and event type.
         */
        fun <A> typed(event: String, data: A): SSEEvent<A> = SSEEvent(data = data, event = event)

        /**
         * Creates an SSE event with all fields.
         */
        fun <A> full(
            data: A,
            event: String? = null,
            id: String? = null,
            retry: Long? = null,
            comment: String? = null
        ): SSEEvent<A> = SSEEvent(
            data = data,
            event = event,
            id = id,
            retry = retry,
            comment = comment
        )

        /**
         * Creates a comment-only SSE event (keepalive).
         */
        fun keepalive(comment: String = "keepalive"): SSEEvent<Unit> =
            SSEEvent(data = Unit, comment = comment)
    }
}

/**
 * SSE-specific response schema for streaming endpoints.
 *
 * @param A The type of data in each SSE event
 */
data class SSEResponseSchema<A>(
    /**
     * The body schema describing how to serialize each event's data.
     */
    val bodySchema: BodySchema<A>,

    /**
     * Optional retry time in milliseconds to include in all events.
     */
    val defaultRetry: Long? = null
) {
    companion object {
        /**
         * Creates an SSE response schema with JSON serialization.
         */
        fun <A> json(bodySchema: BodySchema<A>, defaultRetry: Long? = null): SSEResponseSchema<A> =
            SSEResponseSchema(bodySchema = bodySchema, defaultRetry = defaultRetry)
    }
}
