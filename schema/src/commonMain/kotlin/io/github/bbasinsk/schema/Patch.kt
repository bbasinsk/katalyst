package io.github.bbasinsk.schema

/**
 * A change to a value of [A], shaped by its [Schema]. A patch changes only what it names.
 *
 * Derive the wire schema with [patchSchema], apply a patch with [applyPatch], and compute one with [diff].
 */
sealed interface Patch<out A> {
    /** Leaves the value unchanged. Distinct from `Set(null)`. */
    data object Keep : Patch<Nothing>

    /** Replaces the whole value. */
    data class Set<out A>(val value: A) : Patch<A>

    /**
     * Patches a record field by field. A field without an entry is kept.
     * [fields] is untyped: each entry must patch its field's type. Patches decoded with [patchSchema] always do.
     */
    data class Fields<out A>(val fields: Map<String, Patch<*>>) : Patch<A>

    /**
     * Patches a keyed list. An upsert replaces the item with its key in place; new keys append in patch order.
     * A removal deletes the item with its key. A key may appear once per patch.
     */
    data class Items<out A>(val upserts: List<A>, val removals: List<String>) : Patch<List<A>>

    /** Patches a union value within its current case [name]. */
    data class Case<out A>(val name: String, val patch: Patch<*>) : Patch<A>
}

sealed interface PatchResult<out A> {
    data class Applied<out A>(val value: A) : PatchResult<A>

    /** [path] names the record fields from the root to where the patch failed. */
    data class Failed(val path: List<String>, val reason: PatchFailure) : PatchResult<Nothing>
}

sealed interface PatchFailure {
    /** A removal names a key the list lacks. */
    data class MissingKey(val key: String) : PatchFailure

    /** A key appears twice in one keyed-list patch, or twice in the list being patched. */
    data class DuplicateKey(val key: String) : PatchFailure

    /** A patch for case [patched] met a value in case [actual]. */
    data class WrongCase(val patched: String, val actual: String) : PatchFailure

    /** The patch does not fit the schema or the value, or the patched record failed to construct. */
    data class Invalid(val message: String) : PatchFailure
}

/**
 * The schema of [Patch]es to [A]. Each patch is an object tagged by `op`:
 * - `{"op":"keep"}` leaves the value unchanged;
 * - `{"op":"set","value":…}` replaces it;
 * - `{"op":"edit",…}` changes part of it, for records (`fields`), keyed lists (`upserts`, `removals`)
 *   and unions (`case`). Every other schema patches by `set` only.
 *
 * [omit] leaves named fields of the root record out of the patch, so they stay unchanged. With [omit], the root
 * patch has no `set`, since a whole replacement would change the omitted fields.
 */
@Suppress("UNCHECKED_CAST")
fun <A> Schema<A>.patchSchema(omit: Set<String> = emptySet()): Schema<Patch<A>> =
    Derivation().derive(this, omit) as Schema<Patch<A>>

/** Applies [patch] to [value]. Returns [PatchResult.Failed] instead of throwing. */
@Suppress("UNCHECKED_CAST")
fun <A> Schema<A>.applyPatch(patch: Patch<A>, value: A): PatchResult<A> =
    try {
        PatchResult.Applied(applyAt(this, patch, value, emptyList()) as A)
    } catch (e: PatchException) {
        PatchResult.Failed(e.path, e.reason)
    }

/** A patch that turns [before] into [after]: `applyPatch(diff(a, b), a)` is `b`, and `diff(a, a)` is [Patch.Keep]. */
@Suppress("UNCHECKED_CAST")
fun <A> Schema<A>.diff(before: A, after: A): Patch<A> =
    diffAt(this, before, after) as Patch<A>

// Derivation. Record fields derive lazily, and derived nodes are memoized by source schema, so a recursive schema
// derives to a finite graph.

private const val NAMESPACE = "io.github.bbasinsk.schema"

