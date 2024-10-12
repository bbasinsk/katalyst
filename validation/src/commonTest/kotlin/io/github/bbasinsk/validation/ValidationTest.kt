package io.github.bbasinsk.validation

import io.github.bbasinsk.validation.Validation.Companion.build
import io.github.bbasinsk.validation.Validation.Companion.invalid
import io.github.bbasinsk.validation.Validation.Companion.valid
import io.github.bbasinsk.validation.Validation.Invalid
import io.github.bbasinsk.validation.Validation.Valid
import kotlin.test.Test
import kotlin.test.assertEquals

class ValidationTest {
    private val valid: Validation<String, Int> = valid(1)
    private val invalid: Validation<String, Int> = invalid("error")

    @Test
    fun `mapValid works`() {
        val f = { x: Int -> x + 1 }
        assertEquals(Valid(2), valid.mapValid(f))
        assertEquals(Invalid(listOf("error")), invalid.mapValid(f))
    }

    @Test
    fun `mapInvalid works`() {
        val f = { x: String -> "$x!" }
        assertEquals(Valid(1), valid.mapInvalid(f))
        assertEquals(Invalid(listOf("error!")), invalid.mapInvalid(f))
    }

    @Test
    fun `zip works`() {
        assertEquals(Valid(Pair("a", "b")), valid("a") zip valid("b"))
        assertEquals(Invalid(listOf(1, 2)), invalid(1) zip invalid(2))
        assertEquals(Invalid(listOf(1)), invalid(1) zip valid("b"))
        assertEquals(Invalid(listOf(2)), valid("a") zip invalid(2))
    }

    @Test
    fun `sequence valid`() {
        assertEquals(
            Valid(listOf(1, 2, 3, 4, 5)),
            Validation.sequence(
                (1..5).toList().map { int ->
                    if (int >= 0) valid(int) else invalid(int)
                }
            )
        )
    }

    @Test
    fun `sequence invalid`() {
        assertEquals(
            Invalid(listOf(-5, -4, -3, -2, -1)),
            Validation.sequence(
                (-5..5).toList().map { int ->
                    if (int >= 0) valid(int) else invalid(int)
                }
            )
        )
    }

    @Test
    fun `build valid`() {
        assertEquals(
            Valid(3),
            build(
                valid(1),
                valid(2)
            ) { a, b ->
                a + b
            }
        )
    }

    @Test
    fun `build invalid`() {
        assertEquals(
            Invalid(listOf("a", "b")),
            build(
                invalid("a"),
                invalid("b")
            ) { a: Int, b: Int ->
                a + b
            }
        )
    }

    @Test
    fun `andThen both valid`() {
        assertEquals(
            valid(3),
            valid.andThen { valid(it + 2) }
        )
    }

    @Test
    fun `andThen first invalid`() {
        assertEquals(
            invalid("error"),
            invalid.andThen { valid(it + 2) }
        )
    }

    @Test
    fun `andThen second invalid`() {
        assertEquals(
            invalid("error: 1"),
            valid.andThen { invalid("error: $it") }
        )
    }

    @Test
    fun `orElse first valid`() {
        assertEquals(
            valid(1),
            valid.orElse { valid(2) }
        )
    }

    @Test
    fun `orElse second valid`() {
        assertEquals(
            valid(2),
            invalid.orElse { valid(2) }
        )
    }

    @Test
    fun `orElse both invalid`() {
        assertEquals(
            invalid("errors: 1"),
            invalid.orElse { errors -> invalid("errors: ${errors.size}") }
        )
    }
}
