package io.github.bbasinsk.schema.jsonschema

import com.networknt.schema.JsonSchemaFactory
import com.networknt.schema.SpecVersion
import io.github.bbasinsk.schema.Schema
import kotlin.test.Test
import kotlin.test.assertEquals

class NullableEnumerationValidationTest {
    enum class Unit { F, C }

    private fun accepts(schema: Schema<*>, instance: String): Boolean =
        JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V202012)
            .getSchema(schema.toJsonSchema().encodeToJsonString())
            .validate(instance, com.networknt.schema.InputFormat.JSON)
            .isEmpty()

    @Test
    fun `nullable enumeration accepts null and listed values only`() {
        listOf(
            Schema.enumeration<Unit>().optional(),
            Schema.enumeration<Unit>().optional().description("Temperature unit"),
        ).forEach { schema ->
            assertEquals(
                mapOf("null" to true, "\"F\"" to true, "\"C\"" to true, "\"K\"" to false, "1" to false),
                listOf("null", "\"F\"", "\"C\"", "\"K\"", "1").associateWith { accepts(schema, it) },
                schema.toJsonSchema().encodeToJsonString(),
            )
        }
    }

    @Test
    fun `required enumeration rejects null`() {
        assertEquals(false, accepts(Schema.enumeration<Unit>(), "null"))
    }
}