private class PatchRecord(
    override val metadata: ObjectMetadata<Any?>,
    fields: () -> List<Field<Any?, *>>,
    private val construct: (List<Any?>) -> Any?,
) : Schema.Record<Any?> {
    override val unsafeFields: List<Field<Any?, *>> by lazy(fields)
    override fun unsafeConstruct(values: List<Any?>): Any? = construct(values)
}

private class PatchUnion(
    override val metadata: ObjectMetadata<Any?>,
    override val key: String,
    override val unsafeCases: List<Case<Any?, *>>,
) : Schema.Union<Any?>

private fun meta(kind: String, vararg arguments: String) = ObjectMetadata<Any?>(kind, NAMESPACE, arguments.toList())

@Suppress("UNCHECKED_CAST")
private fun patchField(name: String, schema: Schema<*>, extract: (Any?) -> Any?): Field<Any?, *> =
    Field(name, schema as Schema<Any?>, extract)

private fun patchCase(name: String, schema: Schema.Record<Any?>, deconstruct: (Any?) -> Any?): Case<Any?, *> =
    Case(name, schema, deconstruct)

private class Derivation {
    private val derived = mutableListOf<Triple<Schema<*>, Set<String>, Schema<*>>>()

    fun derive(schema: Schema<*>, omit: Set<String>): Schema<*> =
        derived.firstOrNull { it.first === schema && it.second == omit }?.third
            ?: build(schema, omit).also { derived += Triple(schema, omit, it) }

    @Suppress("UNCHECKED_CAST")
    private fun build(schema: Schema<*>, omit: Set<String>): Schema<*> = when (schema) {
        is Schema.Metadata -> schema.metadata.description
            ?.let { Schema.Metadata(derive(schema.schema, omit) as Schema<Any?>, FieldMetadata(description = it)) }
            ?: derive(schema.schema, omit)
        is Schema.Default -> derive(schema.schema, omit)
        is Schema.Lazy -> {
            val derived by lazy { derive(schema.schema(), omit) as Schema<Any?> }
            Schema.Lazy { derived }
        }
        else -> {
            // Names follow the source type so recursive patches share one JSON Schema definition per type.
            val name = patchName(schema) + omitSuffix(omit)
            val union = PatchUnion(
                meta("Patch", name),
                "op",
                listOfNotNull(
                    patchCase("keep", PatchRecord(meta("Patch.Keep", name), { emptyList() }) { Patch.Keep }) { it as? Patch.Keep },
                    if (omit.isNotEmpty()) null else patchCase(
                        "set",
                        PatchRecord(
                            meta("Patch.Set", name),
                            { listOf(patchField("value", schema) { (it as Patch.Set<*>).value }) },
                        ) { Patch.Set(it[0]) },
                    ) { it as? Patch.Set<*> },
                    editCase(schema, omit, name),
                ),
            )
            description(schema)?.let { Schema.Metadata(union, FieldMetadata(description = it)) } ?: union
        }
    }

    // A description beneath a nullable or transformed schema, as JSON Schema rendering finds it.
    private fun description(schema: Schema<*>): String? = when (schema) {
        is Schema.Metadata -> schema.metadata.description ?: description(schema.schema)
        is Schema.Optional<*> -> description(schema.schema)
        is Schema.Default -> description(schema.schema)
        is Schema.Lazy -> description(schema.schema())
        is Schema.Transform<*, *> -> description(schema.schema)
        else -> null
    }

