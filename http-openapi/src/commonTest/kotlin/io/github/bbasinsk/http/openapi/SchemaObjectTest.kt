package io.github.bbasinsk.http.openapi

import io.github.bbasinsk.http.Http
import io.github.bbasinsk.schema.Schema
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class SchemaObjectTest {

    @Test
    fun `should convert to SchemaObject`() {
        val obj = Person.schema.toSchemaObject()

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
