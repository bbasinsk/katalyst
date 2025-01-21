@file:OptIn(ExperimentalUuidApi::class)

package io.github.bbasinsk.http

import io.github.bbasinsk.schema.kotlin.uuid
import io.github.bbasinsk.tuple.tupleValues
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class ParamsSchemaTest {

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
}
