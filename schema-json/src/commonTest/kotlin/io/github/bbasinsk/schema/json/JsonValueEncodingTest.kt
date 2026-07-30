@file:OptIn(ExperimentalEncodingApi::class)

package io.github.bbasinsk.schema.json

import io.github.bbasinsk.schema.JsonValue
import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.orElse
import io.github.bbasinsk.schema.transform
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class JsonValueEncodingTest {

    @Test
    fun `empty encodes to null`() {
        assertEquals(
            JsonValue.Null,
            Schema.empty().encodeToJsonValue(null)
        )
    }

    @Test
    fun `dynamic passes through`() {
        val sv = JsonValue.Obj(mapOf("x" to JsonValue.Number("1")))
        assertEquals(sv, Schema.dynamic().encodeToJsonValue(sv))
    }

    @Test
    fun `bytes encodes to base64 string`() {
        val bytes = byteArrayOf(1, 2, 3)
        assertEquals(
            JsonValue.Str(Base64.encode(bytes)),
            Schema.byteArray().encodeToJsonValue(bytes)
        )
    }

    @Test
    fun `boolean encodes to Bool`() {
        assertEquals(JsonValue.Bool(true), Schema.boolean().encodeToJsonValue(true))
        assertEquals(JsonValue.Bool(false), Schema.boolean().encodeToJsonValue(false))
    }

    @Test
    fun `string encodes to Str`() {
        assertEquals(JsonValue.Str("hello"), Schema.string().encodeToJsonValue("hello"))
    }

    @Test
    fun `int encodes to Integer`() {
        assertEquals(JsonValue.Number("42"), Schema.int().encodeToJsonValue(42))
    }

    @Test
    fun `long encodes to Integer`() {
        assertEquals(JsonValue.Number("100"), Schema.long().encodeToJsonValue(100L))
    }

    @Test
    fun `float encodes to Decimal`() {
        assertEquals(JsonValue.Number("1.5"), Schema.float().encodeToJsonValue(1.5f))
    }

    @Test
    fun `double encodes to Decimal`() {
        assertEquals(JsonValue.Number("3.14"), Schema.double().encodeToJsonValue(3.14))
    }

    enum class Color { RED, GREEN, BLUE }

    @Test
    fun `enumeration encodes to Str`() {
        assertEquals(
            JsonValue.Str("GREEN"),
            Schema.enumeration<Color>().encodeToJsonValue(Color.GREEN)
        )
    }

    @Test
    fun `optional non-null encodes value`() {
        assertEquals(
            JsonValue.Number("5"),
            Schema.int().optional().encodeToJsonValue(5)
        )
    }

    @Test
    fun `optional null encodes to Null`() {
        assertEquals(
            JsonValue.Null,
            Schema.int().optional().encodeToJsonValue(null)
        )
    }

    @Test
    fun `default encodes value`() {
        assertEquals(
            JsonValue.Number("7"),
            Schema.int().default(0).encodeToJsonValue(7)
        )
    }

    @Test
    fun `collection encodes to Arr`() {
        assertEquals(
            JsonValue.Arr(listOf(JsonValue.Number("1"), JsonValue.Number("2"), JsonValue.Number("3"))),
            Schema.list(Schema.int()).encodeToJsonValue(listOf(1, 2, 3))
        )
    }

    @Test
    fun `empty collection encodes to empty Arr`() {
        assertEquals(
            JsonValue.Arr(emptyList()),
            Schema.list(Schema.string()).encodeToJsonValue(emptyList())
        )
    }

    @Test
    fun `stringMap encodes to Obj`() {
        assertEquals(
            JsonValue.Obj(mapOf("a" to JsonValue.Number("1"), "b" to JsonValue.Number("2"))),
            Schema.stringMap(Schema.int()).encodeToJsonValue(mapOf("a" to 1, "b" to 2))
        )
    }

    data class Point(val x: Int, val y: Int)

    private val pointSchema: Schema<Point> = Schema.record(
        Schema.field(Schema.int(), "x") { x },
        Schema.field(Schema.int(), "y") { y },
        ::Point
    )

    @Test
    fun `record encodes to Obj`() {
        assertEquals(
            JsonValue.Obj(mapOf("x" to JsonValue.Number("1"), "y" to JsonValue.Number("2"))),
            pointSchema.encodeToJsonValue(Point(1, 2))
        )
    }

    @Test
    fun `record with optional null field includes null`() {
        data class Named(val name: String, val alias: String?)

        val schema = Schema.record(
            Schema.field(Schema.string(), "name") { name },
            Schema.field(Schema.string().optional(), "alias") { alias },
            ::Named
        )

        assertEquals(
            JsonValue.Obj(mapOf("name" to JsonValue.Str("Alice"), "alias" to JsonValue.Null)),
            schema.encodeToJsonValue(Named("Alice", null))
        )
    }

    sealed interface Shape {
        data class Circle(val radius: Int) : Shape
        data class Rect(val w: Int, val h: Int) : Shape
    }

    sealed interface Tree {
        data class Leaf(val value: Int) : Tree
        data class Branch(val left: Tree, val right: Tree) : Tree
    }

    private val shapeSchema: Schema<Shape> = Schema.union(
        Schema.case(
            Schema.record(
                Schema.field(Schema.int(), "radius") { radius },
                Shape::Circle
            ),
            "Circle"
        ),
        Schema.case(
            Schema.record(
                Schema.field(Schema.int(), "w") { w },
                Schema.field(Schema.int(), "h") { h },
                Shape::Rect
            ),
            "Rect"
        )
    )

    @Test
    fun `union encodes with discriminator`() {
        assertEquals(
            JsonValue.Obj(mapOf("type" to JsonValue.Str("Circle"), "radius" to JsonValue.Number("5"))),
            shapeSchema.encodeToJsonValue(Shape.Circle(5))
        )
    }

    @Test
    fun `union encodes second case`() {
        assertEquals(
            JsonValue.Obj(
                mapOf(
                    "type" to JsonValue.Str("Rect"),
                    "w" to JsonValue.Number("3"),
                    "h" to JsonValue.Number("4")
                )
            ),
            shapeSchema.encodeToJsonValue(Shape.Rect(3, 4))
        )
    }

    @Test
    fun `transform encodes through encode function`() {
        val schema = Schema.string().transform(
            decode = { s: String -> s.toInt() },
            encode = { i: Int -> i.toString() }
        )

        assertEquals(
            JsonValue.Str("42"),
            schema.encodeToJsonValue(42)
        )
    }

    @Test
    fun `lazy encodes through delegate`() {
        val schema = Schema.lazy { Schema.int() }
        assertEquals(JsonValue.Number("10"), schema.encodeToJsonValue(10))
    }

    @Test
    fun `metadata passes through to inner schema`() {
        val schema = Schema.int().description("some number")
        assertEquals(JsonValue.Number("99"), schema.encodeToJsonValue(99))
    }

    @Test
    fun `orElse encodes via preferred schema`() {
        val schema = Schema.int().orElse(Schema.string()) { it.toInt() }
        assertEquals(JsonValue.Number("42"), schema.encodeToJsonValue(42))
    }

    @Test
    fun `recursive lazy encodes tree structure`() {
        lateinit var treeSchema: Schema<Tree>
        treeSchema = Schema.union(
            Schema.case<Tree, Tree.Leaf>(
                Schema.record(
                    Schema.field(Schema.int(), "value") { value },
                    Tree::Leaf
                ),
                "Leaf"
            ),
            Schema.case<Tree, Tree.Branch>(
                Schema.record(
                    Schema.field(Schema.lazy { treeSchema }, "left") { left },
                    Schema.field(Schema.lazy { treeSchema }, "right") { right },
                    Tree::Branch
                ),
                "Branch"
            )
        )

        val tree = Tree.Branch(Tree.Leaf(1), Tree.Leaf(2))
        assertEquals(
            JsonValue.Obj(
                mapOf(
                    "type" to JsonValue.Str("Branch"),
                    "left" to JsonValue.Obj(
                        mapOf("type" to JsonValue.Str("Leaf"), "value" to JsonValue.Number("1"))
                    ),
                    "right" to JsonValue.Obj(
                        mapOf("type" to JsonValue.Str("Leaf"), "value" to JsonValue.Number("2"))
                    )
                )
            ),
            treeSchema.encodeToJsonValue(tree)
        )
    }

    @Test
    fun `union with custom discriminator key`() {
        val schema = Schema.union(
            Schema.case<Shape, Shape.Circle>(
                Schema.record(
                    Schema.field(Schema.int(), "radius") { radius },
                    Shape::Circle
                ),
                "Circle"
            ),
            Schema.case<Shape, Shape.Rect>(
                Schema.record(
                    Schema.field(Schema.int(), "w") { w },
                    Schema.field(Schema.int(), "h") { h },
                    Shape::Rect
                ),
                "Rect"
            ),
            key = "kind"
        )

        assertEquals(
            JsonValue.Obj(mapOf("kind" to JsonValue.Str("Circle"), "radius" to JsonValue.Number("5"))),
            schema.encodeToJsonValue(Shape.Circle(5))
        )
    }

    @Test
    fun `collection of records encodes to Arr of Obj`() {
        assertEquals(
            JsonValue.Arr(
                listOf(
                    JsonValue.Obj(mapOf("x" to JsonValue.Number("1"), "y" to JsonValue.Number("2"))),
                    JsonValue.Obj(mapOf("x" to JsonValue.Number("3"), "y" to JsonValue.Number("4")))
                )
            ),
            Schema.list(pointSchema).encodeToJsonValue(listOf(Point(1, 2), Point(3, 4)))
        )
    }

    @Test
    fun `empty stringMap encodes to empty Obj`() {
        assertEquals(
            JsonValue.Obj(emptyMap()),
            Schema.stringMap(Schema.int()).encodeToJsonValue(emptyMap())
        )
    }

    @Test
    fun `empty byte array encodes to empty base64 string`() {
        assertEquals(
            JsonValue.Str(""),
            Schema.byteArray().encodeToJsonValue(byteArrayOf())
        )
    }

    sealed interface Wrapper {
        data class Text(val value: String) : Wrapper
    }

    @Test
    fun `union case with non-record schema throws`() {
        val schema = Schema.union(
            Schema.case<Wrapper, Wrapper.Text>(
                Schema.string().transform(Wrapper::Text) { it.value },
                "Text"
            )
        )

        assertFailsWith<IllegalStateException> {
            schema.encodeToJsonValue(Wrapper.Text("hello"))
        }
    }

    data class Collider(val type: String)

    sealed interface Collide {
        data class A(val bad: Collider) : Collide
    }

    @Test
    fun `discriminator collision throws`() {
        val schema = Schema.union(
            Schema.case<Collide, Collide.A>(
                Schema.record(
                    Schema.field(Schema.string(), "type") { bad.type },
                    { Collide.A(Collider(it)) }
                ),
                "A"
            )
        )

        assertFailsWith<IllegalArgumentException> {
            schema.encodeToJsonValue(Collide.A(Collider("oops")))
        }
    }

    @Test
    fun `explicitNulls false excludes null optional fields`() {
        data class Named(val name: String, val alias: String?)

        val schema = Schema.record(
            Schema.field(Schema.string(), "name") { name },
            Schema.field(Schema.string().optional(), "alias") { alias },
            ::Named
        )
        val config = JsonEncodingConfig(explicitNulls = false)

        assertEquals(
            JsonValue.Obj(mapOf("name" to JsonValue.Str("Alice"))),
            schema.encodeToJsonValue(Named("Alice", null), config)
        )
    }

    @Test
    fun `orElse fallback encodes via preferred schema`() {
        val schema = Schema.int().orElse(Schema.string()) { it.toInt() }
        assertEquals(JsonValue.Number("42"), schema.encodeToJsonValue(42))
    }

    // -- Key ordering --

    data class Ordered(val z: Int, val m: Int, val a: Int, val q: Int, val b: Int)

    @Test
    fun `record encodeToJsonValue preserves schema-defined key order`() {
        val schema = Schema.record(
            Schema.field(Schema.int(), "z") { z },
            Schema.field(Schema.int(), "m") { m },
            Schema.field(Schema.int(), "a") { a },
            Schema.field(Schema.int(), "q") { q },
            Schema.field(Schema.int(), "b") { b },
            ::Ordered
        )

        val obj = schema.encodeToJsonValue(Ordered(1, 2, 3, 4, 5)) as JsonValue.Obj
        assertEquals(listOf("z", "m", "a", "q", "b"), obj.entries.keys.toList())
    }

    @Test
    fun `record encodeToJsonValue to JSON string preserves key order`() {
        val schema = Schema.record(
            Schema.field(Schema.int(), "z") { z },
            Schema.field(Schema.int(), "m") { m },
            Schema.field(Schema.int(), "a") { a },
            Schema.field(Schema.int(), "q") { q },
            Schema.field(Schema.int(), "b") { b },
            ::Ordered
        )

        val json = schema.encodeToJsonValue(Ordered(1, 2, 3, 4, 5)).encodeToJsonString()
        assertEquals("""{"z":1,"m":2,"a":3,"q":4,"b":5}""", json)
    }

    @Test
    fun `record encodeToJsonValue to pretty JSON string preserves key order`() {
        val schema = Schema.record(
            Schema.field(Schema.int(), "z") { z },
            Schema.field(Schema.int(), "m") { m },
            Schema.field(Schema.int(), "a") { a },
            Schema.field(Schema.int(), "q") { q },
            Schema.field(Schema.int(), "b") { b },
            ::Ordered
        )

        val prettyConfig = JsonEncodingConfig(printConfig = JsonEncodingConfig.PrintConfig.pretty())
        val json = schema.encodeToJsonValue(Ordered(1, 2, 3, 4, 5)).encodeToJsonString(prettyConfig)
        val expected = "{\n  \"z\": 1,\n  \"m\": 2,\n  \"a\": 3,\n  \"q\": 4,\n  \"b\": 5\n}"
        assertEquals(expected, json)
    }
}
