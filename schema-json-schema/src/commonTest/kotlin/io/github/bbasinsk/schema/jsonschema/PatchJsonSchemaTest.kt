package io.github.bbasinsk.schema.jsonschema

import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.patchSchema
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PatchJsonSchemaTest {
    data class Item(val id: String, val name: String)
    data class Entry(val text: String)
    data class Report(val title: String, val scale: Double?, val items: List<Item>, val entries: List<Entry>)
    data class Node(val name: String, val children: List<Node>, val next: Node?)

    private val itemSchema: Schema<Item> = Schema.record(
        Schema.field(Schema.string(), "id") { id },
        Schema.field(Schema.string(), "name") { name },
        ::Item,
    )
    private val reportSchema: Schema<Report> = Schema.record(
        Schema.field(Schema.string().description("The report's title"), "title") { title },
        Schema.field(Schema.double().description("Scale factor").optional(), "scale") { scale },
        Schema.field(Schema.keyedList(itemSchema) { it.id }, "items") { items },
        Schema.field(Schema.list(Schema.record(Schema.field(Schema.string(), "text") { text }, ::Entry)), "entries") { entries },
        ::Report,
    )

    // OpenAI strict mode: every object lists all its properties as required and allows no others.
    private fun assertStrict(element: JsonElement) {
        when (element) {
            is JsonObject -> {
                element["properties"]?.let { properties ->
                    assertEquals(properties.jsonObject.keys, element.getValue("required").jsonArray.map { it.jsonPrimitive.content }.toSet(), "$element")
                    assertEquals(JsonPrimitive(false), element["additionalProperties"], "$element")
                }
                element.values.forEach(::assertStrict)
            }
            is JsonArray -> element.forEach(::assertStrict)
            is JsonPrimitive -> Unit
        }
    }

    @Test
    fun `keyed list renders exactly as a plain list`() {
        assertEquals(Schema.list(itemSchema).toJsonSchema(), Schema.keyedList(itemSchema) { it.id }.toJsonSchema())
    }

    @Test
    fun `record patch with omitted fields renders keep and per-field edits in strict mode`() {
        val rendered = reportSchema.patchSchema(omit = setOf("entries")).toJsonSchema().encodeToJsonElement()
        assertStrict(rendered)

        val fields = rendered.jsonObject.getValue("\$defs").jsonObject.entries
            .single { it.key.contains("Patch.Fields") }.value.jsonObject.getValue("properties").jsonObject
        assertEquals(setOf("title", "scale", "items"), fields.keys)
        assertEquals(JsonPrimitive("The report's title"), fields.getValue("title").jsonObject["description"])
        assertEquals(JsonPrimitive("Scale factor"), fields.getValue("scale").jsonObject["description"])
        // No whole-record set: it would replace the omitted fields.
        assertEquals(
            listOf("keep", "edit"),
            rendered.jsonObject.getValue("anyOf").jsonArray.map {
                it.jsonObject.getValue("properties").jsonObject.getValue("op").jsonObject.getValue("enum").jsonArray.single().jsonPrimitive.content
            },
        )
    }

    @Test
    fun `primitive patch is keep or set`() {
        assertEquals(
            Json.parseToJsonElement(
                """
                {"anyOf":[
                  {"type":"object","properties":{"op":{"enum":["keep"]}},"additionalProperties":false,"required":["op"]},
                  {"type":"object","properties":{"op":{"enum":["set"]},"value":{"type":["string","null"]}},"additionalProperties":false,"required":["op","value"]}
                ]}
                """
            ),
            Schema.string().optional().patchSchema().toJsonSchema().encodeToJsonElement(),
        )
    }

    @Test
    fun `recursive schema renders its patch in strict mode`() {
        lateinit var nodeSchema: Schema<Node>
        nodeSchema = Schema.record(
            Schema.field(Schema.string(), "name") { name },
            Schema.field(Schema.keyedList(Schema.lazy { nodeSchema }) { it.name }, "children") { children },
            Schema.field(Schema.lazy { nodeSchema }.optional(), "next") { next },
            ::Node,
        )
        val rendered = nodeSchema.patchSchema().toJsonSchema().encodeToJsonElement()
        assertStrict(rendered)
        assertTrue(rendered.jsonObject.getValue("\$defs").jsonObject.keys.any { it.contains("Patch.Fields") }, "$rendered")

        assertStrict(nodeSchema.patchSchema().toJsonSchema(maxRecursionDepth = 2).encodeToJsonElement())
    }
}
