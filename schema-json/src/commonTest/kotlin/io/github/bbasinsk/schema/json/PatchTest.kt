package io.github.bbasinsk.schema.json

import io.github.bbasinsk.schema.Patch
import io.github.bbasinsk.schema.PatchFailure
import io.github.bbasinsk.schema.PatchResult
import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.applyPatch
import io.github.bbasinsk.schema.diff
import io.github.bbasinsk.schema.patchSchema
import io.github.bbasinsk.validation.Validation
import io.github.bbasinsk.validation.getOrNull
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PatchTest {
    data class Item(val id: String, val name: String, val count: Int)
    data class Note(val text: String, val author: String?)
    sealed interface Shape {
        data class Circle(val radius: Int) : Shape
        data class Square(val side: Int, val label: String?) : Shape
    }

    data class Doc(
        val title: String,
        val scale: Int?,
        val note: Note,
        val items: List<Item>,
        val shape: Shape,
        val tags: List<String>,
        val extra: Note?,
    )

    private val itemSchema: Schema<Item> = Schema.record(
        Schema.field(Schema.string(), "id") { id },
        Schema.field(Schema.string(), "name") { name },
        Schema.field(Schema.int(), "count") { count },
        ::Item,
    )
    private val noteSchema: Schema<Note> = Schema.record(
        Schema.field(Schema.string(), "text") { text },
        Schema.field(Schema.string().optional(), "author") { author },
        ::Note,
    )
    private val shapeSchema: Schema<Shape> = Schema.union(
        Schema.case<Shape, Shape.Circle>(Schema.record(Schema.field(Schema.int(), "radius") { radius }, Shape::Circle), "Circle"),
        Schema.case<Shape, Shape.Square>(
            Schema.record(
                Schema.field(Schema.int(), "side") { side },
                Schema.field(Schema.string().optional(), "label") { label },
                Shape::Square,
            ),
            "Square",
        ),
    )
    private val docSchema: Schema<Doc> = Schema.record(
        Schema.field(Schema.string(), "title") { title },
        Schema.field(Schema.int().optional(), "scale") { scale },
        Schema.field(noteSchema, "note") { note },
        Schema.field(Schema.keyedList(itemSchema) { it.id }, "items") { items },
        Schema.field(shapeSchema, "shape") { shape },
        Schema.field(Schema.list(Schema.string()), "tags") { tags },
        Schema.field(noteSchema.optional(), "extra") { extra },
        ::Doc,
    )
    private val docPatchSchema = docSchema.patchSchema()

    private fun Random.note() = Note(listOf("a", "b").random(this), listOf(null, "x", "y").random(this))
    private fun Random.item(id: String) = Item(id, listOf("alpha", "beta").random(this), nextInt(2))
    private fun Random.shape(): Shape =
        if (nextBoolean()) Shape.Circle(nextInt(2)) else Shape.Square(nextInt(2), listOf(null, "l").random(this))

    private fun Random.doc() = Doc(
        title = listOf("t", "u").random(this),
        scale = listOf(null, 1, 2).random(this),
        note = note(),
        items = (1..5).map { "i$it" }.shuffled(this).take(nextInt(5)).map { item(it) },
        shape = shape(),
        tags = List(nextInt(3)) { listOf("x", "y").random(this) },
        extra = if (nextBoolean()) null else note(),
    )

    // Mostly small edits of [doc], so diffs exercise field, item and case patches rather than whole sets.
    private fun Random.edit(doc: Doc): Doc {
        if (nextInt(5) == 0) return doc()
        fun <T> maybe(current: T, next: () -> T): T = if (nextInt(3) == 0) next() else current
        val kept = doc.items.filter { nextInt(4) != 0 }.map { maybe(it) { it.copy(count = it.count + 1) } }
        val added = (6..7).map { "i$it" }.filter { nextBoolean() }.map { item(it) }
        return doc.copy(
            title = maybe(doc.title) { listOf("t", "u").random(this) },
            scale = maybe(doc.scale) { listOf(null, 1, 2).random(this) },
            note = maybe(doc.note) { note() },
            items = maybe(doc.items) { kept + added },
            shape = maybe(doc.shape) {
                when (val shape = doc.shape) {
                    is Shape.Circle -> shape.copy(radius = shape.radius + 1)
                    is Shape.Square -> if (nextBoolean()) shape.copy(label = null) else shape()
                }
            },
            tags = maybe(doc.tags) { doc.tags.reversed() + "z" },
            extra = maybe(doc.extra) { if (nextBoolean()) null else doc.extra?.copy(author = null) ?: note() },
        )
    }

    private fun kinds(patch: Patch<*>): List<String> = listOf(patch::class.simpleName!!) + when (patch) {
        is Patch.Fields<*> -> patch.fields.values.flatMap { kinds(it) }
        is Patch.Case<*> -> kinds(patch.patch)
        is Patch.Set<*> -> listOfNotNull("SetNull".takeIf { patch.value == null })
        else -> emptyList()
    }

    @Test
    fun `diff and apply obey the patch laws and round-trip through JSON`() {
        val random = Random(1272)
        val seen = mutableSetOf<String>()
        repeat(500) {
            val before = random.doc()
            val after = random.edit(before)
            val patch = docSchema.diff(before, after)
            seen += kinds(patch)

            assertEquals(PatchResult.Applied(after), docSchema.applyPatch(patch, before), "patch $patch")
            assertEquals(Patch.Keep, docSchema.diff(before, before))
            assertEquals(PatchResult.Applied(before), docSchema.applyPatch(Patch.Keep, before))
            assertEquals(Validation.Valid(patch), docPatchSchema.decodeFromJsonString(docPatchSchema.encodeToJsonString(patch)))
        }
        assertTrue(seen.containsAll(listOf("Fields", "Items", "Case", "Set", "SetNull")), "generated $seen")
    }

    @Test
    fun `keyed list upserts in place, appends new keys in patch order and removes by key`() {
        val before = Random(1).doc().copy(items = listOf(Item("a", "alpha", 0), Item("b", "beta", 0), Item("c", "gamma", 0)))
        val patch = Patch.Fields<Doc>(
            mapOf("items" to Patch.Items(upserts = listOf(Item("d", "delta", 0), Item("b", "beta", 1), Item("e", "epsilon", 0)), removals = listOf("a")))
        )

        assertEquals(
            PatchResult.Applied(before.copy(items = listOf(Item("b", "beta", 1), Item("c", "gamma", 0), Item("d", "delta", 0), Item("e", "epsilon", 0)))),
            docSchema.applyPatch(patch, before),
        )
        assertEquals(Validation.Valid(patch), docPatchSchema.decodeFromJsonString(docPatchSchema.encodeToJsonString(patch)))
    }

    @Test
    fun `apply returns typed failures naming the path`() {
        val before = Random(2).doc().copy(items = listOf(Item("a", "alpha", 0)), shape = Shape.Circle(1), extra = null)
        fun items(upserts: List<Item>, removals: List<String>) = Patch.Fields<Doc>(mapOf("items" to Patch.Items(upserts, removals)))

        assertEquals(PatchResult.Failed(listOf("items"), PatchFailure.MissingKey("z")), docSchema.applyPatch(items(emptyList(), listOf("z")), before))
        assertEquals(
            PatchResult.Failed(listOf("items"), PatchFailure.DuplicateKey("a")),
            docSchema.applyPatch(items(listOf(Item("a", "beta", 0)), listOf("a")), before),
        )
        assertEquals(
            PatchResult.Failed(listOf("items"), PatchFailure.DuplicateKey("b")),
            docSchema.applyPatch(items(listOf(Item("b", "beta", 0), Item("b", "gamma", 0)), emptyList()), before),
        )
        assertEquals(
            PatchResult.Failed(listOf("shape"), PatchFailure.WrongCase(patched = "Square", actual = "Circle")),
            docSchema.applyPatch(Patch.Fields(mapOf("shape" to Patch.Case<Shape>("Square", Patch.Fields<Shape.Square>(mapOf("side" to Patch.Set(2)))))), before),
        )
        val editNull = docSchema.applyPatch(Patch.Fields(mapOf("extra" to Patch.Fields<Note>(mapOf("text" to Patch.Set("t"))))), before)
        assertTrue(editNull is PatchResult.Failed && editNull.path == listOf("extra") && editNull.reason is PatchFailure.Invalid, "$editNull")
        val noKey = Schema.keyedList(itemSchema) { error("no key") }.applyPatch(Patch.Items(listOf(Item("a", "alpha", 0)), emptyList()), emptyList())
        assertEquals(PatchResult.Failed(emptyList(), PatchFailure.Invalid("no key")), noKey)
    }

    data class Versioned(val x: Int, val revision: Int)

    @Test
    fun `patch laws hold for a record holding state its schema does not`() {
        val schema = Schema.record(Schema.field(Schema.int(), "x") { x }) { Versioned(it, 0) }
        val before = Versioned(1, 7)

        assertEquals(PatchResult.Applied(Versioned(2, 7)), schema.applyPatch(schema.diff(before, Versioned(2, 7)), before))
        assertEquals(PatchResult.Applied(Versioned(1, 8)), schema.applyPatch(schema.diff(before, Versioned(1, 8)), before))
        assertEquals(PatchResult.Applied(before), schema.applyPatch(Patch.Fields(mapOf("x" to Patch.Keep)), before))
    }

    @Test
    fun `keyed list encodes and decodes exactly as a plain list`() {
        val items = listOf(Item("a", "alpha", 1), Item("b", "beta", 2))
        val keyed = Schema.keyedList(itemSchema) { it.id }
        val plain = Schema.list(itemSchema)
        val json = plain.encodeToJsonString(items)

        assertEquals(json, keyed.encodeToJsonString(items))
        assertEquals(plain.decodeFromJsonString(json), keyed.decodeFromJsonString(json))
    }

    data class Node(val name: String, val children: List<Node>, val next: Node?)

    @Test
    fun `recursive schema derives patches lazily and round-trips deep patches`() {
        lateinit var nodeSchema: Schema<Node>
        nodeSchema = Schema.record(
            Schema.field(Schema.string(), "name") { name },
            Schema.field(Schema.keyedList(Schema.lazy { nodeSchema }) { it.name }, "children") { children },
            Schema.field(Schema.lazy { nodeSchema }.optional(), "next") { next },
            ::Node,
        )
        val patchSchema = nodeSchema.patchSchema()
        val before = Node("root", listOf(Node("a", emptyList(), Node("a2", emptyList(), null))), null)
        val after = Node("root", listOf(Node("a", emptyList(), Node("a3", emptyList(), null)), Node("b", emptyList(), null)), null)
        val patch = nodeSchema.diff(before, after)

        assertEquals(Validation.Valid(patch), patchSchema.decodeFromJsonString(patchSchema.encodeToJsonString(patch)))
        assertEquals(PatchResult.Applied(after), nodeSchema.applyPatch(patch, before))
    }

    data class Member(val id: String, val name: String)
    data class Entry(val text: String)

    // A title, a nullable scale, two keyed lists, and an ordered list the caller patches its own way.
    // `priority` stands in for a field added after the patch was first used.
    data class Report(
        val title: String,
        val scale: Double?,
        val items: List<Item>,
        val members: List<Member>,
        val entries: List<Entry>,
        val priority: Int?,
    )

    private val reportSchema: Schema<Report> = Schema.record(
        Schema.field(Schema.string(), "title") { title },
        Schema.field(Schema.double().optional(), "scale") { scale },
        Schema.field(Schema.keyedList(itemSchema) { it.id }, "items") { items },
        Schema.field(
            Schema.keyedList(Schema.record(Schema.field(Schema.string(), "id") { id }, Schema.field(Schema.string(), "name") { name }, ::Member)) { it.id },
            "members",
        ) { members },
        Schema.field(Schema.list(Schema.record(Schema.field(Schema.string(), "text") { text }, ::Entry)), "entries") { entries },
        Schema.field(Schema.int().optional(), "priority") { priority },
        ::Report,
    )

    private val report = Report(
        title = "Quartely report",
        scale = 2.0,
        items = listOf(Item("i1", "Alpha", 1)),
        members = listOf(Member("m1", "Ann"), Member("m2", "Bo")),
        entries = listOf(Entry("first"), Entry("second")),
        priority = null,
    )

    private val reportPatchSchema = reportSchema.patchSchema(omit = setOf("entries"))

    // Strict structured outputs send every field of the patch; unnamed ones keep.
    private fun reportPatch(vararg fields: Pair<String, String>): Patch<Report> {
        val all = listOf("title", "scale", "items", "members", "priority").associateWith { """{"op":"keep"}""" } + fields
        val json = all.entries.joinToString(",", """{"op":"edit","fields":{""", "}}") { (name, op) -> "\"$name\":$op" }
        return reportPatchSchema.decodeFromJsonString(json).getOrNull()!!
    }

    @Test
    fun `one model-written patch fixes the title, clears the scale, upserts an item and removes a member`() {
        val patch = reportPatch(
            "title" to """{"op":"set","value":"Quarterly report"}""",
            "scale" to """{"op":"set","value":null}""",
            "items" to """{"op":"edit","upserts":[{"id":"i2","name":"Beta","count":1}],"removals":[]}""",
            "members" to """{"op":"edit","upserts":[],"removals":["m2"]}""",
            "entries" to """{"op":"set","value":[]}""",
        )

        assertEquals(
            PatchResult.Applied(
                report.copy(
                    title = "Quarterly report",
                    scale = null,
                    items = listOf(Item("i1", "Alpha", 1), Item("i2", "Beta", 1)),
                    members = listOf(Member("m1", "Ann")),
                )
            ),
            reportSchema.applyPatch(patch, report),
        )
    }

    @Test
    fun `keep leaves a nullable field and set null clears it`() {
        assertEquals(PatchResult.Applied(report), reportSchema.applyPatch(reportPatch("scale" to """{"op":"keep"}"""), report))
        assertEquals(
            PatchResult.Applied(report.copy(scale = null)),
            reportSchema.applyPatch(reportPatch("scale" to """{"op":"set","value":null}"""), report),
        )
    }

    @Test
    fun `a field added to the record is patchable without patch code`() {
        val patch = reportPatch("priority" to """{"op":"set","value":4}""")

        assertEquals(PatchResult.Applied(report.copy(priority = 4)), reportSchema.applyPatch(patch, report))
    }
}
