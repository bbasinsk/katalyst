package io.github.bbasinsk.schema.json.kotlinx

import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.json.InvalidJson
import io.github.bbasinsk.schema.transform
import io.github.bbasinsk.validation.Validation
import io.github.bbasinsk.validation.mapValid
import kotlin.test.Test
import kotlin.test.assertEquals

class PrimitiveSerdeTest {

    @Test
    fun `empty serializes to null`() {
        val schema = Schema.empty()
        assertEquals("null", schema.encodeToJsonString(null))
    }

    @Test
    fun `empty deserializes with no content`() {
        val schema = Schema.empty()
        assertEquals(
            Validation.valid(null),
            schema.decodeFromJsonString("")
        )
    }

    @Test
    fun `empty deserializes with null`() {
        val schema = Schema.empty()
        assertEquals(
            Validation.valid(null),
            schema.decodeFromJsonString("null")
        )
    }

    @Test
    fun `empty deserializes with empty object`() {
        val schema = Schema.empty()
        assertEquals(
            Validation.valid(null),
            schema.decodeFromJsonString("{}")
        )
    }

    @Test
    fun `int serializes with valid value`() {
        val schema = Schema.int()
        assertEquals("42", schema.encodeToJsonString(42))
    }

    @Test
    fun `int deserializes with valid json`() {
        val schema = Schema.int()

        assertEquals(
            Validation.valid(42),
            schema.decodeFromJsonString("42")
        )
    }

