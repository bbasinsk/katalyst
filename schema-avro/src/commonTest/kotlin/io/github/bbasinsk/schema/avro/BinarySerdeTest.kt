package io.github.bbasinsk.schema.avro

import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.avro.BinaryDeserialization.deserialize
import io.github.bbasinsk.schema.avro.BinaryDeserialization.deserializeIgnoringSchemaId
import io.github.bbasinsk.schema.avro.BinarySerialization.serialize
import io.github.bbasinsk.schema.java.uuid
import io.github.bbasinsk.schema.optional
import io.github.bbasinsk.validation.Validation
import org.junit.Test
import java.util.UUID
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertIs

class BinarySerdeTest {
    // https://avro.apache.org/docs/1.10.2/spec.pdf

    @Test
    fun `empty as null`() {
        assertValid(null, Schema.empty().serializeAndDeserialize(null))
    }

    @Test
    fun `optional empty`() {
        assertValid(null, Schema.string().optional().serializeAndDeserialize(null))
    }

    @Test
    fun `optional non-empty`() {
        val value = "hello world"
        assertValid(value, Schema.string().optional().serializeAndDeserialize(value))
    }

    @Test
    fun `primitive boolean`() {
        val value = true
        assertValid(value, Schema.boolean().serializeAndDeserialize(value))
    }

    @Test
    fun `primitive int`() {
        val value = 41
        assertValid(value, Schema.int().serializeAndDeserialize(value))
    }

    @Test
    fun `primitive long`() {
        val value = 123L
        assertValid(value, Schema.long().serializeAndDeserialize(value))
    }

    @Test
    fun `primitive double`() {
        val value = 1.5
        assertValid(value, Schema.double().serializeAndDeserialize(value))
    }

    @Test
    fun `primitive float`() {
        val value = 1.1f
        assertValid(value, Schema.float().serializeAndDeserialize(value))
    }

    @Test
    fun `primitive string`() {
        val value = "hello world"
        assertValid(value, Schema.string().serializeAndDeserialize(value))
    }

    @Test
    fun `bytes simple`() {
        val value = byteArrayOf(1, 2, 3, 4)
        val found = assertIs<Validation.Valid<ByteArray>>(Schema.byteArray().serializeAndDeserialize(value)).value
        assertContentEquals(value, found)
    }

    @Test
    fun `transform uuid`() {
        val value = UUID.randomUUID()
        assertValid(value, Schema.uuid().serializeAndDeserialize(value))
    }

    @Test
    fun `enum simple`() {
        val value = Role.entries.random()
        assertValid(value, Schema.enumeration<Role>().serializeAndDeserialize(value))
    }

    @Test
    fun `record simple`() {
        val value = RecordSmall(1L, "a")
        val id = 1
        val bytes = Schema.recordSmall().serialize(id, value)
        print(String(bytes!!))
        val thing = Schema.recordSmall().deserializeIgnoringSchemaId(bytes)
        assertValid(value, thing)
    }

    @Test
    fun `union simple`() {
        val first = UnionSimple.First(123L)
        assertValid(first, Schema.unionSimple().serializeAndDeserialize(first))

        val second = UnionSimple.Second("data")
        assertValid(second, Schema.unionSimple().serializeAndDeserialize(second))
    }

    @Test
    fun `union flipped`() {
        val first = UnionSimple.First(123L)
        assertValid(first, Schema.unionSimple().serializeAndDeserialize(first, Schema.unionFlipped()))

        val second = UnionSimple.Second("data")
        assertValid(second, Schema.unionSimple().serializeAndDeserialize(second, Schema.unionFlipped()))
    }

    @Test
    fun `string record map`() {
        val value = mapOf("a" to RecordSmall(1L, "a"), "b" to RecordSmall(2L, "b"))
        assertValid(value, Schema.recordMap().serializeAndDeserialize(value))
    }

    private fun <A> Schema<A>.serializeAndDeserialize(
        value: A,
        readerSchema: Schema<A> = this,
        id: Int = 1
    ): Validation<InvalidField, A> =
        readerSchema.deserialize(serialize(id, value)) { toAvroSchema() }

    private fun <A> assertValid(expected: A, validation: Validation<*, A>) {
        assertEquals(expected, assertIs<Validation.Valid<A>>(validation).value)
    }
}
