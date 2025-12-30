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

    @Test
    fun formUrlEncodedRequestIsParsed() = testApplication {
        data class FormInput(val name: String, val age: Int)

        val formSchema = Schema.record(
            Schema.field(Schema.string(), "name") { name },
            Schema.field(Schema.int(), "age") { age },
            ::FormInput
        )

        val api = Http.post { Root / "form" }
            .input { formUrlEncoded { formSchema } }
            .output { status(Ok) { plain { string() } } }

        application {
            endpoints {
                handle(api) { request ->
                    Response.success("${request.input.name} is ${request.input.age}")
                }
            }
        }

        val response = client.post("/form") {
            contentType(ContentType.Application.FormUrlEncoded)
            setBody("name=John&age=30")
        }

        assertEquals(200, response.status.value)
        assertEquals("John is 30", response.bodyAsText())
    }

    @Test
    fun formUrlEncodedWithOptionalField() = testApplication {
        data class FormInput(val required: String, val optional: String?)

        val formSchema = Schema.record(
            Schema.field(Schema.string(), "required") { required },
            Schema.field(Schema.string().optional(), "optional") { optional },
            ::FormInput
        )

        val api = Http.post { Root / "form" }
            .input { formUrlEncoded { formSchema } }
            .output { status(Ok) { plain { string() } } }

        application {
            endpoints {
                handle(api) { request ->
                    Response.success("required=${request.input.required}, optional=${request.input.optional}")
                }
            }
        }

        val response = client.post("/form") {
            contentType(ContentType.Application.FormUrlEncoded)
            setBody("required=hello")
        }

        assertEquals(200, response.status.value)
        assertEquals("required=hello, optional=null", response.bodyAsText())
    }

    @Test
    fun formUrlEncodedWithListField() = testApplication {
        data class FormInput(val tags: List<String>)

        val formSchema = Schema.record(
            Schema.field(Schema.list(Schema.string()), "tags") { tags },
            ::FormInput
        )

        val api = Http.post { Root / "form" }
            .input { formUrlEncoded { formSchema } }
            .output { status(Ok) { plain { string() } } }

        application {
            endpoints {
                handle(api) { request ->
                    Response.success(request.input.tags.joinToString(","))
                }
            }
        }

        val response = client.post("/form") {
            contentType(ContentType.Application.FormUrlEncoded)
            setBody("tags=a&tags=b&tags=c")
        }

        assertEquals(200, response.status.value)
        assertEquals("a,b,c", response.bodyAsText())
    }

    @Test
    fun formUrlEncodedWithEmptyValue() = testApplication {
        data class FormInput(val name: String)

        val formSchema = Schema.record(
            Schema.field(Schema.string(), "name") { name },
            ::FormInput
        )

        val api = Http.post { Root / "form" }
            .input { formUrlEncoded { formSchema } }
            .output { status(Ok) { plain { string() } } }

        application {
            endpoints {
                handle(api) { request ->
                    Response.success("name=[${request.input.name}]")
                }
            }
        }

        val response = client.post("/form") {
            contentType(ContentType.Application.FormUrlEncoded)
            setBody("name=")
        }

        assertEquals(200, response.status.value)
        assertEquals("name=[]", response.bodyAsText())
    }

    @Test
    fun patchWithJsonInputAndOutput() = testApplication {
        data class Input(val value: String)
        data class Output(val result: String)

        val inputSchema = Schema.record(
            Schema.field(Schema.string(), "value") { value },
            ::Input
        )

        val outputSchema = Schema.record(
            Schema.field(Schema.string(), "result") { result },
            ::Output
        )

        val api = Http.patch { Root / "resource" / param("id") { string() } }
            .input { json { inputSchema } }
            .output { status(Ok) { json { outputSchema } } }

        install(io.ktor.server.plugins.contentnegotiation.ContentNegotiation) {
            json(json)
        }

        application {
            endpoints {
                handle(api) { request ->
                    val (id) = tupleValues(request.params)
                    Response.success(Output("patched-$id-${request.input.value}"))
                }
            }
        }

        val client = createClient {
            install(io.ktor.client.plugins.contentnegotiation.ContentNegotiation) {
                json(json)
            }
        }

        val response = client.patch("/resource/123") {
            contentType(ContentType.Application.Json)
            setBody("""{"value":"test"}""")
        }

        assertEquals(200, response.status.value)
        assertEquals("""{"result":"patched-123-test"}""", response.bodyAsText())
    }

    @Test
    fun patchWithQueryParams() = testApplication {
        val api = Http.patch { Root / "items" }
            .query { schema("id") { string() } }
            .output { status(Ok) { plain { string() } } }

        application {
            endpoints {
                handle(api) { request ->
                    val (id) = tupleValues(request.params)
                    Response.success("updated-$id")
                }
            }
        }

        val response = client.patch("/items?id=abc123")
        assertEquals(200, response.status.value)
        assertEquals("updated-abc123", response.bodyAsText())
    }

    @Test
    fun patchWithFormUrlEncoded() = testApplication {
        data class FormInput(val name: String, val status: String)

        val formSchema = Schema.record(
            Schema.field(Schema.string(), "name") { name },
            Schema.field(Schema.string(), "status") { status },
            ::FormInput
        )

        val api = Http.patch { Root / "update" }
            .input { formUrlEncoded { formSchema } }
            .output { status(Ok) { plain { string() } } }

        application {
            endpoints {
                handle(api) { request ->
                    Response.success("${request.input.name}:${request.input.status}")
                }
            }
        }

        val response = client.patch("/update") {
            contentType(ContentType.Application.FormUrlEncoded)
            setBody("name=item&status=active")
        }

        assertEquals(200, response.status.value)
        assertEquals("item:active", response.bodyAsText())
    }
}
