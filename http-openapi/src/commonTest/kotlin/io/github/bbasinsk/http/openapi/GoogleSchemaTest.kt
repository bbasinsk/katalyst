package io.github.bbasinsk.http.openapi

import kotlinx.serialization.json.encodeToJsonElement
import kotlin.test.Test
import kotlin.test.assertEquals

class GoogleSchemaTest {

    @Test
    fun `should add propertyOrdering when configured`() {
        val obj = Customer.schema.toSchemaObject(
            OutputOptions(
                usePropertyOrdering = true,
                useAnyOf = true
            )
        )

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
        val obj = Human.schema.toSchemaObject(
            OutputOptions(
                usePropertyOrdering = true,
                useAnyOf = true
            )
        )

        assertEquals(
            OpenApiJson.parseToJsonElement(
                """
                {
                  "anyOf": [
                    {
                      "type": "object",
                      "properties": {
                        "type": {
                          "type": "string"
                        },
                        "id": {
                          "type": "integer",
                          "format": "int32"
                        },
                        "name": {
                          "type": "string"
                        }
                      },
                      "propertyOrdering": [
                        "type",
                        "id",
                        "name"
                      ],
                      "required": [
                        "type",
                        "id",
                        "name"
                      ]
                    },
                    {
                      "type": "object",
                      "properties": {
                        "type": {
                          "type": "string"
                        },
                        "id": {
                          "type": "integer",
                          "format": "int32"
                        },
                        "role": {
                          "type": "string",
                          "enum": [
                            "Admin",
                            "User"
                          ]
                        }
                      },
                      "propertyOrdering": [
                        "type",
                        "id",
                        "role"
                      ],
                      "required": [
                        "type",
                        "id",
                        "role"
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
