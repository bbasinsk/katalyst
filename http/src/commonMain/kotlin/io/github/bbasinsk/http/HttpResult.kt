package io.github.bbasinsk.http

sealed interface HttpResult<out E, out A> {
    data class Success<A>(val value: A) : HttpResult<Nothing, A>
    data class Failure<E>(val status: Int, val error: E) : HttpResult<E, Nothing>
    data class NetworkError(val cause: Throwable) : HttpResult<Nothing, Nothing>
}
