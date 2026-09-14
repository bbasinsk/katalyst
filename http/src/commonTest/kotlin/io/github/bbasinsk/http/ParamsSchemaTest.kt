@file:OptIn(ExperimentalUuidApi::class)

package io.github.bbasinsk.http

import io.github.bbasinsk.schema.kotlin.uuid
import io.github.bbasinsk.tuple.tupleValues
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class ParamsSchemaTest {

    @Test
    fun `it preserves nested parameter types and path consumption order`() {
        val params: ParamsSchema<Pair<Pair<Pair<Int, Boolean>, String?>, Long>> =
            (Root / "users" / param("id") { int() } / "active" / param("active") { boolean() } / "details")
                .withQuery(param("search") { string().optional() })
                .withHeader(param("X-Version") { long() })
        val path = mutableListOf("users", "42", "active", "true", "details", "remaining")

        val found = params.parse(
            rawPath = path,
            rawHeaders = mapOf("x-version" to listOf("99")),
            rawQueryParams = emptyMap()
        )

        assertEquals(((42 to true) to null) to 99L, found)
        assertEquals(listOf("remaining"), path)
    }

    @Test
    fun `it rejects missing and invalid nullable path parameters`() {
        val params = Root / param("id") { int().optional() } / param("active") { boolean().optional() }

        for (path in listOf(emptyList(), listOf("invalid"), listOf("42"), listOf("42", "invalid"))) {
            assertFailsWith<IllegalStateException> {
                params.parse(path.toMutableList(), emptyMap(), emptyMap())
            }
        }
    }

    @Test
    fun `it parses header names case insensitively`() {
        val http = Http
            .get { Root / "recipe" }
            .header { schema("Content-Location") { string() } }

        val found = http.params.parseCatching(
            path = listOf("recipe"),
            headers = mapOf("content-location" to listOf("https://example.com/recipe")),
            queryParams = emptyMap()
        ).getOrThrow()

        val (contentLocation) = tupleValues(found)
        assertEquals("https://example.com/recipe", contentLocation)
    }

    @Test
    fun `it parses list query param`() {
        val http = Http
            .get { Root / "eval" / "invocations" }
            .query { schema("names") { list(string()) } }

        val found = http.params.parseCatching(
            path = listOf("eval", "invocations"),
            headers = emptyMap(),
            queryParams = mapOf("names" to listOf("first", "second"))
        ).getOrThrow()

        val (names) = tupleValues(found)
        assertEquals(listOf("first", "second"), names)
    }

    @Test
    fun `it parses optional list query param when present`() {
        val http = Http
            .get { Root / "eval" / "invocations" }
            .query { schema("names") { list(string()).optional() } }

        val found = http.params.parseCatching(
            path = listOf("eval", "invocations"),
            headers = emptyMap(),
            queryParams = mapOf("names" to listOf("first", "second"))
        ).getOrThrow()

        val (names) = tupleValues(found)
        assertEquals(listOf("first", "second"), names)
    }

    @Test
    fun `it parses optional list query param when absent`() {
        val http = Http
            .get { Root / "eval" / "invocations" }
            .query { schema("names") { list(string()).optional() } }

        val found = http.params.parseCatching(
            path = listOf("eval", "invocations"),
            headers = emptyMap(),
            queryParams = emptyMap()
        ).getOrThrow()

        val (names) = tupleValues(found)
        assertEquals(null, names)
    }

    @Test
    fun `it parses optional list query param when present but empty`() {
        val http = Http
            .get { Root / "eval" / "invocations" }
            .query { schema("names") { list(string()).optional() } }

        val found = http.params.parseCatching(
            path = listOf("eval", "invocations"),
            headers = emptyMap(),
            queryParams = mapOf("names" to emptyList())
        ).getOrThrow()

        val (names) = tupleValues(found)
        assertEquals(emptyList<String>(), names)
    }

    @Test
    fun `it parses uuid in path`() {
        val http = Http.get { Root / "thing" / param("thing-id") { uuid().optional() } }
            .output { status(Ok) { json { uuid().optional() } } }

        val uuid = Uuid.random()
        val found = http.params.parseCatching(
            path = listOf("thing", uuid.toString()),
            headers = emptyMap(),
            queryParams = emptyMap()
        ).getOrThrow()

        val (thingId) = tupleValues(found)
        assertEquals(uuid, thingId)
    }

    @Test
    fun `it defaults query params`() {
        val def = 1
        val http = Http
            .get { Root / "paging" }
            .query { schema("page") { int().default(def) } }

        val uuid = Uuid.random()
        val found = http.params.parseCatching(
            path = listOf("paging"),
            headers = emptyMap(),
            queryParams = emptyMap()
        ).getOrThrow()

        val (page) = tupleValues(found)
        assertEquals(def, page)
    }

    @Test
    fun `it parses optional string query param when present`() {
        val http = Http
            .get { Root / "eval" / "invocations" }
            .query { schema("name") { string().optional() } }

        val found = http.params.parseCatching(
            path = listOf("eval", "invocations"),
            headers = emptyMap(),
            queryParams = mapOf("name" to listOf("abc"))
        ).getOrThrow()

        val (name) = tupleValues(found)
        assertEquals("abc", name)
    }

    @Test
    fun `it parses optional string query param as null when missing`() {
        val http = Http
            .get { Root / "eval" / "invocations" }
            .query { schema("name") { string().optional() } }

        val found = http.params.parseCatching(
            path = listOf("eval", "invocations"),
            headers = emptyMap(),
            queryParams = mapOf()
        ).getOrThrow()

        val (name) = tupleValues(found)
        assertEquals(null, name)
    }

    @Test
    fun `it parses optional string query param as null when empty list`() {
        // This can happen when a query param is provided without a value, e.g. ?name=
        // I don't think we have a way to distinguish between absent and empty value for optional params
        val http = Http
            .get { Root / "eval" / "invocations" }
            .query { schema("name") { string().optional() } }

        val found = http.params.parseCatching(
            path = listOf("eval", "invocations"),
            headers = emptyMap(),
            queryParams = mapOf("name" to listOf(""))
        ).getOrThrow()

        val (name) = tupleValues(found)
        assertEquals(null, name)
    }

    @Test
    fun `it defaults list query param when absent`() {
        val defaultNames = listOf("default1", "default2")
        val http = Http
            .get { Root / "eval" / "invocations" }
            .query { schema("names") { list(string()).default(defaultNames) } }

        val found = http.params.parseCatching(
            path = listOf("eval", "invocations"),
            headers = emptyMap(),
            queryParams = emptyMap()
        ).getOrThrow()

        val (names) = tupleValues(found)
        assertEquals(defaultNames, names)
    }
}
