package io.github.bbasinsk.schema.json.kotlinx

import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.json.InvalidJson
import io.github.bbasinsk.schema.orElse
import io.github.bbasinsk.schema.transform
import io.github.bbasinsk.validation.Validation
import kotlin.test.Test
import kotlin.test.assertEquals

class LenientSerdeTest {

    fun Schema.Companion.lenientDouble(): Schema<Double> =
        double().orElse(string()) { it.toDouble() }

    @Test
    fun `deserializes string of double to double`() {
        val rawString = "\"12.123\""

        assertEquals(
            Validation.valid(12.123),
            Schema.lenientDouble().decodeFromJsonString(rawString)
        )
        assertEquals(
            Validation.invalid(InvalidJson.FieldError(expected = "Double", found = "\"12.123\"", path = emptyList())),
            Schema.double().decodeFromJsonString(rawString)
        )
    }

    @Test
    fun `returns error`() {
        val rawString = "{}"
        assertEquals(
            Validation.invalid(
                InvalidJson.Or(
                    preferred = listOf(InvalidJson.FieldError(expected = "Double", found = "{}", path = emptyList())),
                    fallback = listOf(InvalidJson.FieldError(expected = "String", found = "{}", path = emptyList()))
                )
            ),
            Schema.lenientDouble().decodeFromJsonString(rawString)
        )
    }
}
