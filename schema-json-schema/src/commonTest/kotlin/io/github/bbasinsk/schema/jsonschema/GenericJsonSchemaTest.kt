package io.github.bbasinsk.schema.jsonschema

import io.github.bbasinsk.schema.ObjectMetadata
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.Schema.Companion.field
import io.github.bbasinsk.schema.Schema.Companion.record
import io.github.bbasinsk.schema.metadataFromType
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.jsonArray
import kotlin.test.Test
import kotlin.test.assertEquals

class GenericJsonSchemaTest {
    // https://avro.apache.org/docs/current/specification/

    @Test
    fun `generic union works`() {
        val expected = Json.parseToJsonElement(
            """
            {
              "type": "object",
              "properties": {
                "variant": {
                  "${'$'}ref": "#/${'$'}defs/io.github.bbasinsk.schema.jsonschema.Variant.of.io.github.bbasinsk.schema.jsonschema.Something"
                }
              },
              "additionalProperties": false,
              "required": [
                "variant"
              ],
              "${'$'}defs": {
                "io.github.bbasinsk.schema.jsonschema.Variant.of.io.github.bbasinsk.schema.jsonschema.Something": {
                  "anyOf": [
                    {
                      "type": "object",
                      "properties": {
                        "type": {
                          "enum": [
                            "Value"
                          ]
                        },
                        "value": {
                          "${'$'}ref": "#/${'$'}defs/io.github.bbasinsk.schema.jsonschema.Something"
                        }
                      },
                      "additionalProperties": false,
                      "required": [
                        "type",
                        "value"
                      ]
                    },
                    {
                      "type": "object",
                      "properties": {
                        "type": {
                          "enum": [
                            "Optional"
                          ]
                        },
                        "variant": {
                          "${'$'}ref": "#/${'$'}defs/io.github.bbasinsk.schema.jsonschema.Variant.of.io.github.bbasinsk.schema.jsonschema.Something"
                        }
                      },
                      "additionalProperties": false,
                      "required": [
                        "type",
                        "variant"
                      ]
                    },
                    {
                      "type": "object",
                      "properties": {
                        "type": {
                          "enum": [
                            "Choice"
                          ]
                        },
                        "options": {
                          "type": "array",
                          "items": {
                            "${'$'}ref": "#/${'$'}defs/io.github.bbasinsk.schema.jsonschema.Variant.of.io.github.bbasinsk.schema.jsonschema.Something"
                          }
                        }
                      },
                      "additionalProperties": false,
                      "required": [
                        "type",
                        "options"
                      ]
                    }
                  ]
                },
                "io.github.bbasinsk.schema.jsonschema.Something": {
                  "type": "object",
                  "properties": {
                    "name": {
                      "type": "string"
                    },
                    "age": {
                      "type": "integer"
                    }
                  },
                  "additionalProperties": false,
                  "required": [
                    "name",
                    "age"
                  ]
                }
              }
            }
            """.trimIndent()
        )
        val actual = Wrapper.schema.toJsonSchema().encodeToJsonElement()
        assertEquals(expected, actual)
    }

    @Test
    fun `generic record produces specialized definition`() {
        val boxSchema = (BoxWrapper.schema as Schema.Record<BoxWrapper>).unsafeFields().single().schema
        val metadata = (boxSchema as Schema.Record<*>).metadata
        assertEquals(listOf("io.github.bbasinsk.schema.jsonschema.Something"), metadata.typeArguments)

        val jsonSchema = BoxWrapper.schema.toJsonSchema().encodeToJsonElement().jsonObject
        val defs = jsonSchema["\$defs"]!!.jsonObject

        val expectedName = "io.github.bbasinsk.schema.jsonschema.Box.of.io.github.bbasinsk.schema.jsonschema.Something"
        require(defs.containsKey(expectedName)) {
            "Expected defs to contain $expectedName but had ${defs.keys}"
        }

        val boxDefinition = defs[expectedName]!!.jsonObject
        val itemRef = boxDefinition["properties"]!!
            .jsonObject["value"]!!
            .jsonObject["\u0024ref"]
        assertEquals("#/\$defs/io.github.bbasinsk.schema.jsonschema.Something", itemRef!!.jsonPrimitive.content)
    }

