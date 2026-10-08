package io.github.bbasinsk.schema.jsonschema

import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.default
import io.github.bbasinsk.schema.json.decodeFromJsonString
import io.github.bbasinsk.validation.Validation
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonPrimitive
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class OmitDefaultsJsonSchemaTest {
    // Every object with properties, as (property names, required names).
    private fun records(element: JsonElement): List<kotlin.Pair<Set<String>, Set<String>>> = when (element) {
        is JsonObject -> {
            val properties = element["properties"] as JsonObject?
            listOfNotNull(properties?.let { it.keys to element.getValue("required").jsonArray.map { r -> r.jsonPrimitive.content }.toSet() }) +
                element.values.flatMap { records(it) }
        }
        is JsonArray -> element.flatMap { records(it) }
        else -> emptyList()
    }

    private val order = setOf("id", "items", "rush", "coupon", "main")
    private val item = setOf("name", "tags", "note")
    private val leaf = setOf("kind", "label")
    private val node = setOf("kind", "children", "open")

    @Test
    fun `default rendering keeps defaulted and optional fields required`() {
        assertEquals(
            setOf(order to order, item to item),
            records(orderSchema.toJsonSchema().encodeToJsonElement()).toSet()
        )
        assertEquals(
            setOf(leaf to leaf, node to node),
            records(treeSchema.toJsonSchema(maxRecursionDepth = 2).encodeToJsonElement()).toSet()
        )
    }

    @Test
    fun `omitDefaults leaves defaulted and optional fields out of nested records and defs`() {
        // Order, plus the one Item definition that `items` and `main` both reference.
        assertEquals(
            listOf(item to setOf("name"), order to setOf("id", "items", "main")).sortedBy { it.toString() },
            records(orderSchema.toJsonSchema(omitDefaults = true).encodeToJsonElement()).sortedBy { it.toString() }
        )
    }

    @Test
    fun `omitDefaults applies to union cases and recursive defs and keeps the discriminator`() {
        val expected = setOf(leaf to setOf("kind"), node to setOf("kind"))
        val plain = records(treeSchema.toJsonSchema(omitDefaults = true).encodeToJsonElement())
        assertEquals(2, plain.size)
        assertEquals(expected, plain.toSet())
        // Leaf at depths 0..2 and Node at depths 1..2.
        val unrolled = records(treeSchema.toJsonSchema(maxRecursionDepth = 2, omitDefaults = true).encodeToJsonElement())
        assertEquals(5, unrolled.size)
        assertEquals(expected, unrolled.toSet())
    }

    @Test
    fun `omitted fields decode to their defaults and a missing required field still fails`() {
        val item = """{"name":"pan"}"""
        assertEquals(
            Validation.valid(Order("o1", listOf(Item("pan", emptyList(), null)), rush = false, coupon = null, main = Item("pan", emptyList(), null))),
            orderSchema.decodeFromJsonString("""{"id":"o1","items":[$item],"main":$item}""")
        )
        assertTrue(orderSchema.decodeFromJsonString("""{"items":[$item],"main":$item}""") is Validation.Invalid)
    }
}

private data class Item(val name: String, val tags: List<String>, val note: String?)

private data class Order(val id: String, val items: List<Item>, val rush: Boolean, val coupon: String?, val main: Item)

private val itemSchema: Schema<Item> = Schema.record(
    Schema.field(Schema.string(), "name") { name },
    Schema.field(Schema.list(Schema.string()).default(emptyList()).description("Tags"), "tags") { tags },
    Schema.field(Schema.string().optional(), "note") { note },
    ::Item
)

private val orderSchema: Schema<Order> = Schema.record(
    Schema.field(Schema.string(), "id") { id },
    Schema.field(Schema.list(itemSchema), "items") { items },
    Schema.field(Schema.boolean().default(false), "rush") { rush },
    Schema.field(Schema.string().optional().description("Coupon"), "coupon") { coupon },
    Schema.field(itemSchema, "main") { main },
    ::Order
)

private sealed interface Tree {
    data class Leaf(val label: String?) : Tree
    data class Node(val children: List<Tree>, val open: Boolean) : Tree
}

private val treeSchema: Schema<Tree> by lazy {
    Schema.union(
        Schema.case(Schema.record(Schema.field(Schema.string().optional(), "label") { label }, Tree::Leaf), "Leaf"),
        Schema.case(
            Schema.record(
                Schema.field(Schema.list(Schema.lazy { treeSchema }).default(emptyList()), "children") { children },
                Schema.field(Schema.boolean().default(false), "open") { open },
                Tree::Node
            ),
            "Node"
        ),
        key = "kind"
    )
}
