package io.github.bbasinsk.http.ktor3

import io.github.bbasinsk.http.Http
import io.github.bbasinsk.http.Response
import io.github.bbasinsk.http.query
import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.java.instant
import io.github.bbasinsk.tuple.tupleValues
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class KtorAdapterTest {
    val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = false
    }

    @Test
    fun unprocessableEntityIsReturnedAsIs() = testApplication {
        data class Input(val value: Int)

        val inputSchema = Schema.record(
            Schema.field(Schema.int(), "value") { value },
            ::Input
        )

        val api = Http.post { Root / "some" / "path" }
            .input { json { inputSchema } }
            .output { status(Ok) { plain { string() } } }

        install(io.ktor.server.plugins.contentnegotiation.ContentNegotiation) {
            json(json)
        }

        application {
            endpoints {
                handle(api) { request ->
                    success("OK")
                }
            }
        }

        val client = createClient {
            install(io.ktor.client.plugins.contentnegotiation.ContentNegotiation) {
                json(json)
            }
        }

        val response = client.post("/some/path") {
            contentType(ContentType.Application.Json)
            setBody("{}")
        }

        // Look at logs: "WARN  io.ktor.test - Expected Int, found null at path: $.value"
        assertEquals(422, response.status.value)
    }

    @Test
    fun multipleQueryParamsAreCollectedAsList() = testApplication {
        val api = Http.get { Root / "some" / "path" }
            .query { schema("name") { list(string()) } }
            .output { status(Ok) { plain { string() } } }

        application {
            endpoints {
                handle(api) { request ->
                    val (names) = tupleValues(request.params)
                    Response.success(names.joinToString(prefix = "[", postfix = "]", separator = ","))
                }
            }
        }

        val response = client.get("/some/path?name=a&name=b&name=c")
        assertEquals("[a,b,c]", response.bodyAsText())
    }

    @Test
    fun pathParamsArePercentDecoded() = testApplication {
        val api = Http.get { Root / "items" / param("since") { instant() } }
            .output { status(Ok) { plain { string() } } }

        application {
            endpoints {
                handle(api) { request ->
                    val (since) = tupleValues(request.params)
                    Response.success(since.toString())
                }
            }
        }

        val response = client.get("/items/2000-01-01T00%3A00%3A00Z")
        assertEquals("2000-01-01T00:00:00Z", response.bodyAsText())
    }
}
