package io.github.bbasinsk.http

abstract class HttpEndpointGroup(vararg val tags: String) {
    private val _apis = mutableListOf<Http<*, *, *, *, *>>()

    fun <P, I, E, O, A> api(definition: Http<P, I, E, O, A>): Http<P, I, E, O, A> =
        definition.tag(*tags).also { _apis.add(it) }

    fun <P, I, E, O, A> http(build: Http.Companion.() -> Http<P, I, E, O, A>): Http<P, I, E, O, A> =
        api(Http.build())

    val apis: List<Http<*, *, *, *, *>> get() = _apis
}
