package io.github.bbasinsk.schema.avro

import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.avro.BinarySerialization.serialize
import io.github.bbasinsk.schema.avro.ByteAllocation.ID_SIZE
import io.github.bbasinsk.schema.avro.ByteAllocation.MAGIC_BYTE
import org.apache.avro.io.EncoderFactory
import org.junit.Test
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer
import kotlin.test.assertContentEquals
import kotlin.test.assertNull

class BinarySerializationTest {
    // https://avro.apache.org/docs/1.10.2/spec.pdf

    @Test
    fun `empty as null`() {
        assertNull(Schema.empty().serialize(1, null))
    }

    @Test
    fun `optional empty`() {
        assertNull(Schema.string().optional().serialize(1, null))
    }

    @Test
    fun `boolean is encoded as 0 or 1`() {
        val id = 1
        assertContentEquals(prefixBytes(id) + byteArrayOf(0), Schema.boolean().serialize(id, false))
        assertContentEquals(prefixBytes(id) + byteArrayOf(1), Schema.boolean().serialize(id, true))
    }

    @Test
    fun `bytes are encoded directly`() {
        val id = 1
        val bytes = "hello world".toByteArray()
        assertContentEquals(prefixBytes(id) + bytes, Schema.byteArray().serialize(id, bytes))
    }

    @Test
    fun `int and long are encoded using variable length zigzag`() {
        val id = 1

        val int = 123
        val long = 321L

        assertContentEquals(prefixBytes(id) + int.toLong().toVariableLengthBytes(), Schema.int().serialize(id, int))
        assertContentEquals(prefixBytes(id) + long.toVariableLengthBytes(), Schema.long().serialize(id, long))
    }

    @Test
    fun `string is encoded as length followed by utf encoded character data`() {
        val id = 1
        val str = "hello world"
        val bytes = str.toByteArray(Charsets.UTF_8)
        val size = bytes.size.toLong().toVariableLengthBytes()
        assertContentEquals(prefixBytes(id) + size + bytes, Schema.string().serialize(id, str))
    }

    @Test
    fun `record is encoded in the order of the fields`() {
        val id = 1
        val record = RecordSmall(123L, "Hello")
        val bytes = record.a.toVariableLengthBytes() + record.b.toEncodedUtf8Bytes()
        assertContentEquals(prefixBytes(id) + bytes, Schema.recordSmall().serialize(id, record))
    }

    @Test
    fun `enum is encoded using the ordinal location`() {
        val id = 1
        val value = Role.DEVELOPER
        val bytes = value.ordinal.toLong().toVariableLengthBytes()
        assertContentEquals(prefixBytes(id) + bytes, Schema.enumeration<Role>().serialize(id, value))
    }

    @Test
    fun `unions are encoded using the ordinal position of the case`() {
        val id = 1
        val schema = Schema.unionSimple()
        val first = UnionSimple.First(123L)
        val second = UnionSimple.Second("Hello")

        val expectedFirst = 0L.toVariableLengthBytes() + first.data.toVariableLengthBytes()
        val expectedSecond = 1L.toVariableLengthBytes() + second.data.toEncodedUtf8Bytes()
        assertContentEquals(prefixBytes(id) + expectedFirst, schema.serialize(id, first))
        assertContentEquals(prefixBytes(id) + expectedSecond, schema.serialize(id, second))
    }

    @Test
    fun `arrays are encoded using size + contents + 0 ending`() {
        val id = 1
        val schema = Schema.list(Schema.long())
        val array = listOf(1L, 2L, 3L)
        val size = array.size.toLong().toVariableLengthBytes()
        val content = array.flatMap { it.toVariableLengthBytes().toList() }.toByteArray()
        assertContentEquals(prefixBytes(id) + size + content + byteArrayOf(0), schema.serialize(id, array))
    }

    @Test
    fun `string map is encoded using size + contents + 0 ending`() {
        val id = 1234
        val schema = Schema.stringMap(Schema.long())
        val value = mapOf("a" to 1L, "b" to 2L)
        val size = value.size.toLong().toVariableLengthBytes()
        val bytes = value.flatMap { (k, v) -> (k.toEncodedUtf8Bytes() + v.toVariableLengthBytes()).toList() }
            .toByteArray()
        assertContentEquals(prefixBytes(id) + size + bytes + byteArrayOf(0), schema.serialize(id, value))
    }

    private fun prefixBytes(id: Int): ByteArray =
        byteArrayOf(MAGIC_BYTE) + ByteBuffer.allocate(ID_SIZE).putInt(id).array()

    private fun Long.toVariableLengthBytes(): ByteArray =
        ByteArrayOutputStream().use { out ->
            EncoderFactory.get().directBinaryEncoder(out, null).also { it.writeLong(this) }
            out.flush()
            out.toByteArray()
        }

    private fun String.toEncodedUtf8Bytes(): ByteArray =
        toByteArray(Charsets.UTF_8).let { bytes ->
            bytes.size.toLong().toVariableLengthBytes() + bytes
        }
}

sealed interface UnionSimple {
    data class First(val data: Long) : UnionSimple
    data class Second(val data: String) : UnionSimple
}

fun Schema.Companion.first(): Schema<UnionSimple.First> =
    record(
        field("a", long()) { it.data },
        UnionSimple::First
    )

fun Schema.Companion.second(): Schema<UnionSimple.Second> =
    record(
        field("b", string()) { it.data },
        UnionSimple::Second
    )

fun Schema.Companion.unionSimple(): Schema<UnionSimple> =
    union(
        case(first()),
        case(second())
    )

fun Schema.Companion.unionFlipped(): Schema<UnionSimple> =
    union(
        case(second()),
        case(first())
    )
