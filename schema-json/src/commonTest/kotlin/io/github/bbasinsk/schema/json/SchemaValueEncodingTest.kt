@file:OptIn(ExperimentalEncodingApi::class)

package io.github.bbasinsk.schema.json

import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.SchemaValue
import io.github.bbasinsk.schema.orElse
import io.github.bbasinsk.schema.transform
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class SchemaValueEncodingTest {

    @Test
    fun `empty encodes to null`() {
        assertEquals(
            SchemaValue.Null,
            Schema.empty().encodeToSchemaValue(null)
        )
    }

    @Test
    fun `dynamic passes through`() {
        val sv = SchemaValue.Obj(mapOf("x" to SchemaValue.Integer(1)))
        assertEquals(sv, Schema.dynamic().encodeToSchemaValue(sv))
    }

    @Test
    fun `bytes encodes to base64 string`() {
        val bytes = byteArrayOf(1, 2, 3)
        assertEquals(
            SchemaValue.Str(Base64.encode(bytes)),
            Schema.byteArray().encodeToSchemaValue(bytes)
        )
    }

    @Test
    fun `boolean encodes to Bool`() {
        assertEquals(SchemaValue.Bool(true), Schema.boolean().encodeToSchemaValue(true))
        assertEquals(SchemaValue.Bool(false), Schema.boolean().encodeToSchemaValue(false))
    }

    @Test
    fun `string encodes to Str`() {
        assertEquals(SchemaValue.Str("hello"), Schema.string().encodeToSchemaValue("hello"))
    }

    @Test
    fun `int encodes to Integer`() {
        assertEquals(SchemaValue.Integer(42L), Schema.int().encodeToSchemaValue(42))
    }

    @Test
    fun `long encodes to Integer`() {
        assertEquals(SchemaValue.Integer(100L), Schema.long().encodeToSchemaValue(100L))
    }

    @Test
    fun `float encodes to Decimal`() {
        assertEquals(SchemaValue.Decimal(1.5), Schema.float().encodeToSchemaValue(1.5f))
    }

    @Test
    fun `double encodes to Decimal`() {
        assertEquals(SchemaValue.Decimal(3.14), Schema.double().encodeToSchemaValue(3.14))
    }

    enum class Color { RED, GREEN, BLUE }

    @Test
    fun `enumeration encodes to Str`() {
        assertEquals(
            SchemaValue.Str("GREEN"),
            Schema.enumeration<Color>().encodeToSchemaValue(Color.GREEN)
        )
    }

    @Test
    fun `optional non-null encodes value`() {
        assertEquals(
            SchemaValue.Integer(5L),
            Schema.int().optional().encodeToSchemaValue(5)
        )
    }

    @Test
    fun `optional null encodes to Null`() {
        assertEquals(
            SchemaValue.Null,
            Schema.int().optional().encodeToSchemaValue(null)
        )
    }

    @Test
    fun `default encodes value`() {
        assertEquals(
            SchemaValue.Integer(7L),
            Schema.int().default(0).encodeToSchemaValue(7)
        )
    }

    @Test
    fun `collection encodes to Arr`() {
        assertEquals(
            SchemaValue.Arr(listOf(SchemaValue.Integer(1L), SchemaValue.Integer(2L), SchemaValue.Integer(3L))),
            Schema.list(Schema.int()).encodeToSchemaValue(listOf(1, 2, 3))
        )
    }

    @Test
    fun `empty collection encodes to empty Arr`() {
        assertEquals(
            SchemaValue.Arr(emptyList()),
            Schema.list(Schema.string()).encodeToSchemaValue(emptyList())
        )
    }

    @Test
    fun `stringMap encodes to Obj`() {
        assertEquals(
            SchemaValue.Obj(mapOf("a" to SchemaValue.Integer(1L), "b" to SchemaValue.Integer(2L))),
            Schema.stringMap(Schema.int()).encodeToSchemaValue(mapOf("a" to 1, "b" to 2))
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
            SchemaValue.Obj(mapOf("x" to SchemaValue.Integer(1L), "y" to SchemaValue.Integer(2L))),
            pointSchema.encodeToSchemaValue(Point(1, 2))
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
            SchemaValue.Obj(mapOf("name" to SchemaValue.Str("Alice"), "alias" to SchemaValue.Null)),
            schema.encodeToSchemaValue(Named("Alice", null))
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
            SchemaValue.Obj(mapOf("type" to SchemaValue.Str("Circle"), "radius" to SchemaValue.Integer(5L))),
            shapeSchema.encodeToSchemaValue(Shape.Circle(5))
        )
    }

    @Test
    fun `union encodes second case`() {
        assertEquals(
            SchemaValue.Obj(
                mapOf(
                    "type" to SchemaValue.Str("Rect"),
                    "w" to SchemaValue.Integer(3L),
                    "h" to SchemaValue.Integer(4L)
                )
            ),
            shapeSchema.encodeToSchemaValue(Shape.Rect(3, 4))
        )
    }

    @Test
    fun `transform encodes through encode function`() {
        val schema = Schema.string().transform(
            decode = { s: String -> s.toInt() },
            encode = { i: Int -> i.toString() }
        )

        assertEquals(
            SchemaValue.Str("42"),
            schema.encodeToSchemaValue(42)
        )
    }

    @Test
    fun `lazy encodes through delegate`() {
        val schema = Schema.lazy { Schema.int() }
        assertEquals(SchemaValue.Integer(10L), schema.encodeToSchemaValue(10))
    }

    @Test
    fun `metadata passes through to inner schema`() {
        val schema = Schema.int().description("some number")
        assertEquals(SchemaValue.Integer(99L), schema.encodeToSchemaValue(99))
    }

    @Test
    fun `orElse encodes via preferred schema`() {
        val schema = Schema.int().orElse(Schema.string()) { it.toInt() }
        assertEquals(SchemaValue.Integer(42L), schema.encodeToSchemaValue(42))
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
            SchemaValue.Obj(
                mapOf(
                    "type" to SchemaValue.Str("Branch"),
                    "left" to SchemaValue.Obj(
                        mapOf("type" to SchemaValue.Str("Leaf"), "value" to SchemaValue.Integer(1L))
                    ),
                    "right" to SchemaValue.Obj(
                        mapOf("type" to SchemaValue.Str("Leaf"), "value" to SchemaValue.Integer(2L))
                    )
                )
            ),
            treeSchema.encodeToSchemaValue(tree)
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
            SchemaValue.Obj(mapOf("kind" to SchemaValue.Str("Circle"), "radius" to SchemaValue.Integer(5L))),
            schema.encodeToSchemaValue(Shape.Circle(5))
        )
    }

    @Test
    fun `collection of records encodes to Arr of Obj`() {
        assertEquals(
            SchemaValue.Arr(
                listOf(
                    SchemaValue.Obj(mapOf("x" to SchemaValue.Integer(1L), "y" to SchemaValue.Integer(2L))),
                    SchemaValue.Obj(mapOf("x" to SchemaValue.Integer(3L), "y" to SchemaValue.Integer(4L)))
                )
            ),
            Schema.list(pointSchema).encodeToSchemaValue(listOf(Point(1, 2), Point(3, 4)))
        )
    }

    @Test
    fun `empty stringMap encodes to empty Obj`() {
        assertEquals(
            SchemaValue.Obj(emptyMap()),
            Schema.stringMap(Schema.int()).encodeToSchemaValue(emptyMap())
        )
    }

    @Test
    fun `empty byte array encodes to empty base64 string`() {
        assertEquals(
            SchemaValue.Str(""),
            Schema.byteArray().encodeToSchemaValue(byteArrayOf())
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
            schema.encodeToSchemaValue(Wrapper.Text("hello"))
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
            schema.encodeToSchemaValue(Collide.A(Collider("oops")))
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
            SchemaValue.Obj(mapOf("name" to SchemaValue.Str("Alice"))),
            schema.encodeToSchemaValue(Named("Alice", null), config)
        )
    }

    @Test
    fun `orElse fallback encodes via preferred schema`() {
        val schema = Schema.int().orElse(Schema.string()) { it.toInt() }
        assertEquals(SchemaValue.Integer(42L), schema.encodeToSchemaValue(42))
    }
}
