package io.github.bbasinsk.schema.jsonschema

import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.Schema.Companion.field
import io.github.bbasinsk.schema.Schema.Companion.record
import kotlinx.serialization.json.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

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
        val boxSchema = (BoxWrapper.schema as Schema.Record<BoxWrapper>).unsafeFields.single().schema
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
    fun `unrolled variant with depth 2`() {
        val jsonSchema = Wrapper.schema.toJsonSchema(maxRecursionDepth = 2).encodeToJsonElement().jsonObject
        val defs = jsonSchema["\$defs"]!!.jsonObject

        val variantPrefix = "io.github.bbasinsk.schema.jsonschema.Variant.of.io.github.bbasinsk.schema.jsonschema.Something"

        // Verify top-level ref points to Variant_2
        val variantRef = jsonSchema["properties"]!!.jsonObject["variant"]!!.jsonObject["\$ref"]!!.jsonPrimitive.content
        assertEquals("#/\$defs/${variantPrefix}_2", variantRef)

        // Variant_0: only terminal case (Value)
        val variant0 = defs["${variantPrefix}_0"]!!.jsonObject["anyOf"]!!.jsonArray
        assertEquals(1, variant0.size)
        assertEquals("Value", variant0[0].jsonObject["properties"]!!.jsonObject["type"]!!.jsonObject["enum"]!!.jsonArray[0].jsonPrimitive.content)

        // Variant_1: all 3 cases, recursive refs → Variant_0
        val variant1 = defs["${variantPrefix}_1"]!!.jsonObject["anyOf"]!!.jsonArray
        assertEquals(3, variant1.size)
        val optionalRef1 = variant1[1].jsonObject["properties"]!!.jsonObject["variant"]!!.jsonObject["\$ref"]!!.jsonPrimitive.content
        assertEquals("#/\$defs/${variantPrefix}_0", optionalRef1)
        val choiceItemsRef1 = variant1[2].jsonObject["properties"]!!.jsonObject["options"]!!.jsonObject["items"]!!.jsonObject["\$ref"]!!.jsonPrimitive.content
        assertEquals("#/\$defs/${variantPrefix}_0", choiceItemsRef1)

        // Variant_2: all 3 cases, recursive refs → Variant_1
        val variant2 = defs["${variantPrefix}_2"]!!.jsonObject["anyOf"]!!.jsonArray
        assertEquals(3, variant2.size)
        val optionalRef2 = variant2[1].jsonObject["properties"]!!.jsonObject["variant"]!!.jsonObject["\$ref"]!!.jsonPrimitive.content
        assertEquals("#/\$defs/${variantPrefix}_1", optionalRef2)
        val choiceItemsRef2 = variant2[2].jsonObject["properties"]!!.jsonObject["options"]!!.jsonObject["items"]!!.jsonObject["\$ref"]!!.jsonPrimitive.content
        assertEquals("#/\$defs/${variantPrefix}_1", choiceItemsRef2)
    }

    @Test
    fun `non-recursive union unchanged with maxRecursionDepth`() {
        // Shape is a non-recursive union (Circle | Square) — should produce same output with or without maxRecursionDepth
        val withoutUnroll = ShapeWrapper.schema.toJsonSchema().encodeToJsonElement()
        val withUnroll = ShapeWrapper.schema.toJsonSchema(maxRecursionDepth = 2).encodeToJsonElement()
        assertEquals(withoutUnroll, withUnroll)
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

    @Test
    fun `maxRecursionDepth 0 produces terminal-only output`() {
        val jsonSchema = Wrapper.schema.toJsonSchema(maxRecursionDepth = 0).encodeToJsonElement().jsonObject
        val defs = jsonSchema["\$defs"]!!.jsonObject

        val variantPrefix = "io.github.bbasinsk.schema.jsonschema.Variant.of.io.github.bbasinsk.schema.jsonschema.Something"

        // Top-level ref points to Variant_0
        val variantRef = jsonSchema["properties"]!!.jsonObject["variant"]!!.jsonObject["\$ref"]!!.jsonPrimitive.content
        assertEquals("#/\$defs/${variantPrefix}_0", variantRef)

        // Variant_0 exists and contains only terminal case (Value)
        val variant0 = defs["${variantPrefix}_0"]!!.jsonObject["anyOf"]!!.jsonArray
        assertEquals(1, variant0.size)
        assertEquals("Value", variant0[0].jsonObject["properties"]!!.jsonObject["type"]!!.jsonObject["enum"]!!.jsonArray[0].jsonPrimitive.content)
    }

    @Test
    fun `negative maxRecursionDepth throws`() {
        assertFailsWith<IllegalArgumentException> {
            Wrapper.schema.toJsonSchema(maxRecursionDepth = -1)
        }
    }

    @Test
    fun `optional recursive union includes null in unrolled ref`() {
        val jsonSchema = OptionalVariantWrapper.schema.toJsonSchema(maxRecursionDepth = 1).encodeToJsonElement().jsonObject
        val variantField = jsonSchema["properties"]!!.jsonObject["variant"]!!.jsonObject

        // Top-level ref should be wrapped in anyOf with null
        val anyOf = variantField["anyOf"]!!.jsonArray
        assertEquals(2, anyOf.size)
        val refEntry = anyOf.first { it.jsonObject.containsKey("\$ref") }
        val nullEntry = anyOf.first { it.jsonObject.containsKey("type") }
        assertEquals("null", nullEntry.jsonObject["type"]!!.jsonPrimitive.content)
        assertTrue(refEntry.jsonObject["\$ref"]!!.jsonPrimitive.content.endsWith("_1"))
    }

    @Test
    fun `all-recursive union throws`() {
        assertFailsWith<IllegalArgumentException> {
            AllRecursiveWrapper.schema.toJsonSchema(maxRecursionDepth = 2)
        }
    }

    @Test
    fun `mutually recursive unions both get unrolled`() {
        val jsonSchema = MutualWrapper.schema.toJsonSchema(maxRecursionDepth = 1).encodeToJsonElement().jsonObject
        val defs = jsonSchema["\$defs"]!!.jsonObject

        // Both unions should have numbered definitions
        val treeAPrefix = MutualWrapper.treeAName
        val treeBPrefix = MutualWrapper.treeBName
        assertTrue(defs.containsKey("${treeAPrefix}_0"), "Missing ${treeAPrefix}_0")
        assertTrue(defs.containsKey("${treeAPrefix}_1"), "Missing ${treeAPrefix}_1")
        assertTrue(defs.containsKey("${treeBPrefix}_0"), "Missing ${treeBPrefix}_0")
        assertTrue(defs.containsKey("${treeBPrefix}_1"), "Missing ${treeBPrefix}_1")

        // Level 0 should contain only terminal cases (Leaf)
        val treeA0 = defs["${treeAPrefix}_0"]!!.jsonObject["anyOf"]!!.jsonArray
        assertEquals(1, treeA0.size)
        assertEquals("Leaf", treeA0[0].jsonObject["properties"]!!.jsonObject["type"]!!.jsonObject["enum"]!!.jsonArray[0].jsonPrimitive.content)

        val treeB0 = defs["${treeBPrefix}_0"]!!.jsonObject["anyOf"]!!.jsonArray
        assertEquals(1, treeB0.size)
        assertEquals("Leaf", treeB0[0].jsonObject["properties"]!!.jsonObject["type"]!!.jsonObject["enum"]!!.jsonArray[0].jsonPrimitive.content)

        // Level 1 should have both cases; TreeA_1 refs TreeB_1 (completed), TreeB_1 refs TreeA_0 (during generation)
        val treeA1 = defs["${treeAPrefix}_1"]!!.jsonObject["anyOf"]!!.jsonArray
        assertEquals(2, treeA1.size)
        val nodeA1 = treeA1.first {
            it.jsonObject["properties"]!!.jsonObject["type"]!!.jsonObject["enum"]!!.jsonArray[0].jsonPrimitive.content == "Node"
        }
        val childRefA1 = nodeA1.jsonObject["properties"]!!.jsonObject["child"]!!.jsonObject["\$ref"]!!.jsonPrimitive.content
        assertEquals("#/\$defs/${treeBPrefix}_1", childRefA1)

        val treeB1 = defs["${treeBPrefix}_1"]!!.jsonObject["anyOf"]!!.jsonArray
        assertEquals(2, treeB1.size)
        val nodeB1 = treeB1.first {
            it.jsonObject["properties"]!!.jsonObject["type"]!!.jsonObject["enum"]!!.jsonArray[0].jsonPrimitive.content == "Node"
        }
        val childRefB1 = nodeB1.jsonObject["properties"]!!.jsonObject["child"]!!.jsonObject["\$ref"]!!.jsonPrimitive.content
        assertEquals("#/\$defs/${treeAPrefix}_0", childRefB1)
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

inline fun <reified A> Schema.Companion.variant(schema: Schema<A>): Schema<Variant<A>> = fix { self, metadata ->
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

// Non-recursive union for testing that unrolling doesn't affect it
sealed interface Shape {
    data class Circle(val radius: Double) : Shape
    data class Square(val side: Double) : Shape
}

val Schema.Companion.shape: Schema<Shape>
    get() {
        val circle: Schema<Shape.Circle> = record(
            field(double(), "radius") { radius },
            Shape::Circle
        )
        val square: Schema<Shape.Square> = record(
            field(double(), "side") { side },
            Shape::Square
        )
        return union(
            case(circle, "Circle"),
            case(square, "Square"),
            key = "type"
        )
    }

private data class ShapeWrapper(val shape: Shape) {
    companion object {
        val schema: Schema<ShapeWrapper> = record(
            field(Schema.shape, "shape") { shape },
            ::ShapeWrapper
        )
    }
}

private data class OptionalVariantWrapper(val variant: Variant<Something>?) {
    companion object {
        val schema: Schema<OptionalVariantWrapper> = record(
            field(Schema.variant(Schema.something).optional(), "variant") { variant },
            ::OptionalVariantWrapper
        )
    }
}

// Union where every case is recursive (no terminal cases)
private sealed interface AllRecursive {
    data class Left(val next: AllRecursive) : AllRecursive
    data class Right(val next: AllRecursive) : AllRecursive
}

private val Schema.Companion.allRecursive: Schema<AllRecursive>
    get() = fix { self, metadata ->
        val left: Schema<AllRecursive.Left> = record(
            field(lazy { self }, "next") { next },
            AllRecursive::Left
        )
        val right: Schema<AllRecursive.Right> = record(
            field(lazy { self }, "next") { next },
            AllRecursive::Right
        )
        union(
            case(left, "Left"),
            case(right, "Right"),
            metadata = metadata,
            key = "type"
        )
    }

private data class AllRecursiveWrapper(val value: AllRecursive) {
    companion object {
        val schema: Schema<AllRecursiveWrapper> = record(
            field(Schema.allRecursive, "value") { value },
            ::AllRecursiveWrapper
        )
    }
}

// Mutually recursive unions: TreeA references TreeB and vice versa
private sealed interface TreeA {
    data class Leaf(val value: String) : TreeA
    data class Node(val child: TreeB) : TreeA
}

private sealed interface TreeB {
    data class Leaf(val value: Int) : TreeB
    data class Node(val child: TreeA) : TreeB
}

private data class MutualWrapper(val a: TreeA, val b: TreeB) {
    companion object {
        private val treeA: Schema<TreeA> by kotlin.lazy {
            Schema.union(
                Schema.case(record(field(Schema.string(), "value") { value }, TreeA::Leaf), "Leaf"),
                Schema.case(record(field(Schema.lazy { treeB }, "child") { child }, TreeA::Node), "Node"),
                key = "type"
            )
        }
        private val treeB: Schema<TreeB> by kotlin.lazy {
            Schema.union(
                Schema.case(record(field(Schema.int(), "value") { value }, TreeB::Leaf), "Leaf"),
                Schema.case(record(field(Schema.lazy { treeA }, "child") { child }, TreeB::Node), "Node"),
                key = "type"
            )
        }
        val treeAName: String get() = "io.github.bbasinsk.schema.jsonschema.TreeA"
        val treeBName: String get() = "io.github.bbasinsk.schema.jsonschema.TreeB"
        val schema: Schema<MutualWrapper> = record(
            field(Schema.lazy { treeA }, "a") { a },
            field(Schema.lazy { treeB }, "b") { b },
            ::MutualWrapper
        )
    }
}
