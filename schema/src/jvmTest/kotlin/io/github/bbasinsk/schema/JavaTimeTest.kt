package io.github.bbasinsk.schema

import io.github.bbasinsk.schema.java.duration
import io.github.bbasinsk.schema.java.localTime
import io.github.bbasinsk.schema.java.zonedDateTime
import io.github.bbasinsk.schema.kotlin.uuid
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class JavaTimeTest {

    @Test
    fun `serde on ZonedDateTime`() {
        val isoZonedDateTime = "2023-10-01T12:00:00Z"
        val decoded = Schema.zonedDateTime().decodePrimitiveString(isoZonedDateTime).getOrThrow()
        val encoded = Schema.zonedDateTime().encodePrimitiveString(decoded)
        assertEquals(isoZonedDateTime, encoded.getOrThrow())
    }

    @Test
    fun `serde on Duration`() {
        val isoDuration = "PT1H"
        val decoded = Schema.duration().decodePrimitiveString(isoDuration).getOrThrow()
        val encoded = Schema.duration().encodePrimitiveString(decoded)
        assertEquals(isoDuration, encoded.getOrThrow())
    }

    @Test
    fun `serde on LocalTime`() {
        val isoLocalTime = "12:00:00"
        val decoded = Schema.localTime().decodePrimitiveString(isoLocalTime).getOrThrow()
        val encoded = Schema.localTime().encodePrimitiveString(decoded)
        assertEquals(isoLocalTime, encoded.getOrThrow())
    }
}
