package io.github.bbasinsk.schema.json

import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.json.scheam.toJsonSchema
import kotlinx.serialization.json.Json
import org.junit.Ignore
import org.junit.Test
import kotlin.test.assertEquals

class JsonSchemaTest {
    // https://avro.apache.org/docs/current/specification/

    @Test
    fun `primitive schema`() {
        assertEquals("""{"type":"null"}""", Schema.empty().toJsonSchema().toString())
        assertEquals("""{"type":"boolean"}""", Schema.boolean().toJsonSchema().toString())
        assertEquals("""{"type":"integer"}""", Schema.int().toJsonSchema().toString())
        assertEquals("""{"type":"integer"}""", Schema.long().toJsonSchema().toString())
        assertEquals("""{"type":"number"}""", Schema.double().toJsonSchema().toString())
        assertEquals("""{"type":"number"}""", Schema.float().toJsonSchema().toString())
        assertEquals("""{"type":"string"}""", Schema.string().toJsonSchema().toString())
    }

    @Test
    @Ignore
    fun `base64 schema`() {
        assertEquals("""{"type":"string","contentEncoding": "base64"}""", Schema.byteArray().toJsonSchema().toString())
    }

    @Test
    fun `record schema`() {
        assertEquals(
            Json.parseToJsonElement(
                """
                    {
                        "type": "object",
                        "properties": {
                            "a": { "type": "integer" },
                            "b": { "type": "string" }
                        }
                    }
                """.trimIndent()
            ),
            Json.parseToJsonElement(
                Schema.recordSmall().toJsonSchema().toString()
            )
        )
    }

    @Test
    fun `oneOf schema`() {
        assertEquals(
            Json.parseToJsonElement(
                """
                    {
                        "oneOf": [
                            {
                                "type": "object",
                                "properties": {
                                    "id": { "type": "integer" },
                                    "email": { "type": "string", "nullable": true }
                                }
                            },
                            {
                                "type": "object",
                                "properties": {
                                    "id": { "type": "integer" }
                                }
                            }
                        ]
                    }
                """.trimIndent()
            ),
            Json.parseToJsonElement(
                Schema.person().toJsonSchema().toString()
            )
        )
    }

    @Test
    fun `stringMap schema`() {
        assertEquals(
            Json.parseToJsonElement(
                """
                    {
                        "type": "object",
                        "additionalProperties": {
                            "type": "object",
                            "properties": {
                                "a": { "type": "integer" },
                                "b": { "type": "string" }
                            }
                        }
                    }
                """.trimIndent()
            ),
            Json.parseToJsonElement(
                Schema.recordMap().toJsonSchema().toString()
            )
        )
    }

    @Test
    fun `default field`() {
        assertEquals(
            Json.parseToJsonElement(
                """
                    {
                        "type": "object",
                        "properties": {
                            "a": {
                                "type": "integer",
                                "default": 42
                            },
                            "b": {
                                "type": "string",
                                "default": "foo"
                            }
                        }
                    }
                """.trimIndent()
            ),
            Json.parseToJsonElement(
                Schema.recordDefault().toJsonSchema().toString()
            )
        )
    }
}
data class RecordOptional(
    val a: Long?,
    val b: String?,
)

data class RecordDefault(
    val a: Long,
    val b: String,
)

fun Schema.Companion.recordOptional(): Schema<RecordOptional> =
    record(
        field("a", long().optional()) { it.a },
        field("b", string().optional()) { it.b },
        ::RecordOptional
    )

fun Schema.Companion.recordDefault(): Schema<RecordDefault> =
    record(
        field("a", long().default(42)) { it.a },
        field("b", string().default("foo")) { it.b },
        ::RecordDefault
    )

fun Schema.Companion.recordMap(): Schema<Map<String, RecordSmall>> =
    stringMap(recordSmall())

data class RecordSmall(
    val a: Long,
    val b: String
)

fun Schema.Companion.recordSmall(): Schema<RecordSmall> =
    record(
        field("a", long()) { it.a },
        field("b", string()) { it.b },
        ::RecordSmall
    )

fun Schema.Companion.recordFlipped(): Schema<RecordSmall> =
    record(
        field("b", string()) { it.b },
        field("a", long()) { it.a }
    ) { b, a -> RecordSmall(a, b) }

sealed interface Person

data class Customer(
    val id: Int,
    val email: String?
) : Person

data class Employee(
    val id: Int,
) : Person

fun Schema.Companion.customer(): Schema<Customer> =
    record(
        field("id", int()) { it.id },
        field("email", string().optional()) { it.email },
        ::Customer
    )

fun Schema.Companion.employee(): Schema<Employee> =
    record(
        field("id", int()) { it.id },
        ::Employee
    )

fun Schema.Companion.person(): Schema<Person> =
    union(
        case(customer()),
        case(employee())
    )