    @Suppress("UNCHECKED_CAST")
    private fun editCase(schema: Schema<*>, omit: Set<String>, owner: String): Case<Any?, *>? = when (schema) {
        is Schema.Metadata -> editCase(schema.schema, omit, owner)
        is Schema.Default -> editCase(schema.schema, omit, owner)
        is Schema.Lazy -> editCase(schema.schema(), omit, owner)
        is Schema.Optional<*> -> editCase(schema.schema, omit, owner)
        is Schema.Record -> {
            val names = schema.unsafeFields.map { it.name }
            require(names.containsAll(omit)) { "Cannot omit ${omit - names.toSet()} from ${schema.metadata.name}: no such field" }
            val fields = fieldsRecord(schema, omit)
            editRecord(owner, { listOf(patchField("fields", fields) { it }) }, { it[0] }) { it as? Patch.Fields<*> }
        }
        else -> {
            require(omit.isEmpty()) { "Cannot omit $omit: only record fields can be omitted" }
            when (schema) {
                is Schema.Collection<*> -> schema.key?.let {
                    editRecord(
                        owner,
                        {
                            listOf(
                                patchField("upserts", Schema.Collection(schema.itemSchema)) { (it as Patch.Items<*>).upserts },
                                patchField("removals", Schema.Collection(Schema.string())) { (it as Patch.Items<*>).removals },
                            )
                        },
                        { Patch.Items(it[0] as List<Any?>, it[1] as List<String>) },
                    ) { it as? Patch.Items<*> }
                }
                is Schema.Union -> {
                    val cases = casesUnion(schema)
                    editRecord(owner, { listOf(patchField("case", cases) { it }) }, { it[0] }) { it as? Patch.Case<*> }
                }
                else -> null
            }
        }
    }

    private fun editRecord(
        owner: String,
        fields: () -> List<Field<Any?, *>>,
        construct: (List<Any?>) -> Any?,
        deconstruct: (Any?) -> Any?,
    ): Case<Any?, *> = patchCase("edit", PatchRecord(meta("Patch.Edit", owner), fields, construct), deconstruct)

    private fun fieldsRecord(record: Schema.Record<*>, omit: Set<String>): Schema.Record<Any?> {
        val kept = record.unsafeFields.filter { it.name !in omit }
        return PatchRecord(
            meta("Patch.Fields", patchName(record) + omitSuffix(omit)),
            {
                kept.map { field ->
                    patchField(field.name, derive(field.schema, emptySet())) { (it as Patch.Fields<*>).fields[field.name] ?: Patch.Keep }
                }
            },
        ) { values ->
            Patch.Fields<Any?>(kept.zip(values).filter { it.second != Patch.Keep }.associate { (field, patch) -> field.name to patch as Patch<*> })
        }
    }

    private fun casesUnion(union: Schema.Union<*>): Schema.Union<Any?> {
        val unionName = patchName(union)
        return PatchUnion(
            meta("Patch.Cases", unionName),
            "name",
            union.unsafeCases.map { case ->
                patchCase(
                    case.name,
                    PatchRecord(
                        meta("Patch.Case", unionName, case.name),
                        { listOf(patchField("patch", derive(case.schema, emptySet())) { (it as Patch.Case<*>).patch }) },
                    ) { Patch.Case<Any?>(case.name, it[0] as Patch<*>) },
                ) { (it as Patch.Case<*>).takeIf { patch -> patch.name == case.name } }
            },
        )
    }
}

private fun omitSuffix(omit: Set<String>): String =
    if (omit.isEmpty()) "" else ".without." + omit.sorted().joinToString(".")

private fun patchName(schema: Schema<*>): String = when (schema) {
    is Schema.Primitive.Enumeration<*> -> schema.metadata.fullName()
    is Schema.Primitive -> schema.name
    is Schema.Empty -> "Null"
    is Schema.Bytes -> "Bytes"
    is Schema.Dynamic -> "Dynamic"
    is Schema.Lazy -> patchName(schema.schema())
    is Schema.Metadata -> patchName(schema.schema)
    is Schema.Default -> patchName(schema.schema)
    is Schema.Optional<*> -> "Nullable.of." + patchName(schema.schema)
    is Schema.Collection<*> -> (if (schema.key == null) "List.of." else "KeyedList.of.") + patchName(schema.itemSchema)
    is Schema.StringMap<*> -> "Map.of." + patchName(schema.valueSchema)
    is Schema.OrElse<*, *> -> "OrElse.of." + patchName(schema.preferred) + ".or." + patchName(schema.fallback)
    is Schema.Transform<*, *> -> "Transform.of." + schema.metadata.fullName()
    is Schema.Record -> schema.metadata.fullName()
    is Schema.Union -> schema.metadata.fullName()
}

