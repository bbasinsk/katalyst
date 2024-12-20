package io.github.bbasinsk.http

sealed interface Metadata<A> {
    data class Example<A>(val description: String, val value: A) : Metadata<A>
    data class Deprecated<A>(val reason: String) : Metadata<A>
    data class Description<A>(val description: String) : Metadata<A>
}