    @Test
    fun `int fails to deserialize with invalid value`() {
        val schema = Schema.int()

        assertEquals(
            Validation.invalid(
                InvalidJson.FieldError(
                    expected = "Int",
                    found = """"not-an-int"""",
                    path = listOf()
                )
            ),
            schema.decodeFromJsonString(""""not-an-int"""")
        )
    }

    @Test
    fun `string serializes with valid value`() {
        val schema = Schema.string()
        assertEquals(""""hello"""", schema.encodeToJsonString("hello"))
    }

    @Test
    fun `string deserializes with valid json`() {
        val schema = Schema.string()

        assertEquals(
            Validation.valid("hello"),
            schema.decodeFromJsonString(""""hello"""")
        )
    }

    @Test
    fun `string fails deserialize with invalid value`() {
        val schema = Schema.string()

        assertEquals(
            Validation.invalid(
                InvalidJson.FieldError(
                    expected = "String",
                    found = "42",
                    path = listOf()
                )
            ),
            schema.decodeFromJsonString("42")
        )
    }

    @Test
    fun `boolean serializes with valid value`() {
        val schema = Schema.boolean()
        assertEquals("true", schema.encodeToJsonString(true))
    }

    @Test
    fun `boolean deserializes with valid json`() {
        val schema = Schema.boolean()

        assertEquals(
            Validation.valid(true),
            schema.decodeFromJsonString("true")
        )
    }

    @Test
    fun `boolean fails deserialize with invalid value`() {
        val schema = Schema.boolean()
        assertEquals(
            Validation.invalid(
                InvalidJson.FieldError(
                    expected = "Boolean",
                    found = "42",
                    path = listOf()
                )
            ),
            schema.decodeFromJsonString("42")
        )
    }

    @Test
    fun `double serializes with valid value`() {
        val schema = Schema.double()
        assertEquals("42.0", schema.encodeToJsonString(42.0))
    }

    @Test
    fun `double deserializes with valid json`() {
        val schema = Schema.double()

        assertEquals(
            Validation.valid(42.0),
            schema.decodeFromJsonString("42.0")
        )
    }

    @Test
    fun `double fails deserialize with invalid value`() {
        val schema = Schema.double()

        assertEquals(
            Validation.invalid(
                InvalidJson.FieldError(
                    expected = "Double",
                    found = "not-a-number",
                    path = listOf()
                )
            ),
            schema.decodeFromJsonString("not-a-number")
        )
    }

    @Test
    fun `float serializes with valid value`() {
        val schema = Schema.float()
        assertEquals("42.0", schema.encodeToJsonString(42.0f))
    }

    @Test
    fun `float deserializes with valid json`() {
        val schema = Schema.float()

        assertEquals(
            Validation.valid(42.0f),
            schema.decodeFromJsonString("42.0")
        )
    }

    @Test
    fun `float fails deserialize with invalid value`() {
        val schema = Schema.float()

        assertEquals(
            Validation.invalid(
                InvalidJson.FieldError(
                    expected = "Float",
                    found = "not-a-number",
                    path = listOf()
                )
            ),
            schema.decodeFromJsonString("not-a-number")
        )
    }

    @Test
    fun `long serializes with valid value`() {
        val schema = Schema.long()
        assertEquals("30000000000", schema.encodeToJsonString(30000000000L))
    }

    @Test
    fun `long deserializes with valid json`() {
        val schema = Schema.long()

        assertEquals(
            Validation.valid(30000000000L),
            schema.decodeFromJsonString("30000000000")
        )
    }

    @Test
    fun `long fails deserialize with invalid value`() {
        val schema = Schema.long()

        assertEquals(
            Validation.invalid(
                InvalidJson.FieldError(
                    expected = "Long",
                    found = "42.0",
                    path = listOf()
                )
            ),
            schema.decodeFromJsonString("42.0")
        )
    }

    enum class Color {
        RED, GREEN, BLUE
    }

    @Test
    fun `enumeration serializes with valid value`() {
        val schema = Schema.enumeration<Color>()
        assertEquals(""""RED"""", schema.encodeToJsonString(Color.RED))
    }

    @Test
    fun `enumeration deserializes with valid json`() {
        val schema = Schema.enumeration<Color>()

        assertEquals(
            Validation.valid(Color.RED),
            schema.decodeFromJsonString(""""RED"""")
        )
    }

    @Test
    fun `enumeration fails deserialize when invalid value`() {
        val schema = Schema.enumeration<Color>()

        assertEquals(
            Validation.invalid(
                InvalidJson.FieldError(
                    expected = "Color",
                    found = """"BAD"""",
                    path = listOf()
                )
            ),
            schema.decodeFromJsonString(""""BAD"""")
        )
    }

    @Test
    fun `enumeration fails deserialize when invalid type`() {
        val schema = Schema.enumeration<Color>()

        assertEquals(
            Validation.invalid(
                InvalidJson.FieldError(
                    expected = "Color",
                    found = "[]",
                    path = listOf()
                )
            ),
            schema.decodeFromJsonString("""[]""")
        )
    }

    sealed interface Tree {
        data class Leaf(val value: Int) : Tree
        data class Branch(val value: Tree) : Tree
    }

    private fun Schema.Companion.leaf(): Schema<Tree.Leaf> =
        record(
            field(int(), "value") { value },
            Tree::Leaf
        )

    private fun Schema.Companion.branch(): Schema<Tree.Branch> =
        record(
            field(lazy { tree() }, "value") { value },
            Tree::Branch
        )

    private fun Schema.Companion.tree(): Schema<Tree> =
        union(
            case(leaf()),
            case(branch())
        )

    @Test
    fun `lazy serializes with valid value`() {
        val schema = Schema.tree()
        assertEquals(
            """{"type":"Branch","value":{"type":"Leaf","value":42}}""",
            schema.encodeToJsonString(Tree.Branch(Tree.Leaf(42)))
        )
    }

    @Test
    fun `lazy deserializes`() {
        val schema = Schema.tree()
        assertEquals(
            Validation.valid(Tree.Branch(Tree.Leaf(42))),
            schema.decodeFromJsonString("""{"type": "Branch", "value": {"type": "Leaf", "value": 42}}""")
        )
    }

    @Test
    fun `default deserializes with specified default`() {
        val schema = Schema.int().default(42)
        assertEquals(Validation.valid(42), schema.decodeFromJsonString("null"))
    }

    @Test
    fun `default serializes with null`() {
        val schema = Schema.int().optional().default(42)
        assertEquals("null", schema.encodeToJsonString(null))
    }

    @Test
    fun `optional deserializes with null`() {
        val schema = Schema.int().optional()
        assertEquals(Validation.valid(null), schema.decodeFromJsonString("null"))
    }

    @Test
    fun `optional serializes with null`() {
        val schema = Schema.int().optional()
        assertEquals("null", schema.encodeToJsonString(null))
    }

    @Test
    fun `byteArray serializes as base64 encoded`() {
        val schema = Schema.byteArray()
        assertEquals(
            """"c29tZSBiYXNlNjQgY29udGVudA=="""",
            schema.encodeToJsonString("some base64 content".encodeToByteArray())
        )
    }

    @Test
    fun `byteArray deserializes with valid json`() {
        val schema = Schema.byteArray()
        assertEquals(
            Validation.valid("some base64 content"),
            schema.decodeFromJsonString("c29tZSBiYXNlNjQgY29udGVudA==").mapValid { it.decodeToString() }
        )
    }

    @Test
    fun `transform fail to deserialize with bad decode `() {
        val uuidRegex = Regex("""^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$""")
        data class UUID(val value: String) {
            init {
                require(value.matches(uuidRegex)) { "Invalid UUID string: $value" }
            }
        }
        val uuidSchema = Schema.string().transform(
            decode = { UUID(it) }
        ) { it.toString() }

        assertEquals(
            Validation.invalid(
                InvalidJson.FieldError(
                    expected = "UUID",
                    found = """"Invalid UUID string: not-a-uuid"""",
                    path = listOf()
                )
            ),
            uuidSchema.decodeFromJsonString(""""not-a-uuid"""")
        )
    }
}
