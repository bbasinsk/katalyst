package io.github.bbasinsk.schema.jsonschema

import io.github.bbasinsk.schema.ObjectMetadata
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.Schema.Companion.case
import io.github.bbasinsk.schema.Schema.Companion.field
import io.github.bbasinsk.schema.Schema.Companion.list
import io.github.bbasinsk.schema.Schema.Companion.record
import io.github.bbasinsk.schema.Schema.Companion.union
import io.github.bbasinsk.schema.Union3
import io.github.bbasinsk.schema.metadataFromType
import kotlinx.serialization.json.Json
import kotlin.coroutines.EmptyCoroutineContext.get
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
        val meta = (boxSchema as Schema.Record<Box<Something>>).metadata
        assertEquals(listOf("io.github.bbasinsk.schema.jsonschema.Something"), meta.typeArguments)

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
}

sealed interface Variant<T> {
    data class Value<A>(val value: A) : Variant<A>
    data class Optional<A>(val variant: Variant<A>) : Variant<A>
    data class Choice<A>(val options: List<Variant<A>>) : Variant<A>
}

data class Something(val name: String, val age: Int)

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

    return Union3(
        metadata = metadata,
        key = "type",
        case(variantValue, "Value"),
        case(variantOptional, "Optional"),
        case(variantChoice, "Choice")
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

private data class BoxWrapper(val value: Box<Something>) {
    companion object {
        val schema: Schema<BoxWrapper> = record(
            field(Schema.box(Schema.something), "value") { value },
            ::BoxWrapper
        )
    }
}