    @Test
    fun `handles deeply nested generic types`() {
        val deepSchema = Schema.box(Schema.box(Schema.box(Schema.something)))
        val jsonSchema = deepSchema.toJsonSchema().encodeToJsonElement().jsonObject
        val defs = jsonSchema["\$defs"]!!.jsonObject

        val expectedName = "io.github.bbasinsk.schema.jsonschema.Box.of.io.github.bbasinsk.schema.jsonschema.Box.of.io.github.bbasinsk.schema.jsonschema.Something"
        require(defs.containsKey(expectedName)) {
            "Expected defs to contain $expectedName but had ${defs.keys}"
        }

        val innerRef = defs[expectedName]!!
            .jsonObject["properties"]!!
            .jsonObject["value"]!!
            .jsonObject["\u0024ref"]!!
            .jsonPrimitive
            .content
        assertEquals(
            "#/\$defs/io.github.bbasinsk.schema.jsonschema.Box.of.io.github.bbasinsk.schema.jsonschema.Something",
            innerRef
        )
    }

    @Test
    fun `pair schema produces primitive fields`() {
        val jsonSchema = PairWrapper.schema.toJsonSchema().encodeToJsonElement().jsonObject
        val defs = jsonSchema["\$defs"]!!.jsonObject

        val expectedName = "io.github.bbasinsk.schema.jsonschema.Pair.of.kotlin.String.of.kotlin.Int"
        val pairSchema = defs.getValue(expectedName).jsonObject
        val firstType = pairSchema["properties"]!!
            .jsonObject["first"]!!
            .jsonObject["type"]!!
            .jsonPrimitive
            .content
        val secondType = pairSchema["properties"]!!
            .jsonObject["second"]!!
            .jsonObject["type"]!!
            .jsonPrimitive
            .content

        assertEquals("string", firstType)
        assertEquals("integer", secondType)
        val required = pairSchema["required"]!!.jsonArray.map { it.jsonPrimitive.content }.toSet()
        assertEquals(setOf("first", "second"), required)
        val additionalProperties = pairSchema["additionalProperties"]!!.jsonPrimitive.boolean
        assertEquals(false, additionalProperties)
    }

    @Test
    fun `box of pair emits specialized definition`() {
        val jsonSchema = BoxPairWrapper.schema.toJsonSchema().encodeToJsonElement().jsonObject
        val defs = jsonSchema["\$defs"]!!.jsonObject

        val expectedName =
            "io.github.bbasinsk.schema.jsonschema.Box.of.io.github.bbasinsk.schema.jsonschema.Pair.of.kotlin.String.of.kotlin.Int"
        require(defs.containsKey(expectedName)) {
            "Expected defs to contain $expectedName but had ${defs.keys}"
        }
        val pairName = "io.github.bbasinsk.schema.jsonschema.Pair.of.kotlin.String.of.kotlin.Int"
        require(defs.containsKey(pairName)) {
            "Expected defs to contain $pairName but had ${defs.keys}"
        }
    }
}

sealed interface Variant<T> {
    data class Value<A>(val value: A) : Variant<A>
    data class Optional<A>(val variant: Variant<A>) : Variant<A>
    data class Choice<A>(val options: List<Variant<A>>) : Variant<A>
}

data class Something(val name: String, val age: Int)

data class Pair<A, B>(val first: A, val second: B)

val Schema.Companion.something: Schema<Something>
    get() = record(
        field(string(), "name") { name },
        field(int(), "age") { age },
        ::Something
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
        metadata = metadata,
        key = "type"
    )
}

private data class Wrapper(val variant: Variant<Something>) {
    companion object {
        val schema: Schema<Wrapper> = record(
            field(Schema.variant(Schema.something), "variant") { variant },
            ::Wrapper
        )
    }
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

private data class BoxWrapper(val value: Box<Something>) {
    companion object {
        val schema: Schema<BoxWrapper> = record(
            field(Schema.box(Schema.something), "value") { value },
            ::BoxWrapper
        )
    }
}

private data class PairWrapper(val pair: Pair<String, Int>) {
    companion object {
        val schema: Schema<PairWrapper> = record(
            field(Schema.pair(Schema.string(), Schema.int()), "pair") { pair },
            ::PairWrapper
        )
    }
}

private data class BoxPairWrapper(val value: Box<Pair<String, Int>>) {
    companion object {
        val schema: Schema<BoxPairWrapper> = record(
            field(Schema.box(Schema.pair(Schema.string(), Schema.int())), "value") { value },
            ::BoxPairWrapper
        )
    }
}
