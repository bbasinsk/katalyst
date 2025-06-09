package io.github.bbasinsk.schema.jsonschema

import io.github.bbasinsk.schema.Schema
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
    fun `base64 schema`() {
        assertEquals(
            Json.parseToJsonElement("""{"type":"string","contentEncoding":"base64"}"""),
            Schema.byteArray().toJsonSchema().encodeToJsonElement()
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
                  },
                  "required": ["a", "b"],
                  "additionalProperties": false
                }
                """.trimIndent()
            ),
            Schema.recordSmall().toJsonSchema().encodeToJsonElement()
        )
    }

    @Test
    fun `record with description schema`() {
        val schema = with(Schema.Companion) {
            record(
                field(long().description("a desc"), "a") { a },
                field(string().description("b desc"), "b") { b },
                ::RecordSmall
            ).description("record desc")
        }

        assertEquals(
            Json.parseToJsonElement(
                """
                {
                  "type": "object",
                  "properties": {
                    "a": { 
                      "type": "integer",
                      "description": "a desc"
                    },
                    "b": { 
                      "type": "string",
                      "description": "b desc"
                    }
                  },
                  "required": ["a", "b"],
                  "additionalProperties": false,
                  "description": "record desc"
                }
                """.trimIndent()
            ),
            schema.toJsonSchema().encodeToJsonElement()
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
                          "items": { "${'$'}ref": "#/${'$'}defs/io.github.bbasinsk.schema.jsonschema.RecordSmall" }
                        }
                      },
                      "required": ["value"],
                      "additionalProperties": false,
                      "${'$'}defs": {
                        "io.github.bbasinsk.schema.jsonschema.RecordSmall": {
                          "type": "object",
                          "properties": {
                            "a": { "type": "integer" },
                            "b": { "type": "string" }
                          },
                          "required": ["a", "b"],
                          "additionalProperties": false
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
    fun `stringMap schema`() {
        assertEquals(
            Json.parseToJsonElement(
                """
                    {
                      "type": "object",
                      "additionalProperties": {
                        "${'$'}ref":"#/${'$'}defs/io.github.bbasinsk.schema.jsonschema.RecordSmall"
                      },
                      "${'$'}defs": {
                        "io.github.bbasinsk.schema.jsonschema.RecordSmall":{
                          "type": "object",
                          "properties": {
                            "a": {"type": "integer"},
                            "b": {"type": "string"}
                          },
                          "required":["a","b"],
                          "additionalProperties":false
                        }
                      }
                    }
                """.trimIndent()
            ),
            Schema.recordMap().toJsonSchema().encodeToJsonElement()
        )
    }

    @Test
    fun `optional field`() {
        assertEquals(
            Json.parseToJsonElement(
                """
                {
                  "type": "object",
                  "properties": {
                    "a": { 
                      "type": ["integer", "null"]
                    },
                    "b": { 
                      "type": ["string", "null"]
                    }
                  },
                  "required": ["a", "b"],
                  "additionalProperties": false
                }
                """.trimIndent()
            ),
            Schema.recordOptional().toJsonSchema().encodeToJsonElement()
        )
    }

    @Test
    @Ignore // Need Schema.Json to encode the default value (as JsonElement)
    fun `default field`() {
        assertEquals(
            Json.parseToJsonElement(
                """
                    {
                      "${'$'}ref": "#/${'$'}defs/io.github.bbasinsk.schema.jsonschema.RecordDefault",
                      "${'$'}defs": {
                        "io.github.bbasinsk.schema.jsonschema.RecordDefault": {
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
                          },
                          "required": ["a", "b"],
                          "additionalProperties": false
                        }
                      }
                    }
                """.trimIndent()
            ),
            Schema.recordDefault().toJsonSchema().encodeToJsonElement()
        )
    }

    @Test
    fun `union works`() {
        val expected = Json.parseToJsonElement("""
            {
              "anyOf": [
                {
                  "type": "object",
                  "properties": {
                    "type": {"enum": ["Customer"]},
                    "id": {"type": "integer"},
                    "email": {"type": ["string", "null"]}
                  },
                  "additionalProperties": false,
                  "required": ["type","id","email"],
                  "description": "A customer description"
                },
                {
                  "type": "object",
                  "properties": {
                    "type": {"enum": ["Employee"]},
                    "id": {"type": "integer"}
                  },
                  "additionalProperties": false,
                  "required": ["type","id"],
                  "description": "An employee description"
                }
              ]
            }
        """.trimIndent())
        val actual = Schema.person().toJsonSchema().encodeToJsonElement()
        assertEquals(expected, actual)
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
    ).description("A customer description")

fun Schema.Companion.employee(): Schema<Employee> =
    record(
        field(int(), "id") { id },
        ::Employee
    ).description("An employee description")

fun Schema.Companion.person(): Schema<Person> =
    union(
        case(customer()),
        case(employee())
    )
