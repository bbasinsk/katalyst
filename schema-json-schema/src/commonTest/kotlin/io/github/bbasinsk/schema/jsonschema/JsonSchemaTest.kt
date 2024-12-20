package io.github.bbasinsk.schema.jsonschema

import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.default
import io.github.bbasinsk.schema.optional
import kotlinx.serialization.json.Json
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals

class JsonSchemaTest {
    // https://avro.apache.org/docs/current/specification/

    @Test
    fun `primitive schema`() {
        assertEquals("""{"type":"null"}""", Schema.empty().toJsonSchema().encodeToJsonString())
        assertEquals("""{"type":"boolean"}""", Schema.boolean().toJsonSchema().encodeToJsonString())
        assertEquals("""{"type":"integer"}""", Schema.int().toJsonSchema().encodeToJsonString())
        assertEquals("""{"type":"integer"}""", Schema.long().toJsonSchema().encodeToJsonString())
        assertEquals("""{"type":"number"}""", Schema.double().toJsonSchema().encodeToJsonString())
        assertEquals("""{"type":"number"}""", Schema.float().toJsonSchema().encodeToJsonString())
        assertEquals("""{"type":"string"}""", Schema.string().toJsonSchema().encodeToJsonString())
    }

    @Test
    @Ignore
    fun `base64 schema`() {
        assertEquals(
            """{"type":"string","contentEncoding": "base64"}""",
            Schema.byteArray().toJsonSchema().encodeToJsonString()
        )
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
            Schema.recordSmall().toJsonSchema().encodeToJsonElement()
        )
    }

    @Test
    fun `record collection`() {
        assertEquals(
            Json.parseToJsonElement(
                """
                   {
                      "type": "object",
                      "properties": {
                        "value": {
                          "type": "array",
                          "items": {
                            "type": "object",
                            "properties": { 
                              "a": { "type": "integer" }, 
                              "b": { "type": "string" }
                            }
                          }
                        }
                      }
                    }
                """.trimIndent()
            ),
            Schema.recordCollection().toJsonSchema().encodeToJsonElement()
        )
    }

    @Test
    @Ignore
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
            Schema.person().toJsonSchema().encodeToJsonElement()
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
            Schema.recordMap().toJsonSchema().encodeToJsonElement()
        )
    }

    @Test
    @Ignore
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
            Schema.recordDefault().toJsonSchema().encodeToJsonElement()
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
        field(long().optional(), "a") { a },
        field(string().optional(), "b") { b },
        ::RecordOptional
    )

fun Schema.Companion.recordDefault(): Schema<RecordDefault> =
    record(
        field(long().default(42), "a") { a },
        field(string().default("foo"), "b") { b },
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
        field(long(), "a") { a },
        field(string(), "b") { b },
        ::RecordSmall
    )

data class RecordCollection(val value: List<RecordSmall>)

fun Schema.Companion.recordCollection(): Schema<RecordCollection> =
    record(
        field(list(recordSmall()), "value") { value },
        ::RecordCollection
    )

fun Schema.Companion.recordFlipped(): Schema<RecordSmall> =
    record(
        field(string(), "b") { b },
        field(long(), "a") { a }
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
        field(int(), "id") { id },
        field(string().optional(), "email") { email },
        ::Customer
    )

fun Schema.Companion.employee(): Schema<Employee> =
    record(
        field(int(), "id") { id },
        ::Employee
    )

fun Schema.Companion.person(): Schema<Person> =
    union(
        case(customer()),
        case(employee())
    )