private fun ObjectMetadata<*>.fullName(): String =
    typeArguments.fold(qualifiedName()) { acc, argument -> "$acc.of.$argument" }

// Apply

private class PatchException(val path: List<String>, val reason: PatchFailure) : Exception()

private fun fail(path: List<String>, reason: PatchFailure): Nothing = throw PatchException(path, reason)

private fun applyAt(schema: Schema<*>, patch: Patch<*>, value: Any?, path: List<String>): Any? = when (patch) {
    is Patch.Keep -> value
    is Patch.Set<*> -> patch.value
    else -> editAt(schema, patch, value, path)
}

// Wraps every user callback (field extractors, keys, constructors, case deconstructors, Lazy) in a typed failure.
private fun editAt(schema: Schema<*>, patch: Patch<*>, value: Any?, path: List<String>): Any? = try {
    when {
        schema is Schema.Metadata -> editAt(schema.schema, patch, value, path)
        schema is Schema.Default -> editAt(schema.schema, patch, value, path)
        schema is Schema.Lazy -> editAt(schema.schema(), patch, value, path)
        schema is Schema.Optional<*> ->
            if (value == null) fail(path, PatchFailure.Invalid("Cannot edit a null value")) else editAt(schema.schema, patch, value, path)
        schema is Schema.Record && patch is Patch.Fields<*> -> editFields(schema, patch, value, path)
        schema is Schema.Collection<*> && schema.key != null && patch is Patch.Items<*> -> editItems(schema, patch, value, path)
        schema is Schema.Union && patch is Patch.Case<*> -> editUnion(schema, patch, value, path)
        else -> fail(path, PatchFailure.Invalid("${patch::class.simpleName} does not apply to ${patchName(schema)}"))
    }
} catch (e: PatchException) {
    throw e
} catch (e: Exception) {
    fail(path, PatchFailure.Invalid(e.message ?: e::class.simpleName ?: "Patch failed"))
}

@Suppress("UNCHECKED_CAST")
private fun editFields(schema: Schema.Record<*>, patch: Patch.Fields<*>, value: Any?, path: List<String>): Any? {
    val record = schema as Schema.Record<Any?>
    patch.fields.keys.firstOrNull { name -> record.unsafeFields.none { it.name == name } }
        ?.let { fail(path + it, PatchFailure.Invalid("Unknown field")) }
    // Construct can drop state the schema does not hold, so a patch that changes nothing returns the value itself.
    if (patch.fields.values.all { it == Patch.Keep }) return value
    val values = record.unsafeFields.map { field ->
        val current = (field as Field<Any?, Any?>).extract(value)
        val fieldPatch = patch.fields[field.name]
        if (fieldPatch == null) current else applyAt(field.schema, fieldPatch, current, path + field.name)
    }
    return record.unsafeConstruct(values)
}

@Suppress("UNCHECKED_CAST")
private fun editItems(schema: Schema.Collection<*>, patch: Patch.Items<*>, value: Any?, path: List<String>): List<Any?> {
    val keyOf = schema.key as (Any?) -> String
    val named = HashSet<String>()
    (patch.upserts.map(keyOf) + patch.removals).forEach { if (!named.add(it)) fail(path, PatchFailure.DuplicateKey(it)) }
    val items = (value as List<Any?>).map { keyOf(it) to it }
    val existing = HashSet<String>()
    items.forEach { (key, _) -> if (!existing.add(key)) fail(path, PatchFailure.DuplicateKey(key)) }
    patch.removals.firstOrNull { it !in existing }?.let { fail(path, PatchFailure.MissingKey(it)) }

    val upserts = patch.upserts.associateBy(keyOf)
    val removals = patch.removals.toSet()
    return items.filter { (key, _) -> key !in removals }.map { (key, item) -> if (key in upserts) upserts[key] else item } +
        patch.upserts.filter { keyOf(it) !in existing }
}

