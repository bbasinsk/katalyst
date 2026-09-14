@file:OptIn(ExperimentalUuidApi::class)

package io.github.bbasinsk.http

import io.github.bbasinsk.schema.kotlin.uuid
import io.github.bbasinsk.validation.Validation
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
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

        assertEquals(Validation.valid(((42 to true) to null) to 99L), found)
        assertEquals(listOf("remaining"), path)
    }

    @Test
    fun `it rejects missing and invalid nullable path parameters`() {
        val params = Root / param("id") { int().optional() } / param("active") { boolean().optional() }
        val cases = listOf(
            emptyList<String>() to listOf("id", "active"),
            listOf("invalid") to listOf("id", "active"),
            listOf("42") to listOf("active"),
            listOf("42", "invalid") to listOf("active")
        )

        for ((path, names) in cases) {
            val result = params.parse(path.toMutableList(), emptyMap(), emptyMap())
            val errors = assertIs<Validation.Invalid<ParamError>>(result).errors
            assertEquals(names, errors.map { it.name })
            assertEquals(names.map { ParamError.Source.Path }, errors.map { it.source })
        }
    }

    @Test
    fun `it accumulates path query header and list item errors without exposing values`() {
        val params =
            (Root / "users" / param("id") { int() } / "active" / param("active") { boolean() } / "details")
                .withQuery(param("numbers") { list(int()) }.description("Requested numbers"))
                .withQuery(param("page") { int() })
                .withHeader(param("X-Version") { long() })
        val path = mutableListOf("users", "private-id", "active", "private-active", "details", "remaining")

        val result = params.parse(
            rawPath = path,
            rawHeaders = mapOf("x-version" to listOf("private-header")),
            rawQueryParams = mapOf("numbers" to listOf("private-first", "7", "private-third"))
        )

        val errors = assertIs<Validation.Invalid<ParamError>>(result).errors
        assertEquals(
            listOf(
                Triple(ParamError.Source.Path, "id", null),
                Triple(ParamError.Source.Path, "active", null),
                Triple(ParamError.Source.Query, "numbers", 0),
                Triple(ParamError.Source.Query, "numbers", 2),
                Triple(ParamError.Source.Query, "page", null),
                Triple(ParamError.Source.Header, "X-Version", null)
            ),
            errors.map { Triple(it.source, it.name, it.index) }
        )
        assertContains(errors[0].message, "Int")
        assertContains(errors[1].message, "Boolean")
        assertContains(errors[2].message, "Int")
        assertContains(errors[5].message, "Long")
        assertFalse(errors.any { it.message.contains("private-") })
        assertEquals(listOf("remaining"), path)
    }

    @Test
    fun `literal mismatches consume their position without misreading later parameters`() {
        val params = Root / "users" / param("id") { int() } / "details" / param("active") { boolean() }
        val path = mutableListOf("wrong", "42", "wrong", "true", "remaining")

        val result = params.parse(path, emptyMap(), emptyMap())

        val errors = assertIs<Validation.Invalid<ParamError>>(result).errors
        assertEquals(listOf("users", "details"), errors.map { it.name })
        assertEquals(listOf("remaining"), path)
    }

    @Test
    fun `empty path composition accumulates literal and query failures`() {
        val params = (Root / "users").withQuery(param("page") { int() })

        val result = params.parse(mutableListOf("wrong"), emptyMap(), emptyMap())

        val errors = assertIs<Validation.Invalid<ParamError>>(result).errors
        assertEquals(
            listOf(ParamError.Source.Path to "users", ParamError.Source.Query to "page"),
            errors.map { it.source to it.name }
        )
    }

    @Test
    fun `optional and default query values retain their decoding fallbacks`() {
        val params = Root.withQuery(param("optional") { int().optional() })
            .withQuery(param("default") { int().default(9) })

        val result = params.parse(
            mutableListOf(),
            emptyMap(),
            mapOf("optional" to listOf("not-an-int"), "default" to listOf("not-an-int"))
        )

        assertEquals(Validation.valid(null to 9), result)
    }

    @Test
    fun `it parses header names case insensitively`() {
        val http = Http
            .get { Root / "recipe" }
            .header { schema("Content-Location") { string() } }

        val found = http.params.parse(
            rawPath = mutableListOf("recipe"),
            rawHeaders = mapOf("content-location" to listOf("https://example.com/recipe")),
            rawQueryParams = emptyMap()
        )

        assertEquals(Validation.valid("https://example.com/recipe"), found)
    }

    @Test
    fun `it parses list query param`() {
        val http = Http
            .get { Root / "eval" / "invocations" }
            .query { schema("names") { list(string()) } }

        val found = http.params.parse(
            rawPath = mutableListOf("eval", "invocations"),
            rawHeaders = emptyMap(),
            rawQueryParams = mapOf("names" to listOf("first", "second"))
        )

        assertEquals(Validation.valid(listOf("first", "second")), found)
    }

    @Test
    fun `it parses optional list query param when present`() {
        val http = Http
            .get { Root / "eval" / "invocations" }
            .query { schema("names") { list(string()).optional() } }

        val found = http.params.parse(
            rawPath = mutableListOf("eval", "invocations"),
            rawHeaders = emptyMap(),
            rawQueryParams = mapOf("names" to listOf("first", "second"))
        )

        assertEquals(Validation.valid(listOf("first", "second")), found)
    }

    @Test
    fun `it parses optional list query param when absent`() {
        val http = Http
            .get { Root / "eval" / "invocations" }
            .query { schema("names") { list(string()).optional() } }

        val found = http.params.parse(
            rawPath = mutableListOf("eval", "invocations"),
            rawHeaders = emptyMap(),
            rawQueryParams = emptyMap()
        )

        assertEquals(Validation.valid(null), found)
    }

    @Test
    fun `it parses optional list query param when present but empty`() {
        val http = Http
            .get { Root / "eval" / "invocations" }
            .query { schema("names") { list(string()).optional() } }

        val found = http.params.parse(
            rawPath = mutableListOf("eval", "invocations"),
            rawHeaders = emptyMap(),
            rawQueryParams = mapOf("names" to emptyList())
        )

        assertEquals(Validation.valid(emptyList<String>()), found)
    }

    @Test
    fun `it parses uuid in path`() {
        val http = Http.get { Root / "thing" / param("thing-id") { uuid().optional() } }
            .output { status(Ok) { json { uuid().optional() } } }

        val uuid = Uuid.random()
        val found = http.params.parse(
            rawPath = mutableListOf("thing", uuid.toString()),
            rawHeaders = emptyMap(),
            rawQueryParams = emptyMap()
        )

        assertEquals(Validation.valid(uuid), found)
    }

    @Test
    fun `it defaults query params`() {
        val def = 1
        val http = Http
            .get { Root / "paging" }
            .query { schema("page") { int().default(def) } }

        val found = http.params.parse(
            rawPath = mutableListOf("paging"),
            rawHeaders = emptyMap(),
            rawQueryParams = emptyMap()
        )

        assertEquals(Validation.valid(def), found)
    }

    @Test
    fun `it parses optional string query param when present`() {
        val http = Http
            .get { Root / "eval" / "invocations" }
            .query { schema("name") { string().optional() } }

        val found = http.params.parse(
            rawPath = mutableListOf("eval", "invocations"),
            rawHeaders = emptyMap(),
            rawQueryParams = mapOf("name" to listOf("abc"))
        )

        assertEquals(Validation.valid("abc"), found)
    }

    @Test
    fun `it parses optional string query param as null when missing`() {
        val http = Http
            .get { Root / "eval" / "invocations" }
            .query { schema("name") { string().optional() } }

        val found = http.params.parse(
            rawPath = mutableListOf("eval", "invocations"),
            rawHeaders = emptyMap(),
            rawQueryParams = emptyMap()
        )

        assertEquals(Validation.valid(null), found)
    }

    @Test
    fun `it parses optional string query param as null when empty list`() {
        val http = Http
            .get { Root / "eval" / "invocations" }
            .query { schema("name") { string().optional() } }

        val found = http.params.parse(
            rawPath = mutableListOf("eval", "invocations"),
            rawHeaders = emptyMap(),
            rawQueryParams = mapOf("name" to listOf(""))
        )

        assertEquals(Validation.valid(null), found)
    }

    @Test
    fun `it defaults list query param when absent`() {
        val defaultNames = listOf("default1", "default2")
        val http = Http
            .get { Root / "eval" / "invocations" }
            .query { schema("names") { list(string()).default(defaultNames) } }

        val found = http.params.parse(
            rawPath = mutableListOf("eval", "invocations"),
            rawHeaders = emptyMap(),
            rawQueryParams = emptyMap()
        )

        assertEquals(Validation.valid(defaultNames), found)
    }
}
