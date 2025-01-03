package io.github.bbasinsk.http.openapi

import kotlinx.serialization.encodeToString
import kotlin.test.Test
import kotlin.test.assertEquals

class SchemaObjectTest {

    @Test
    fun `should convert to SchemaObject`() {
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
}
