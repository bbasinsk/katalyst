package io.github.bbasinsk.schema

import kotlin.test.Test
import kotlin.test.assertEquals

class JsSchemaMetadataTest {

    @Test
    fun `record metadata has correct name on js`() {
        val boxSchema = Schema.box(Schema.something)
        val metadata = (boxSchema as Schema.Record<*>).metadata
        assertEquals("Box", metadata.qualifiedName())
    }

    @Test
    fun `record metadata captures type arguments on js`() {
        val boxSchema = Schema.box(Schema.something)
        val metadata = (boxSchema as Schema.Record<*>).metadata
        assertEquals(1, metadata.typeArguments.size)
    }

    @Test
    fun `nested record metadata captures type arguments on js`() {
        val nested = Schema.box(Schema.box(Schema.box(Schema.something)))
        val metadata = (nested as Schema.Record<*>).metadata
        assertEquals(1, metadata.typeArguments.size)
    }
}

private data class Something(val name: String, val age: Int)
private data class Box<T>(val value: T)

private val Schema.Companion.something: Schema<Something>
    get() = record(
        field(string(), "name") { name },
        field(int(), "age") { age },
        ::Something
    )

private inline fun <reified A> Schema.Companion.box(schema: Schema<A>): Schema<Box<A>> =
    record(
        field(schema, "value") { value },
        ::Box
    )
