package io.github.bbasinsk.http

sealed interface Response<Error, Output> {
    data class Error<E, A>(val value: E) : Response<E, A>
    data class Success<E, A>(val value: A) : Response<E, A>

    companion object {
        fun <E, A> success(value: A): Response<E, A> = Success(value)
        fun <E, A> error(value: E): Response<E, A> = Error(value)
    }
}
