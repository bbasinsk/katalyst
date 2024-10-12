package io.github.bbasinsk.schema.json.kotlinx

import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.instant
import io.github.bbasinsk.schema.localDate
import io.github.bbasinsk.schema.uuid
import io.github.bbasinsk.validation.Validation
import java.time.Instant
import java.time.LocalDate
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals

class JvmSerdeTest {

    @Test
    fun `uuid serializes and deserializes`() {
        val schema = Schema.uuid()
        val uuid = UUID.fromString("0cad71ed-aaca-47de-a97f-151f95fbd86f")
        val encoded = schema.encodeToJsonString(uuid)
        assertEquals(""""0cad71ed-aaca-47de-a97f-151f95fbd86f"""", encoded)
        assertEquals(Validation.valid(uuid), schema.decodeFromJsonString(encoded))
    }

    @Test
    fun `localDate serializes and deserializes`() {
        val schema = Schema.localDate()
        val localDate = LocalDate.parse("2021-01-01")
        val encoded = schema.encodeToJsonString(localDate)
        assertEquals(""""2021-01-01"""", encoded)
        assertEquals(Validation.valid(localDate), schema.decodeFromJsonString(encoded))
    }

    @Test
    fun `instant serializes and deserializes`() {
        val schema = Schema.instant()
        val instant = Instant.parse("2021-01-01T00:00:00Z")
        val encoded = schema.encodeToJsonString(instant)
        assertEquals(""""2021-01-01T00:00:00Z"""", encoded)
        assertEquals(Validation.valid(instant), schema.decodeFromJsonString(encoded))
    }
}
