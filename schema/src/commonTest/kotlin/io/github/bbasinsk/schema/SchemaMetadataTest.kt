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

}

private data class Something(val name: String, val age: Int)
private data class Box<T>(val value: T)

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

        Union3(
            metadata = metadata,
            key = "type",
            case(variantValue, "Value"),
            case(variantOptional, "Optional"),
            case(variantChoice, "Choice")
        )
    }

inline fun <reified A> Schema.Companion.variant(schema: Schema<A>): Schema<Variant<A>> =
    variantImpl(schema, metadata = metadataFromType<Variant<A>>())

fun <A> Schema.Companion.variantImpl(schema: Schema<A>, metadata: ObjectMetadata<Variant<A>>): Schema<Variant<A>> {
    val variantValue: Schema<Variant.Value<A>> = record(
        field(schema, "value") { value },
        Variant<A>::Value
    )

    val variantOptional: Schema<Variant.Optional<A>> = record(
        field(lazy { variantImpl(schema, metadata) }, "variant") { variant },
        Variant<A>::Optional
    )

    val variantChoice: Schema<Variant.Choice<A>> = record(
        field(lazy { list(variantImpl(schema, metadata)) }, "options") { options },
        Variant<A>::Choice
    )

    return Union3(
        metadata = metadata,
        key = "type",
        case(variantValue, "Value"),
        case(variantOptional, "Optional"),
        case(variantChoice, "Choice")
    )
}
