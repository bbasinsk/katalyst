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
}
