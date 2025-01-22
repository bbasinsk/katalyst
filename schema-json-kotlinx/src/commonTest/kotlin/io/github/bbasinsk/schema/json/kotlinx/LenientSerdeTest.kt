package io.github.bbasinsk.schema.json.kotlinx

import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.json.InvalidJson
import io.github.bbasinsk.schema.transform
import io.github.bbasinsk.validation.Validation
import kotlin.test.Test
import kotlin.test.assertEquals

class LenientSerdeTest {

    fun Schema.Companion.quotedDouble(): Schema<Double> =
        string().transform({ it.toDouble() }) { it.toString() }

    fun Schema.Companion.lenientDouble(): Schema<Double> =
        double().orElse(quotedDouble())

    @Test
    fun `deserializes string of double to double`() {
        val rawString = "\"12.123\""

        assertEquals(
            Validation.valid(12.123),
            Schema.lenientDouble().decodeFromJsonString(rawString)
        )
        assertEquals(
            Validation.invalid(InvalidJson(expected = "Double", found = "\"12.123\"", path = emptyList())),
            Schema.double().decodeFromJsonString(rawString)
        )
    }

    @Test
    fun `still returns error of preferred deserialization`() {
        val rawString = "{}"
        assertEquals(
            Validation.invalid(InvalidJson(expected = "Double", found = "{}", path = emptyList())),
            Schema.lenientDouble().decodeFromJsonString(rawString)
        )
    }
}
