package io.github.bbasinsk.schema

import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class NonReifiedGenericTest {

    @Test
    fun `non-reified generic schema produces helpful error message`() {
        val exception = assertFailsWith<IllegalStateException> {
            Schema.nonReifiedBox(Schema.string())
        }
        
        assertTrue(
            exception.message!!.contains("Type argument"),
            "Error message should mention 'Type argument'"
        )
        assertTrue(
            exception.message!!.contains("inline reified"),
            "Error message should mention 'inline reified'"
        )
        assertTrue(
            exception.message!!.contains("inline fun <reified A>"),
            "Error message should show the correct function signature"
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
