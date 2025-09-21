package io.github.bbasinsk.http.openapi

import kotlinx.serialization.json.encodeToJsonElement
import kotlin.test.Test
import kotlin.test.assertEquals

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
}
