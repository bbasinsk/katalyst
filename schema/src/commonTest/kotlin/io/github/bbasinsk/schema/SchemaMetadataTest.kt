package io.github.bbasinsk.schema

import kotlin.test.Test
import kotlin.test.assertEquals

class SchemaMetadataTest {

    @Test
    fun `record metadata captures type arguments`() {
        val somethingSchema = Schema.record(
            Schema.field(Schema.string(), "name") { name },
            Schema.field(Schema.int(), "age") { age },
            ::Something
        )

        val boxSchema = Schema.box(somethingSchema)

        val metadata = (boxSchema as Schema.Record<*>).metadata
        assertEquals(
            "io.github.bbasinsk.schema.Box",
            metadata.qualifiedName()
        )
        assertEquals(
            listOf("io.github.bbasinsk.schema.Something"),
            metadata.typeArguments
        )
    }

    @Test
    fun `union metadata captures type arguments`() {
        val somethingSchema = Schema.record(
            Schema.field(Schema.string(), "name") { name },
            Schema.field(Schema.int(), "age") { age },
            ::Something
        )

        val genericSchema = Schema.variant(somethingSchema)

        val metadata = (genericSchema as Schema.Union<*>).metadata
        assertEquals(
            "io.github.bbasinsk.schema.Variant",
            metadata.qualifiedName()
        )
        assertEquals(
            listOf("io.github.bbasinsk.schema.Something"),
            metadata.typeArguments
        )
    }

    @Test
    fun `nested metadata preserves hierarchy`() {
        val nested = Schema.box(Schema.box(Schema.box(Schema.something)))
        val metadata = (nested as Schema.Record<*>).metadata
        assertEquals(
            listOf("io.github.bbasinsk.schema.Box.of.io.github.bbasinsk.schema.Box.of.io.github.bbasinsk.schema.Something"),
            metadata.typeArguments
        )
    }
}

private data class Something(val name: String, val age: Int)
private data class Box<T>(val value: T)

private val Schema.Companion.something: Schema<Something>
    get() = record(
        field(string(), "name") { name },
        field(int(), "age") { age },
        ::Something
    )

private inline fun <reified A> Schema.Companion.box(schema: Schema<A>): Schema<Box<A>> =
    record(
        field(schema, "value") { value },
        ::Box
    )


sealed interface Variant<T> {
    data class Value<A>(val value: A) : Variant<A>
    data class Optional<A>(val variant: Variant<A>) : Variant<A>
    data class Choice<A>(val options: List<Variant<A>>) : Variant<A>
}

inline fun <reified A> Schema.Companion.variant2(schema: Schema<A>) : Schema<Variant<A>> =
    fix { self, metadata ->
        val variantValue: Schema<Variant.Value<A>> = record(
            field(schema, "value") { value },
            Variant<A>::Value
        )

        val variantOptional: Schema<Variant.Optional<A>> = record(
            field(lazy { self }, "variant") { variant },
            Variant<A>::Optional
        )

        val variantChoice: Schema<Variant.Choice<A>> = record(
            field(lazy { list(self) }, "options") { options },
            Variant<A>::Choice
        )

        union(
            case(variantValue, "Value"),
            case(variantOptional, "Optional"),
            case(variantChoice, "Choice"),
            metadata = metadata,
            key = "type",
        )
    }

inline fun <reified A> Schema.Companion.variant(schema: Schema<A>): Schema<Variant<A>> =
    variantImpl(schema, metadata = metadataFromType<Variant<A>>())

fun <A> Schema.Companion.variantImpl(schema: Schema<A>, metadata: ObjectMetadata<Variant<A>>): Schema<Variant<A>> {
    // Derive metadata for inner types from the passed metadata
    val typeArgs = metadata.typeArguments
    val valueMetadata: ObjectMetadata<Variant.Value<A>> = ObjectMetadata(
        name = "Value",
        namespace = metadata.qualifiedName(),
        typeArguments = typeArgs
    )
    val optionalMetadata: ObjectMetadata<Variant.Optional<A>> = ObjectMetadata(
        name = "Optional",
        namespace = metadata.qualifiedName(),
        typeArguments = typeArgs
    )
    val choiceMetadata: ObjectMetadata<Variant.Choice<A>> = ObjectMetadata(
        name = "Choice",
        namespace = metadata.qualifiedName(),
        typeArguments = typeArgs
    )
    
    val variantValue: Schema<Variant.Value<A>> = record(
        field(schema, "value") { value },
        Variant<A>::Value,
        metadata = valueMetadata
    )

    val variantOptional: Schema<Variant.Optional<A>> = record(
        field(lazy { variantImpl(schema, metadata) }, "variant") { variant },
        Variant<A>::Optional,
        metadata = optionalMetadata
    )

    val variantChoice: Schema<Variant.Choice<A>> = record(
        field(lazy { list(variantImpl(schema, metadata)) }, "options") { options },
        Variant<A>::Choice,
        metadata = choiceMetadata
    )

    return union(
        case(variantValue, "Value"),
        case(variantOptional, "Optional"),
        case(variantChoice, "Choice"),
        key = "type",
        metadata = metadata,
    )
}
