package io.github.bbasinsk.http

abstract class HttpEndpointGroup(vararg val tags: String) {
    private val _apis = mutableListOf<Http<*, *, *, *>>()

    fun <P, I, E, O> api(definition: Http<P, I, E, O>): Http<P, I, E, O> =
        definition.tag(*tags).also { _apis.add(it) }

    fun <P, I, E, O> http(build: Http.Companion.() -> Http<P, I, E, O>): Http<P, I, E, O> =
        api(Http.build())

    val apis: List<Http<*, *, *, *>> get() = _apis
}
