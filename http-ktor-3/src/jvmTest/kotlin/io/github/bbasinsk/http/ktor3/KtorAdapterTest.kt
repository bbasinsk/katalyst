package io.github.bbasinsk.http.ktor3

import io.github.bbasinsk.http.Http
import io.github.bbasinsk.http.PathSchema
import io.github.bbasinsk.http.Response
import io.github.bbasinsk.tuple.tupleValues
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class KtorAdapterTest {

    @Test
    fun multipleQueryParamsAreCollectedAsList() = testApplication {
        val api = Http.get { PathSchema.Root / "some" / "path" }
            .query { schema("name") { list(string()) } }
            .output { status(Ok) { plain { string() } } }

        application {
            endpoints {
                apply {
                    handle(api) { request ->
                        val (names) = tupleValues(request.params)
                        Response.success(names.joinToString(prefix = "[", postfix = "]", separator = ","))
                    }
                }
            }
        }

        val response = client.get("/some/path?name=a&name=b&name=c")
        assertEquals("[a,b,c]", response.bodyAsText())
    }
}
