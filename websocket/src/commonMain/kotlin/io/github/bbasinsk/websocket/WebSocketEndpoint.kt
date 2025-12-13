package io.github.bbasinsk.websocket

import kotlinx.coroutines.flow.Flow

data class WebSocketEndpoint<Params, In, Out, Context>(
    val api: WebSocket<Params, In, Out>,
    val handler: suspend WebSocketScope<In, Out>.(Params, Context) -> Unit
)

interface WebSocketScope<In, Out> {
    val incoming: Flow<WebSocketMessage<In>>
    suspend fun send(message: WebSocketMessage<Out>)
    suspend fun close(code: Int = 1000, reason: String? = null)
}