@Suppress("UNCHECKED_CAST")
private fun editUnion(schema: Schema.Union<*>, patch: Patch.Case<*>, value: Any?, path: List<String>): Any? {
    val cases = schema.unsafeCases as List<Case<Any?, *>>
    val target = cases.firstOrNull { it.name == patch.name } ?: fail(path, PatchFailure.Invalid("Unknown case ${patch.name}"))
    val actual = cases.firstNotNullOfOrNull { case -> case.deconstruct(value)?.let { case to it } }
        ?: fail(path, PatchFailure.Invalid("Value matches no case"))
    if (actual.first.name != target.name) fail(path, PatchFailure.WrongCase(patched = target.name, actual = actual.first.name))
    return applyAt(target.schema, patch.patch, actual.second, path)
}

// Diff

private fun diffAt(schema: Schema<*>, before: Any?, after: Any?): Patch<*> {
    if (before == after) return Patch.Keep
    return when (schema) {
        is Schema.Metadata -> diffAt(schema.schema, before, after)
        is Schema.Default -> diffAt(schema.schema, before, after)
        is Schema.Lazy -> diffAt(schema.schema(), before, after)
        is Schema.Optional<*> -> if (before == null || after == null) Patch.Set(after) else diffAt(schema.schema, before, after)
        is Schema.Record -> diffFields(schema, before, after)
        is Schema.Collection<*> -> diffItems(schema, before as List<*>, after as List<*>)
        is Schema.Union -> diffCase(schema, before, after)
        else -> Patch.Set(after)
    }
}

@Suppress("UNCHECKED_CAST")
private fun diffFields(schema: Schema.Record<*>, before: Any?, after: Any?): Patch<*> {
    val changed = schema.unsafeFields.mapNotNull { field ->
        val extract = (field as Field<Any?, Any?>).extract
        val b = extract(before)
        val a = extract(after)
        if (b == a) null else field.name to diffAt(field.schema, b, a)
    }
    val patch = Patch.Fields<Any?>(changed.toMap())
    // Construct can drop state the schema does not hold; only a set reproduces such a value.
    return if (runCatching { editFields(schema, patch, before, emptyList()) }.getOrNull() == after) patch else Patch.Set(after)
}

@Suppress("UNCHECKED_CAST")
private fun diffItems(schema: Schema.Collection<*>, before: List<*>, after: List<*>): Patch<*> {
    val keyOf = (schema.key ?: return Patch.Set(after)) as (Any?) -> String
    val beforeByKey = before.associateBy(keyOf)
    val afterByKey = after.associateBy(keyOf)
    if (beforeByKey.size != before.size || afterByKey.size != after.size) return Patch.Set(after)
    // Upserts keep surviving items in place and append new ones; any other order needs a set.
    val patchedOrder = before.map(keyOf).filter { it in afterByKey } + after.map(keyOf).filter { it !in beforeByKey }
    if (patchedOrder != after.map(keyOf)) return Patch.Set(after)
    return Patch.Items(
        upserts = after.filter { val key = keyOf(it); key !in beforeByKey || beforeByKey[key] != it },
        removals = before.map(keyOf).filter { it !in afterByKey },
    )
}

@Suppress("UNCHECKED_CAST")
private fun diffCase(schema: Schema.Union<*>, before: Any?, after: Any?): Patch<*> {
    val cases = schema.unsafeCases as List<Case<Any?, *>>
    val case = cases.firstOrNull { it.deconstruct(after) != null }
    if (case == null || case !== cases.firstOrNull { it.deconstruct(before) != null }) return Patch.Set(after)
    // A case patch yields the case's payload, so it reproduces only a value that is its own payload.
    if (case.deconstruct(after) != after) return Patch.Set(after)
    val patch = diffAt(case.schema, case.deconstruct(before), after)
    return if (patch is Patch.Set<*>) patch else Patch.Case<Any?>(case.name, patch)
}
