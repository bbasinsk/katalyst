package io.github.bbasinsk.schema

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class NonReifiedGenericTest {

    @Test
    fun `non-reified generic schema produces helpful error message`() {
        val exception = assertFailsWith<IllegalStateException> {
            Schema.nonReifiedBox(Schema.string())
        }

        assertEquals(
            """
            |Type argument 'A' could not be resolved. This typically happens when using a generic schema function without 'inline reified'.
            |
            |To fix this, update your function signature from:
            |  fun <A> Schema.Companion.yourFunction(schema: Schema<A>): Schema<YourType<A>>
            |
            |To:
            |  inline fun <reified A> Schema.Companion.yourFunction(schema: Schema<A>): Schema<YourType<A>>
            """.trimMargin(),
            exception.message
        )
    }
}

private data class NonReifiedBox<T>(val value: T)

// This intentionally does NOT use inline reified to test the error message
private fun <A> Schema.Companion.nonReifiedBox(schema: Schema<A>): Schema<NonReifiedBox<A>> =
    Schema.record(
        Schema.field(schema, "value") { value },
        ::NonReifiedBox
    )
