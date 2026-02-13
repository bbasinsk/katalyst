package io.github.bbasinsk.http.client.ktor3

import io.github.bbasinsk.http.Http
import io.github.bbasinsk.http.header
import io.github.bbasinsk.http.parseCatching
import io.github.bbasinsk.http.query
import io.github.bbasinsk.http.render
import kotlin.test.Test
import kotlin.test.assertEquals

class ParamsRenderTest {

    @Test
    fun `render path with literal segments`() {
        val api = Http.get { Root / "users" / "list" }
        val rendered = api.params.render(Unit)

        assertEquals(listOf("users", "list"), rendered.pathSegments)
        assertEquals(emptyMap(), rendered.queryParams)
        assertEquals(emptyMap(), rendered.headers)
    }

    @Test
    fun `render path with single param`() {
        val api = Http.get { Root / "users" / param("id") { int() } }
        val rendered = api.params.render(42)

        assertEquals(listOf("users", "42"), rendered.pathSegments)
    }

    @Test
    fun `render path with multiple params`() {
        val api = Http.get { Root / "orgs" / param("orgId") { string() } / "users" / param("userId") { int() } }
        val rendered = api.params.render(Pair("acme", 7))

        assertEquals(listOf("orgs", "acme", "users", "7"), rendered.pathSegments)
    }

    @Test
    fun `render query params`() {
        val api = Http.get { Root / "search" }
            .query { schema("q") { string() } }
        val rendered = api.params.render("hello")

        assertEquals(listOf("search"), rendered.pathSegments)
        assertEquals(mapOf("q" to listOf("hello")), rendered.queryParams)
    }

    @Test
    fun `render header params`() {
        val api = Http.get { Root / "data" }
            .header { schema("X-Request-Id") { string() } }
        val rendered = api.params.render("abc-123")

        assertEquals(listOf("data"), rendered.pathSegments)
        assertEquals(mapOf("X-Request-Id" to listOf("abc-123")), rendered.headers)
    }

    @Test
    fun `render combined path, query, and header params`() {
        val api = Http.get { Root / "users" / param("id") { int() } }
            .query { schema("page") { int() } }
            .header { schema("X-Auth") { string() } }

        // Type is Pair<Pair<Int, Int>, String>
        val rendered = api.params.render(Pair(Pair(42, 1), "token"))

        assertEquals(listOf("users", "42"), rendered.pathSegments)
        assertEquals(mapOf("page" to listOf("1")), rendered.queryParams)
        assertEquals(mapOf("X-Auth" to listOf("token")), rendered.headers)
    }

    @Test
    fun `render list query params`() {
        val api = Http.get { Root / "filter" }
            .query { schema("tags") { list(string()) } }
        val rendered = api.params.render(listOf("a", "b", "c"))

        assertEquals(mapOf("tags" to listOf("a", "b", "c")), rendered.queryParams)
    }

    @Test
    fun `render roundtrip - parse then render yields original values`() {
        val api = Http.get { Root / "orgs" / param("orgId") { string() } / "users" / param("userId") { int() } }
            .query { schema("page") { int() } }

        val originalParams = Pair(Pair("acme", 7), 2)
        val rendered = api.params.render(originalParams)

        assertEquals(listOf("orgs", "acme", "users", "7"), rendered.pathSegments)
        assertEquals(mapOf("page" to listOf("2")), rendered.queryParams)

        // Now parse back
        val parsed = api.params.parseCatching(
            path = rendered.pathSegments.toMutableList(),
            headers = rendered.headers,
            queryParams = rendered.queryParams
        ).getOrThrow()

        assertEquals(originalParams, parsed)
    }
}
