package io.github.bbasinsk.schema.json

import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.json.kotlinx.encodeToJsonElement
import io.github.bbasinsk.validation.Validation
import kotlin.test.Test
import kotlin.test.assertEquals

class OmitDefaultsTest {
    data class Entry(val tags: List<String>, val done: Boolean, val note: String?, val items: List<String>)

    sealed interface Shape {
        data class Circle(val radius: Int, val filled: Boolean) : Shape
        data class Square(val side: Int) : Shape
    }

    data class Doc(val title: String, val flag: Boolean, val entries: List<Entry>, val byName: Map<String, Entry>, val shape: Shape)

    private val entrySchema: Schema<Entry> = Schema.record(
        Schema.field(Schema.list(Schema.string()).default(emptyList()), "tags") { tags },
        Schema.field(Schema.boolean().default(false).description("finished"), "done") { done },
        Schema.field(Schema.string().optional(), "note") { note },
        Schema.field(Schema.list(Schema.string()), "items") { items },
        ::Entry,
    )

    private val shapeSchema: Schema<Shape> = Schema.union(
        Schema.case<Shape, Shape.Circle>(
            Schema.record(
                Schema.field(Schema.int(), "radius") { radius },
                Schema.field(Schema.boolean().default(false), "filled") { filled },
                Shape::Circle,
            ),
            "Circle",
        ),
        Schema.case<Shape, Shape.Square>(Schema.record(Schema.field(Schema.int(), "side") { side }, Shape::Square), "Square"),
    )

    private val docSchema: Schema<Doc> = Schema.record(
        Schema.field(Schema.string(), "title") { title },
        Schema.field(Schema.boolean(), "flag") { flag },
        Schema.field(Schema.list(entrySchema), "entries") { entries },
        Schema.field(Schema.stringMap(entrySchema), "byName") { byName },
        Schema.field(shapeSchema, "shape") { shape },
        ::Doc,
    )

    private val bare = Entry(emptyList(), false, null, emptyList())
    private val doc = Doc(
        title = "t",
        flag = false,
        entries = listOf(bare, Entry(listOf("a"), true, "n", listOf("x"))),
        byName = mapOf("k" to bare),
        shape = Shape.Circle(1, false),
    )
    private val sparse = JsonEncodingConfig(explicitNulls = false, omitDefaults = true)

    @Test
    fun `omits fields at their default at every depth and keeps required and discriminator fields`() {
        val expected = """{"title":"t","flag":false,"entries":[{"items":[]},{"tags":["a"],"done":true,"note":"n","items":["x"]}],""" +
            """"byName":{"k":{"items":[]}},"shape":{"type":"Circle","radius":1}}"""
        assertEquals(expected, docSchema.encodeToJsonString(doc, sparse))
        assertEquals(expected, docSchema.encodeToJsonValue(doc, sparse).encodeToJsonString())
        assertEquals(expected, docSchema.encodeToJsonElement(doc, sparse).toString())
    }

    @Test
    fun `sparse encoding decodes back to the original value`() {
        assertEquals(Validation.valid(doc), docSchema.decodeFromJsonString(docSchema.encodeToJsonString(doc, sparse)))
    }
}
