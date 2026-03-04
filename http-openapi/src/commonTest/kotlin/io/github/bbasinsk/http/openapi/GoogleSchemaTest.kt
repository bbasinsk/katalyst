package io.github.bbasinsk.http.openapi

import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.Schema.Companion.string
import kotlinx.serialization.json.encodeToJsonElement
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class GoogleSchemaTest {

    @Test
    fun `should add propertyOrdering when configured`() {
        val obj = Customer.schema.toSchemaObject(OutputOptions.gemini)

        assertEquals(
            OpenApiJson.parseToJsonElement(
                """
                {
                    "type": "object",
                    "properties": {
                        "id": {
                            "type": "integer",
                            "format": "int32"
                        },
                        "name": {
                            "type": "string"
                        }
                    },
                    "required": [
                        "id",
                        "name"
                    ],
                    "propertyOrdering": [
                        "id",
                        "name"
                    ]
                }
                """.trimIndent()
            ),
            OpenApiJson.encodeToJsonElement(obj)
        )
    }

    @Test
    fun `should add propertyOrdering to union when configured`() {
        val obj = Human.schema.toSchemaObject(OutputOptions.gemini)

        assertEquals(
            OpenApiJson.parseToJsonElement(
                """
                {
                  "anyOf": [
                    {
                      "allOf": [
                        {
                          "type": "object",
                          "properties": {
                            "type": {
                              "type": "string",
                              "enum": ["Customer"]
                            }
                          },
                          "required": ["type"]
                        },
                        {
                          "type": "object",
                          "properties": {
                            "id": {
                              "type": "integer",
                              "format": "int32"
                            },
                            "name": {
                              "type": "string"
                            }
                          },
                          "propertyOrdering": [
                            "id",
                            "name"
                          ],
                          "required": [
                            "id",
                            "name"
                          ]
                        }
                      ]
                    },
                    {
                      "allOf": [
                        {
                          "type": "object",
                          "properties": {
                            "type": {
                              "type": "string",
                              "enum": ["Employee"]
                            }
                          },
                          "required": ["type"]
                        },
                        {
                          "type": "object",
                          "properties": {
                            "id": {
                              "type": "integer",
                              "format": "int32"
                            },
                            "role": {
                              "type": "string",
                              "format": "enum",
                              "enum": [
                                "Admin",
                                "User"
                              ]
                            }
                          },
                          "propertyOrdering": [
                            "id",
                            "role"
                          ],
                          "required": [
                            "id",
                            "role"
                          ]
                        }
                      ]
                    }
                  ]
                }
                """.trimIndent()
            ),
            OpenApiJson.encodeToJsonElement(obj)
        )
    }

    @Test
    fun `should handle recursive union with maxRecursionDepth 0`() {
        val schema = with(Schema) { variant(string()) }
        val obj = schema.toSchemaObject(OutputOptions.gemini.copy(maxRecursionDepth = 0))
        val json = OpenApiJson.encodeToJsonElement(obj)

        // Top level should have all 3 cases
        val topAnyOf = obj.anyOf!!
        assertEquals(3, topAnyOf.size)

        // The Optional case's variant field should only have the terminal case (Value)
        val optionalCase = topAnyOf[1] // Optional
        val optionalRecord = optionalCase.allOf!![1] // the record part
        val variantField = optionalRecord.properties!!["variant"]!!
        val nestedAnyOf = variantField.anyOf!!
        assertEquals(1, nestedAnyOf.size, "At depth 0, recursive re-entry should only include terminal cases")

        // The Choice case's options items should only have the terminal case (Value)
        val choiceCase = topAnyOf[2] // Choice
        val choiceRecord = choiceCase.allOf!![1]
        val optionsField = choiceRecord.properties!!["options"]!!
        val itemsAnyOf = optionsField.items!!.anyOf!!
        assertEquals(1, itemsAnyOf.size, "At depth 0, recursive re-entry in collection should only include terminal cases")
    }

    @Test
    fun `should handle recursive union with maxRecursionDepth 1`() {
        val schema = with(Schema) { variant(string()) }
        val obj = schema.toSchemaObject(OutputOptions.gemini.copy(maxRecursionDepth = 1))

        // Top level should have all 3 cases
        val topAnyOf = obj.anyOf!!
        assertEquals(3, topAnyOf.size)

        // The Optional case's variant field should have all 3 cases at depth 1
        val optionalCase = topAnyOf[1]
        val optionalRecord = optionalCase.allOf!![1]
        val variantField = optionalRecord.properties!!["variant"]!!
        val depth1AnyOf = variantField.anyOf!!
        assertEquals(3, depth1AnyOf.size, "At depth 1, recursive re-entry should include all cases")

        // But the doubly-nested variant should only have terminal cases (depth exhausted)
        val innerOptional = depth1AnyOf[1]
        val innerRecord = innerOptional.allOf!![1]
        val innerVariant = innerRecord.properties!!["variant"]!!
        val depth0AnyOf = innerVariant.anyOf!!
        assertEquals(1, depth0AnyOf.size, "At depth 0 (two levels deep), should only include terminal cases")
    }

    @Test
    fun `should reject negative maxRecursionDepth`() {
        assertFailsWith<IllegalArgumentException> {
            OutputOptions(maxRecursionDepth = -1)
        }
    }

    @Test
    fun `non-recursive union should work without maxRecursionDepth`() {
        // Non-recursive unions should still work fine without maxRecursionDepth
        val obj = Human.schema.toSchemaObject(OutputOptions.gemini)
        assertEquals(2, obj.anyOf!!.size)
    }
}
