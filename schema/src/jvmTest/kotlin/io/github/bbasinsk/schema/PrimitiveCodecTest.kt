package io.github.bbasinsk.schema

import io.github.bbasinsk.schema.kotlin.uuid
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class PrimitiveCodecTest {

    @Test
    fun `decodes uuid`() {
        val uuid = Uuid.random()
        val encoded = uuid.toString()
        val decoded = Schema.uuid().decodePrimitiveString(encoded).getOrThrow()
        assertEquals(uuid, decoded)
    }

    @Test
    fun `null on required int returns failure`() {
        val result = Schema.int().decodePrimitiveString(null)
        assert(result.isFailure)
    }

    @Test
    fun `null on optional int returns null`() {
        val result = Schema.int().optional().decodePrimitiveString(null)
        assertEquals(null, result.getOrThrow())
    }

    @Test
    fun `null on default int returns default value`() {
        val result = Schema.int().default(42).decodePrimitiveString(null)
        assertEquals(42, result.getOrThrow())
    }
}
