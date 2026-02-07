package io.github.bbasinsk.schema.json.kotlinx

import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.Schema.Companion.case
import io.github.bbasinsk.schema.orElse
import io.github.bbasinsk.schema.transform
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class SinkEncodingTest {

    private fun <A> assertEncodingsMatch(schema: Schema<A>, value: A, json: Json = Json.Default) {
        val fromElement = schema.encodeToJsonElement(value, json).toString()
        val fromSink = schema.encodeToJsonString(value, json)
        assertEquals(fromElement, fromSink)
    }

    // Primitives

    @Test
    fun `int`() = assertEncodingsMatch(Schema.int(), 42)

    @Test
    fun `negative int`() = assertEncodingsMatch(Schema.int(), -7)

    @Test
    fun `string`() = assertEncodingsMatch(Schema.string(), "hello")

    @Test
    fun `empty string`() = assertEncodingsMatch(Schema.string(), "")

    @Test
    fun `boolean true`() = assertEncodingsMatch(Schema.boolean(), true)

    @Test
    fun `boolean false`() = assertEncodingsMatch(Schema.boolean(), false)

    @Test
    fun `double`() = assertEncodingsMatch(Schema.double(), 3.14)

    @Test
    fun `float`() = assertEncodingsMatch(Schema.float(), 2.5f)

    @Test
    fun `long`() = assertEncodingsMatch(Schema.long(), 30000000000L)

    enum class Color { RED, GREEN, BLUE }

    @Test
    fun `enum`() = assertEncodingsMatch(Schema.enumeration<Color>(), Color.GREEN)

    // String escaping

    @Test
    fun `string with quotes`() = assertEncodingsMatch(Schema.string(), """say "hi"""")

    @Test
    fun `string with backslash`() = assertEncodingsMatch(Schema.string(), "back\\slash")

    @Test
    fun `string with newline and tab`() = assertEncodingsMatch(Schema.string(), "line1\nline2\tcol")

    @Test
    fun `string with control chars`() = assertEncodingsMatch(Schema.string(), "ctrl\u0000\u001F")

    // Optional

    @Test
    fun `optional null`() = assertEncodingsMatch(Schema.int().optional(), null)

    @Test
    fun `optional present`() = assertEncodingsMatch(Schema.int().optional(), 42)

    // Default

    @Test
    fun `default value`() = assertEncodingsMatch(Schema.int().default(0), 99)

    // ByteArray

    @Test
    fun `byteArray`() = assertEncodingsMatch(Schema.byteArray(), "hello bytes".encodeToByteArray())

    // List

    @Test
    fun `empty list`() = assertEncodingsMatch(Schema.list(Schema.int()), emptyList())

    @Test
    fun `non-empty list`() = assertEncodingsMatch(Schema.list(Schema.int()), listOf(1, 2, 3))

    @Test
    fun `nested list`() = assertEncodingsMatch(
        Schema.list(Schema.list(Schema.string())),
        listOf(listOf("a", "b"), listOf("c"))
    )

    // StringMap

    @Test
    fun `empty stringMap`() = assertEncodingsMatch(Schema.stringMap(Schema.int()), emptyMap())

    @Test
    fun `non-empty stringMap`() = assertEncodingsMatch(
        Schema.stringMap(Schema.int()),
        mapOf("a" to 1, "b" to 2)
    )

    // Record

    data class Point(val x: Int, val y: Int)

    private fun Schema.Companion.point(): Schema<Point> = record(
        field(int(), "x") { x },
        field(int(), "y") { y },
        ::Point
    )

    @Test
    fun `simple record`() = assertEncodingsMatch(Schema.point(), Point(1, 2))

    data class Line(val start: Point, val end: Point)

    private fun Schema.Companion.line(): Schema<Line> = record(
        field(point(), "start") { start },
        field(point(), "end") { end },
        ::Line
    )

    @Test
    fun `nested record`() = assertEncodingsMatch(Schema.line(), Line(Point(0, 0), Point(3, 4)))

    data class Named(val name: String, val age: Int?)

    private fun Schema.Companion.named(): Schema<Named> = record(
        field(string(), "name") { name },
        field(int().optional(), "age") { age },
        ::Named
    )

    @Test
    fun `record with null optional field - explicitNulls false`() {
        val json = Json { explicitNulls = false }
        assertEncodingsMatch(Schema.named(), Named("Alice", null), json)
    }

    @Test
    fun `record with null optional field - explicitNulls true`() =
        assertEncodingsMatch(Schema.named(), Named("Alice", null))

    @Test
    fun `record with present optional field`() =
        assertEncodingsMatch(Schema.named(), Named("Bob", 30))

    // Union

    sealed interface Shape {
        data class Circle(val radius: Int) : Shape
        data class Rect(val w: Int, val h: Int) : Shape
    }

    private fun Schema.Companion.circle(): Schema<Shape.Circle> = record(
        field(int(), "radius") { radius },
        Shape::Circle
    )

    private fun Schema.Companion.rect(): Schema<Shape.Rect> = record(
        field(int(), "w") { w },
        field(int(), "h") { h },
        Shape::Rect
    )

    private fun Schema.Companion.shape(): Schema<Shape> = union(
        case(circle()),
        case(rect())
    )

    @Test
    fun `union first case`() = assertEncodingsMatch(Schema.shape(), Shape.Circle(5))

    @Test
    fun `union second case`() = assertEncodingsMatch(Schema.shape(), Shape.Rect(3, 4))

    // Transform

    data class UserId(val value: Int)

    private fun Schema.Companion.userId(): Schema<UserId> =
        int().transform(::UserId) { it.value }

    @Test
    fun `transform`() = assertEncodingsMatch(Schema.userId(), UserId(42))

    // Lazy / recursive

    sealed interface Tree {
        data class Leaf(val value: Int) : Tree
        data class Branch(val left: Tree, val right: Tree) : Tree
    }

    private fun Schema.Companion.leaf(): Schema<Tree.Leaf> = record(
        field(int(), "value") { value },
        Tree::Leaf
    )

    private fun Schema.Companion.branch(): Schema<Tree.Branch> = record(
        field(lazy { tree() }, "left") { left },
        field(lazy { tree() }, "right") { right },
        Tree::Branch
    )

    private fun Schema.Companion.tree(): Schema<Tree> = union(
        case(leaf()),
        case(branch())
    )

    @Test
    fun `lazy recursive`() = assertEncodingsMatch(
        Schema.tree(),
        Tree.Branch(Tree.Leaf(1), Tree.Branch(Tree.Leaf(2), Tree.Leaf(3)))
    )

    // Union with OrElse

    sealed interface Event {
        data class Click(val x: Int, val y: Int) : Event
        data class Key(val code: Int) : Event
    }

    private data class ClickStrings(val x: String, val y: String)

    private fun Schema.Companion.click(): Schema<Event.Click> =
        record(
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

    private fun Schema.Companion.key(): Schema<Event.Key> =
        record(
            field(int(), "code") { code },
            Event::Key
        )

    private fun Schema.Companion.eventWithOrElse(): Schema<Event> = union(
        case(clickOrElse()),
        case(key())
    )

    @Test
    fun `union with orElse case`() =
        assertEncodingsMatch(Schema.eventWithOrElse(), Event.Click(10, 20))

    @Test
    fun `union with orElse case - second case`() =
        assertEncodingsMatch(Schema.eventWithOrElse(), Event.Key(42))

    // Special floating point values

    @Test
    fun `double NaN with allowSpecialFloatingPointValues`() {
        val json = Json { allowSpecialFloatingPointValues = true }
        assertEncodingsMatch(Schema.double(), Double.NaN, json)
    }

    @Test
    fun `double Infinity with allowSpecialFloatingPointValues`() {
        val json = Json { allowSpecialFloatingPointValues = true }
        assertEncodingsMatch(Schema.double(), Double.POSITIVE_INFINITY, json)
    }

    @Test
    fun `double negative Infinity with allowSpecialFloatingPointValues`() {
        val json = Json { allowSpecialFloatingPointValues = true }
        assertEncodingsMatch(Schema.double(), Double.NEGATIVE_INFINITY, json)
    }

    @Test
    fun `float NaN with allowSpecialFloatingPointValues`() {
        val json = Json { allowSpecialFloatingPointValues = true }
        assertEncodingsMatch(Schema.float(), Float.NaN, json)
    }

    @Test
    fun `float Infinity with allowSpecialFloatingPointValues`() {
        val json = Json { allowSpecialFloatingPointValues = true }
        assertEncodingsMatch(Schema.float(), Float.POSITIVE_INFINITY, json)
    }

    // encodeToJsonBytes round-trip

    @Test
    fun `encodeToJsonBytes matches encodeToJsonString`() {
        val schema = Schema.point()
        val value = Point(10, 20)
        assertEquals(
            schema.encodeToJsonString(value),
            schema.encodeToJsonBytes(value).decodeToString()
        )
    }
}
