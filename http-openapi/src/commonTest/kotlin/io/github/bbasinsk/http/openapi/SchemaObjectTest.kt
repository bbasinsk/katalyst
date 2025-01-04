package io.github.bbasinsk.http.openapi

import kotlinx.serialization.encodeToString
import kotlin.test.Test
import kotlin.test.assertEquals

class SchemaObjectTest {

    @Test
    fun `should convert record to SchemaObject`() {
        val obj = Customer.schema.toSchemaObject()

        assertEquals(
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
                ]
            }
            """.trimIndent(),
            OpenApiJson.encodeToString(obj)
        )
    }

    @Test
    fun `should convert union to SchemaObject`() {
        val obj = Human.schema.toSchemaObject(false)

        assertEquals(
            """
            {
                "anyOf": [
                    {
                        "type": "object",
                        "properties": {
                            "id": {
                                "type": "integer",
                                "format": "int32"
                            },
                            "name": {
                                "type": "string"
                            },
                            "type": {
                                "type": "string"
                            }
                        },
                        "required": [
                            "id",
                            "name",
                            "type"
                        ]
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
                                "enum": [
                                    "Admin",
                                    "User"
                                ]
                            },
                            "type": {
                                "type": "string"
                            }
                        },
                        "required": [
                            "id",
                            "role",
                            "type"
                        ]
                    }
                ]
            }
            """.trimIndent(),
            OpenApiJson.encodeToString(obj)
        )
    }
}
