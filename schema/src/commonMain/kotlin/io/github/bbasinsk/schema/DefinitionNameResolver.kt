package io.github.bbasinsk.schema

class DefinitionNameResolver {
    private data class SchemaNameEntry(val schema: Schema<*>, var name: String)

    private val entries = mutableListOf<SchemaNameEntry>()
    private val nameOwners = mutableMapOf<String, DefinitionSignature>()
    private val signatureToName = mutableMapOf<DefinitionSignature, String>()

    /**
     * Resolves a unique definition name for a schema with type arguments.
     *
     * Examples:
     * - Box<Person> → "io.package.Box.of.io.package.Person"
     * - Variant<String> → "io.package.Variant.of.kotlin.String"
     * - Collisions get numeric suffixes: ".2", ".3", etc.
     */
    fun resolve(schema: Schema<*>, metadata: ObjectMetadata<*>): String {
        lookup(schema)?.let { return it }

        val base = metadata.qualifiedName()
        store(schema, base) // placeholder to break cycles

        val typeArguments = metadata.typeArguments
        val baseWithArguments = typeArguments.fold(base) { acc, arg -> "$acc.of.$arg" }
        val signature = DefinitionSignature(base, typeArguments)
        signatureToName[signature]?.let { existing ->
            store(schema, existing)
            return existing
        }

        val name = assignName(signature, baseWithArguments)
        store(schema, name)
        return name
    }

    private fun assignName(signature: DefinitionSignature, candidateBase: String): String {
        var candidate = candidateBase
        var suffix = 1
        while (true) {
            val existing = nameOwners[candidate]
            if (existing == null || existing == signature) {
                nameOwners[candidate] = signature
                signatureToName[signature] = candidate
                return candidate
            }

            suffix += 1
            candidate = "$candidateBase.$suffix"
        }
    }

    private fun lookup(schema: Schema<*>): String? =
        entries.firstOrNull { it.schema === schema }?.name

    private fun store(schema: Schema<*>, name: String) {
        val existing = entries.firstOrNull { it.schema === schema }
        if (existing != null) {
            existing.name = name
        } else {
            entries.add(SchemaNameEntry(schema, name))
        }
    }

    private data class DefinitionSignature(
        val baseName: String,
        val typeArguments: List<String>
    )

}
