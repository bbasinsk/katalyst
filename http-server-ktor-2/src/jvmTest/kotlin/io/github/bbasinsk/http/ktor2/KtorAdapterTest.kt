package io.github.bbasinsk.http.ktor2

import io.github.bbasinsk.http.Http
import io.github.bbasinsk.http.Response
import io.github.bbasinsk.http.header
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.testApplication
import kotlin.test.Test
import kotlin.test.assertEquals

class KtorAdapterTest {
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
    }
}
