package io.github.bbasinsk.http.openapi

import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.Schema.Companion.field
import io.github.bbasinsk.schema.Schema.Companion.int
import io.github.bbasinsk.schema.Schema.Companion.record
import io.github.bbasinsk.schema.Schema.Companion.string
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
        val obj = Human.schema.toSchemaObject()

        assertEquals(
            """
            {
                "oneOf": [
                    {
                        "${'$'}ref": "#/components/schemas/io.github.bbasinsk.http.openapi.Customer"
                    },
                    {
                        "${'$'}ref": "#/components/schemas/io.github.bbasinsk.http.openapi.Employee"
                    }
                ],
                "discriminator": {
                    "propertyName": "type",
                    "mapping": {
                        "Customer": "#/components/schemas/io.github.bbasinsk.http.openapi.Customer",
                        "Employee": "#/components/schemas/io.github.bbasinsk.http.openapi.Employee"
                    }
                }
            }
            """.trimIndent(),
            OpenApiJson.encodeToString(obj)
        )
    }

    @Test
    fun `should convert optional record to SchemaObject`() {
        val obj = OptionalRecord.schema.toSchemaObject()

        assertEquals(
            """
            {
                "type": "object",
                "properties": {
                    "id": {
                        "type": "integer",
                        "format": "int32",
                        "nullable": true
                    },
                    "name": {
                        "type": "string",
                        "nullable": true
                    }
                },
                "required": []
            }
            """.trimIndent(),
            OpenApiJson.encodeToString(obj)
        )
    }

    @Test
    fun `should convert descriptions to SchemaObject`() {
        val schema: Schema<Customer> = record(
            field(int().description("id int desc"), "id") { id },
            field(string().description("name str desc"), "name") { name },
            ::Customer
        )

        assertEquals(
            """
            {
                "type": "object",
                "properties": {
                    "id": {
                        "type": "integer",
                        "format": "int32",
                        "description": "id int desc"
                    },
                    "name": {
                        "type": "string",
                        "description": "name str desc"
                    }
                },
                "required": [
                    "id",
                    "name"
                ]
            }
            """.trimIndent(),
            OpenApiJson.encodeToString(schema.toSchemaObject())
        )
    }
}

data class OptionalRecord(
    val id: Int?,
    val name: String?
) {
    companion object {
        val schema: Schema<OptionalRecord> = with(Schema.Companion) {
            record(
                field(int().optional(), "id") { id },
                field(string().optional(), "name") { name },
                ::OptionalRecord
            )
        }
    }
}
