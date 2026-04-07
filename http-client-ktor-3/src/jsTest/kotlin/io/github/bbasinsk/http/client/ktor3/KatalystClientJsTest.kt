package io.github.bbasinsk.http.client.ktor3

import io.github.bbasinsk.http.Http
import io.github.bbasinsk.http.HttpResult
import io.github.bbasinsk.schema.Schema
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertIs

class KatalystClientJsTest {

    @Test
    fun throwable_from_engine_returns_NetworkError() = runTest {
        val mockEngine = MockEngine { throw Error("JS fetch TypeError simulation") }
        val client = HttpClient(mockEngine)

        val api = Http.get { Root / "test" }
            .output { status(Ok) { json { Schema.string() } } }

        val result = KatalystClient(client).call(api)

        assertIs<HttpResult.NetworkError>(result)
    }
}
