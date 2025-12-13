package io.github.bbasinsk.http

sealed interface WebSocketMessage<out A> {
    data class Text<A>(val data: A) : WebSocketMessage<A>
    data class Binary<A>(val data: A) : WebSocketMessage<A>
    data class Close(val code: Int, val reason: String?) : WebSocketMessage<Nothing>

    companion object {
        fun <A> text(data: A): WebSocketMessage<A> = Text(data)
        fun <A> binary(data: A): WebSocketMessage<A> = Binary(data)
        fun close(code: Int = 1000, reason: String? = null): WebSocketMessage<Nothing> = Close(code, reason)
    }
}
