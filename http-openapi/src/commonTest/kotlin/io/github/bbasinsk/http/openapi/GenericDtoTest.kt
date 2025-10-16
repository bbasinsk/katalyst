package io.github.bbasinsk.http.openapi

import io.github.bbasinsk.http.Http
import io.github.bbasinsk.schema.ObjectMetadata
import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.metadataFromType
import kotlinx.serialization.json.encodeToJsonElement
import kotlin.test.Test
import kotlin.test.assertEquals

class GenericDtoTest {

    @Test
    fun `it produces schema for variant`() {
        val schema = Schema.variant(Schema.person)
        val expected = """
        {
          "oneOf": [
            {
              "${'$'}ref": "#/components/schemas/io.github.bbasinsk.http.openapi.Variant.of.io.github.bbasinsk.http.openapi.Person.ValueWithDiscriminator"
            },
            {
              "${'$'}ref": "#/components/schemas/io.github.bbasinsk.http.openapi.Variant.of.io.github.bbasinsk.http.openapi.Person.OptionalWithDiscriminator"
            },
            {
              "${'$'}ref": "#/components/schemas/io.github.bbasinsk.http.openapi.Variant.of.io.github.bbasinsk.http.openapi.Person.ChoiceWithDiscriminator"
            }
          ],
          "discriminator": {
            "propertyName": "type",
            "mapping": {
              "Value": "#/components/schemas/io.github.bbasinsk.http.openapi.Variant.of.io.github.bbasinsk.http.openapi.Person.ValueWithDiscriminator",
              "Optional": "#/components/schemas/io.github.bbasinsk.http.openapi.Variant.of.io.github.bbasinsk.http.openapi.Person.OptionalWithDiscriminator",
              "Choice": "#/components/schemas/io.github.bbasinsk.http.openapi.Variant.of.io.github.bbasinsk.http.openapi.Person.ChoiceWithDiscriminator"
            }
          }
        }
        """.trimIndent()

        assertEquals(
            OpenApiJson.decodeFromString(expected),
            OpenApiJson.encodeToJsonElement(schema.toSchemaObject())
        )
    }

    @Test
    fun `generic record emits specialized component name`() {
        val http = Http.post { Root / "box" }
            .input { json { box(person) } }
            .output { status(Ok) { json { string() } } }

        val spec = listOf(http).toOpenApiSpec(info = Info(title = "API", version = "1.0.0"))

        val operation = spec.paths["/box"]!!["post"]!!
        val ref = operation.requestBody!!.content["application/json"]!!.schema.ref
        val expectedName = "io.github.bbasinsk.http.openapi.Box.of.io.github.bbasinsk.http.openapi.Person"
        assertEquals("#/components/schemas/$expectedName", ref)
        require(spec.components.schemas.containsKey(expectedName)) {
            "Expected components to contain $expectedName but had ${spec.components.schemas.keys}"
        }
    }

    @Test
    fun `handles deeply nested generic record`() {
        val http = Http.post { Root / "deep-box" }
            .input { json { box(box(box(person))) } }
            .output { status(Ok) { json { string() } } }

        val spec = listOf(http).toOpenApiSpec(info = Info(title = "API", version = "1.0.0"))

        val operation = spec.paths["/deep-box"]!!["post"]!!
        val ref = operation.requestBody!!.content["application/json"]!!.schema.ref
        val expectedName = "io.github.bbasinsk.http.openapi.Box.of.io.github.bbasinsk.http.openapi.Box.of.io.github.bbasinsk.http.openapi.Box.of.io.github.bbasinsk.http.openapi.Person"
        assertEquals("#/components/schemas/$expectedName", ref)
        require(spec.components.schemas.containsKey(expectedName)) {
            "Expected components to contain $expectedName but had ${spec.components.schemas.keys}"
        }
    }

    @Test
    fun `pair schema produces primitive field components`() {
        val schema = Schema.pair(Schema.string(), Schema.int())

        val expected =
            """
            {
              "type": "object",
              "properties": {
                "first": {
                  "type": "string"
                },
                "second": {
                  "type": "integer",
                  "format": "int32"
                }
              },
              "required": [
                "first",
                "second"
              ]
            }
            """.trimIndent()

        assertEquals(
            OpenApiJson.decodeFromString(expected),
            OpenApiJson.encodeToJsonElement(schema.toSchemaObject())
        )
    }

    @Test
    fun `generic pair emits specialized component name`() {
        val http = Http.post { Root / "pair-box" }
            .input { json { box(pair(string(), int())) } }
            .output { status(Ok) { json { string() } } }

        val spec = listOf(http).toOpenApiSpec(info = Info(title = "API", version = "1.0.0"))

        val operation = spec.paths["/pair-box"]!!["post"]!!
        val ref = operation.requestBody!!.content["application/json"]!!.schema.ref
        val expectedName =
            "io.github.bbasinsk.http.openapi.Box.of.io.github.bbasinsk.http.openapi.Pair.of.kotlin.String.of.kotlin.Int"
        assertEquals("#/components/schemas/$expectedName", ref)

        val pairDefinition =
            "io.github.bbasinsk.http.openapi.Pair.of.kotlin.String.of.kotlin.Int"
        require(spec.components.schemas.containsKey(expectedName)) {
            "Expected components to contain $expectedName but had ${spec.components.schemas.keys}"
        }
        require(spec.components.schemas.containsKey(pairDefinition)) {
            "Expected components to contain $pairDefinition but had ${spec.components.schemas.keys}"
        }
    }
}

sealed interface Variant<T> {
    data class Value<A>(val value: A) : Variant<A>
    data class Optional<A>(val variant: Variant<A>) : Variant<A>
    data class Choice<A>(val options: List<Variant<A>>) : Variant<A>
}

data class Person(val name: String, val age: Int)

data class Pair<A, B>(val first: A, val second: B)

val Schema.Companion.person: Schema<Person>
    get() = record(
        field(string(), "name") { name },
        field(int(), "age") { age },
        ::Person
    )

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

    return union(
        case(variantValue, "Value"),
        case(variantOptional, "Optional"),
        case(variantChoice, "Choice"),
        key = "type",
        metadata = metadata
    )
}

data class Box<T>(val value: T)

inline fun <reified A> Schema.Companion.box(schema: Schema<A>): Schema<Box<A>> =
    record(
        field(schema, "value") { value },
        ::Box
    )

inline fun <reified A, reified B> Schema.Companion.pair(
    firstSchema: Schema<A>,
    secondSchema: Schema<B>
): Schema<Pair<A, B>> =
    record(
        field(firstSchema, "first") { first },
        field(secondSchema, "second") { second },
        ::Pair
    )
