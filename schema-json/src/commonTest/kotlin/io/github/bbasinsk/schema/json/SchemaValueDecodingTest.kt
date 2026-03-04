package io.github.bbasinsk.schema.json

import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.Schema.Companion.case
import io.github.bbasinsk.schema.SchemaValue
import io.github.bbasinsk.schema.orElse
import io.github.bbasinsk.schema.transform
import io.github.bbasinsk.validation.Validation
import io.github.bbasinsk.validation.mapValid
import io.github.bbasinsk.schema.json.decodeFromJsonString
import io.github.bbasinsk.schema.json.kotlinx.decodeFromJsonString as kotlinxDecodeFromJsonString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SchemaValueDecodingTest {

    private fun <A> assertRoundTrip(schema: Schema<A>, value: A) {
        val json = schema.encodeToJsonString(value)
        assertEquals(Validation.valid(value), schema.decodeFromJsonString(json))
    }

    private fun <A> assertMatchesKotlinx(schema: Schema<A>, json: String) {
        val newResult = schema.decodeFromJsonString(json)
        val kotlinxResult = schema.kotlinxDecodeFromJsonString(json, Json.Default)
        assertEquals(kotlinxResult, newResult)
    }

    // Schema definitions

    data class Point(val x: Int, val y: Int)
    fun Schema.Companion.point(): Schema<Point> = record(
        field(int(), "x") { x },
        field(int(), "y") { y },
        ::Point
    )

    data class Line(val start: Point, val end: Point)
    fun Schema.Companion.line(): Schema<Line> = record(
        field(point(), "start") { start },
        field(point(), "end") { end },
        ::Line
    )

    data class Named(val name: String, val age: Int?)
    fun Schema.Companion.named(): Schema<Named> = record(
        field(string(), "name") { name },
        field(int().optional(), "age") { age },
        ::Named
    )

    sealed interface Shape {
        data class Circle(val radius: Int) : Shape
        data class Rect(val w: Int, val h: Int) : Shape
    }
    fun Schema.Companion.circle(): Schema<Shape.Circle> = record(
        field(int(), "radius") { radius },
        Shape::Circle
    )
    fun Schema.Companion.rect(): Schema<Shape.Rect> = record(
        field(int(), "w") { w },
        field(int(), "h") { h },
        Shape::Rect
    )
    fun Schema.Companion.shape(): Schema<Shape> = union(
        case(circle()),
        case(rect())
    )

    data class UserId(val value: Int)
    fun Schema.Companion.userId(): Schema<UserId> =
        int().transform(::UserId) { it.value }

    sealed interface Tree {
        data class Leaf(val value: Int) : Tree
        data class Branch(val left: Tree, val right: Tree) : Tree
    }
    fun Schema.Companion.leaf(): Schema<Tree.Leaf> = record(
        field(int(), "value") { value },
        Tree::Leaf
    )
    fun Schema.Companion.branch(): Schema<Tree.Branch> = record(
        field(lazy { tree() }, "left") { left },
        field(lazy { tree() }, "right") { right },
        Tree::Branch
    )
    fun Schema.Companion.tree(): Schema<Tree> = union(
        case(leaf()),
        case(branch())
    )

    // Round-trip tests

    @Test
    fun `round-trip int`() = assertRoundTrip(Schema.int(), 42)

    @Test
    fun `round-trip negative int`() = assertRoundTrip(Schema.int(), -7)

    @Test
    fun `round-trip string`() = assertRoundTrip(Schema.string(), "hello")

    @Test
    fun `round-trip empty string`() = assertRoundTrip(Schema.string(), "")

    @Test
    fun `round-trip boolean true`() = assertRoundTrip(Schema.boolean(), true)

    @Test
    fun `round-trip boolean false`() = assertRoundTrip(Schema.boolean(), false)

    @Test
    fun `round-trip double`() = assertRoundTrip(Schema.double(), 3.14)

    @Test
    fun `round-trip float`() = assertRoundTrip(Schema.float(), 2.5f)

    @Test
    fun `round-trip long`() = assertRoundTrip(Schema.long(), 30000000000L)

    enum class Color { RED, GREEN, BLUE }

    @Test
    fun `round-trip enum`() = assertRoundTrip(Schema.enumeration<Color>(), Color.GREEN)

    @Test
    fun `round-trip optional null`() = assertRoundTrip(Schema.int().optional(), null)

    @Test
    fun `round-trip optional present`() = assertRoundTrip(Schema.int().optional(), 42)

    @Test
    fun `round-trip default value`() = assertRoundTrip(Schema.int().default(0), 99)

    @Test
    fun `round-trip default from null`() {
        val schema = Schema.int().default(42)
        assertEquals(Validation.valid(42), schema.decodeFromJsonString("null"))
    }

    @Test
    fun `round-trip byteArray`() {
        val schema = Schema.byteArray()
        val original = "hello bytes".encodeToByteArray()
        val json = schema.encodeToJsonString(original)
        val decoded = schema.decodeFromJsonString(json)
        assertEquals(
            Validation.valid("hello bytes"),
            decoded.mapValid { it.decodeToString() }
        )
    }

    @Test
    fun `round-trip empty list`() = assertRoundTrip(Schema.list(Schema.int()), emptyList())

    @Test
    fun `round-trip list`() = assertRoundTrip(Schema.list(Schema.int()), listOf(1, 2, 3))

    @Test
    fun `round-trip nested list`() = assertRoundTrip(
        Schema.list(Schema.list(Schema.string())),
        listOf(listOf("a", "b"), listOf("c"))
    )

    @Test
    fun `round-trip empty stringMap`() = assertRoundTrip(Schema.stringMap(Schema.int()), emptyMap())

    @Test
    fun `round-trip stringMap`() = assertRoundTrip(
        Schema.stringMap(Schema.int()),
        mapOf("a" to 1, "b" to 2)
    )

    @Test
    fun `round-trip simple record`() = assertRoundTrip(Schema.point(), Point(1, 2))

    @Test
    fun `round-trip nested record`() = assertRoundTrip(
        Schema.line(),
        Line(Point(0, 0), Point(3, 4))
    )

    @Test
    fun `round-trip record with null optional`() = assertRoundTrip(
        Schema.named(),
        Named("Alice", null)
    )

    @Test
    fun `round-trip record with present optional`() = assertRoundTrip(
        Schema.named(),
        Named("Bob", 30)
    )

    @Test
    fun `round-trip union first case`() = assertRoundTrip(
        Schema.shape(),
        Shape.Circle(5)
    )

    @Test
    fun `round-trip union second case`() = assertRoundTrip(
        Schema.shape(),
        Shape.Rect(3, 4)
    )

    @Test
    fun `round-trip transform`() = assertRoundTrip(Schema.userId(), UserId(42))

    @Test
    fun `round-trip recursive tree`() = assertRoundTrip(
        Schema.tree(),
        Tree.Branch(Tree.Leaf(1), Tree.Branch(Tree.Leaf(2), Tree.Leaf(3)))
    )

    // OrElse

    sealed interface Event {
        data class Click(val x: Int, val y: Int) : Event
        data class Key(val code: Int) : Event
    }

    private data class ClickStrings(val x: String, val y: String)

    private fun Schema.Companion.click(): Schema<Event.Click> = record(
        field(int(), "x") { x },
        field(int(), "y") { y },
        Event::Click
    )

    private fun Schema.Companion.clickOrElse(): Schema<Event.Click> =
        click().orElse(
            record(
                field(string(), "x") { x },
                field(string(), "y") { y },
                ::ClickStrings
            )
        ) { Event.Click(it.x.toInt(), it.y.toInt()) }

    private fun Schema.Companion.key(): Schema<Event.Key> = record(
        field(int(), "code") { code },
        Event::Key
    )

    private fun Schema.Companion.eventWithOrElse(): Schema<Event> = union(
        case(clickOrElse()),
        case(key())
    )

    @Test
    fun `round-trip orElse preferred path`() = assertRoundTrip(
        Schema.eventWithOrElse(),
        Event.Click(10, 20)
    )

    @Test
    fun `round-trip orElse fallback path`() {
        val schema = Schema.eventWithOrElse()
        val json = """{"type":"Click","x":"10","y":"20"}"""
        assertEquals(Validation.valid(Event.Click(10, 20)), schema.decodeFromJsonString(json))
    }

    @Test
    fun `round-trip dynamic`() {
        val schema = Schema.dynamic()
        val value = SchemaValue.Obj(
            mapOf(
                "name" to SchemaValue.Str("test"),
                "count" to SchemaValue.Integer(42),
                "active" to SchemaValue.Bool(true)
            )
        )
        assertRoundTrip(schema, value)
    }

    // Cross-decoder equivalence

    @Test
    fun `matches kotlinx - record missing field`() =
        assertMatchesKotlinx(Schema.point(), "{}")

    @Test
    fun `matches kotlinx - record wrong type`() =
        assertMatchesKotlinx(Schema.point(), """{"x":"not-an-int","y":2}""")

    @Test
    fun `matches kotlinx - empty schema null`() =
        assertMatchesKotlinx(Schema.empty(), "null")

    @Test
    fun `matches kotlinx - optional null`() =
        assertMatchesKotlinx(Schema.int().optional(), "null")

    @Test
    fun `matches kotlinx - default from null`() =
        assertMatchesKotlinx(Schema.int().default(42), "null")

    @Test
    fun `matches kotlinx - union unknown type`() =
        assertMatchesKotlinx(Schema.shape(), """{"type":"Triangle","base":3}""")

    // Error accumulation

    @Test
    fun `record with multiple bad fields accumulates errors`() {
        val result = Schema.point().decodeFromJsonString("""{"x":"a","y":"b"}""")
        val errors = (result as Validation.Invalid).errors
        assertEquals(2, errors.size)
    }

    @Test
    fun `list with multiple bad items accumulates errors`() {
        val result = Schema.list(Schema.int()).decodeFromJsonString("""["a",1,"b"]""")
        val errors = (result as Validation.Invalid).errors
        assertEquals(2, errors.size)
    }
}
