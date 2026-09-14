package io.github.bbasinsk.http.ktor2

import io.github.bbasinsk.http.Http
import io.github.bbasinsk.http.Response
import io.github.bbasinsk.http.header
import io.github.bbasinsk.http.query
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.testApplication
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class KtorAdapterTest {
    @Test
    fun parameterFailuresReturnAllErrorsWithoutCallingHandler() = testApplication {
        var handledRequests = 0
        val api = Http.get {
            Root / "users" / param("id") { int() } / "active" / param("active") { boolean() }
        }
            .query { schema("page") { int() } }
            .query { schema("ids") { list(int()) } }
            .header { schema("X-Version") { long() } }
            .output { status(Ok) { plain { string() } } }

        application {
            endpoints {
                handle(api) {
                    handledRequests++
                    Response.success("accepted")
                }
            }
        }

        val response = client.get(
            "/users/private-id/active/private-active?page=private-page&ids=private-first&ids=3&ids=private-third"
        ) {
            header("X-Version", "private-header")
        }

        assertEquals(HttpStatusCode.BadRequest, response.status)
        assertEquals(ContentType.Application.Json, response.contentType()?.withoutParameters())
        val body = response.bodyAsText()
        val errors = Json.parseToJsonElement(body).jsonArray.map { it.jsonObject }
        assertEquals(
            listOf(
                Triple("Path", "id", null),
                Triple("Path", "active", null),
                Triple("Query", "page", null),
                Triple("Query", "ids", 0),
                Triple("Query", "ids", 2),
                Triple("Header", "X-Version", null)
            ),
            errors.map {
                Triple(
                    it.getValue("source").jsonPrimitive.content,
                    it.getValue("name").jsonPrimitive.content,
                    it["index"]?.jsonPrimitive?.intOrNull
                )
            }
        )
        assertContains(errors[0].getValue("message").jsonPrimitive.content, "Int")
        assertContains(errors[1].getValue("message").jsonPrimitive.content, "Boolean")
        assertContains(errors[5].getValue("message").jsonPrimitive.content, "Long")
        assertFalse(body.contains("private-"))
        assertEquals(0, handledRequests)

        val corrected = client.get("/users/42/active/true?page=2&ids=1&ids=3") {
            header("x-version", "99")
        }
        assertEquals(HttpStatusCode.OK, corrected.status)
        assertEquals("accepted", corrected.bodyAsText())
        assertEquals(1, handledRequests)

        val missingPath = client.get("/users/42/active?page=2&ids=1") {
            header("X-Version", "99")
        }
        assertEquals(HttpStatusCode.BadRequest, missingPath.status)
        val missingError = Json.parseToJsonElement(missingPath.bodyAsText()).jsonArray.single().jsonObject
        assertEquals("Path", missingError.getValue("source").jsonPrimitive.content)
        assertEquals("active", missingError.getValue("name").jsonPrimitive.content)
        assertEquals(1, handledRequests)
    }

    @Test
    fun headerParameterNamesAreCaseInsensitiveAndMissingValuesReturnBadRequest() = testApplication {
        val api = Http.get { Root / "recipe" }
            .header { schema("Content-Location") { string() } }
            .output { status(Ok) { plain { string() } } }

        application {
            endpoints {
                handle(api) { request -> Response.success(request.params) }
            }
        }

        val parsed = client.get("/recipe") {
            header("content-location", "https://example.com/recipe")
        }
        val missing = client.get("/recipe")

        assertEquals(HttpStatusCode.OK, parsed.status)
        assertEquals("https://example.com/recipe", parsed.bodyAsText())
        assertEquals(HttpStatusCode.BadRequest, missing.status)
        val error = Json.parseToJsonElement(missing.bodyAsText()).jsonArray.single().jsonObject
        assertEquals("Header", error.getValue("source").jsonPrimitive.content)
        assertEquals("Content-Location", error.getValue("name").jsonPrimitive.content)
    }
}
