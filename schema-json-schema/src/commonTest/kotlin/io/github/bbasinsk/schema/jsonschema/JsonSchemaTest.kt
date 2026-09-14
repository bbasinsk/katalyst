package io.github.bbasinsk.schema.jsonschema

import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.kotlin.duration
import io.github.bbasinsk.schema.kotlin.instant
import io.github.bbasinsk.schema.kotlin.uuid
import io.github.bbasinsk.schema.orElse
import io.github.bbasinsk.schema.transform
import kotlinx.serialization.json.Json
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

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
    fun `duration schema emits ISO duration format`() {
        assertEquals(
            Json.parseToJsonElement("""{"type":"string","format":"duration"}"""),
            Schema.duration().toJsonSchema().encodeToJsonElement()
        )
    }

    @Test
    @OptIn(kotlin.time.ExperimentalTime::class, kotlin.uuid.ExperimentalUuidApi::class)
    fun `instant and UUID codecs emit standard formats`() {
        assertEquals(JsonSchema(type = listOf("string"), format = "date-time"), Schema.instant().toJsonSchema())
        assertEquals(JsonSchema(type = listOf("string"), format = "uuid"), Schema.uuid().toJsonSchema())
    }

    @Test
    fun `format and description compose in either order`() {
        val expected = Json.parseToJsonElement(
            """{"type":"string","description":"ISO duration","format":"duration"}"""
        )

        assertEquals(
            expected,
            Schema.string().format("duration").description("ISO duration").toJsonSchema().encodeToJsonElement()
        )
        assertEquals(
            expected,
            Schema.string().description("ISO duration").format("duration").toJsonSchema().encodeToJsonElement()
        )
    }

    @Test
    fun `later annotations override built-in and wrapped annotations`() {
        val schema = Schema.duration().description("Original duration")
            .optional()
            .format("custom-duration").description("Updated duration")

        assertEquals(
            JsonSchema(type = listOf("string", "null"), description = "Updated duration", format = "custom-duration"),
            schema.toJsonSchema()
        )
    }

    @Test
    fun `nonstring primitives retain format annotations`() {
        listOf(
            Schema.boolean() to "flag",
            Schema.int() to "int32",
            Schema.long() to "int64",
            Schema.float() to "float",
            Schema.double() to "double"
        ).forEach { (schema, format) ->
            assertEquals(format, schema.format(format).toJsonSchema().format)
        }
    }

    @Test
    fun `collection annotations do not override item annotations or nullability`() {
        val schema = Schema.list(Schema.duration().description("Item duration"))
            .description("Durations").format("duration-list").optional()

        assertEquals(
            JsonSchema(
                type = listOf("array", "null"),
                description = "Durations",
                format = "duration-list",
                items = JsonSchema(type = listOf("string"), description = "Item duration", format = "duration")
            ),
            schema.toJsonSchema()
        )
    }

    @Test
    fun `composite and unconstrained schemas retain annotations`() {
        listOf(
            Schema.recordSmall(),
            Schema.person(),
            Schema.stringMap(Schema.string()),
            Schema.dynamic(),
            Schema.empty()
        ).forEach { schema ->
            val json = schema.description("Annotated value").format("custom").toJsonSchema()
            assertEquals("Annotated value", json.description)
            assertEquals("custom", json.format)
        }
    }

    @Test
    fun `annotations on shared record and union references stay local`() {
        val record = Schema.recordSmall()
        val union = Schema.person()
        val records = Schema.record(
            Schema.field(record.description("Left value").format("left"), "left") { first },
            Schema.field(record.description("Right value").format("right"), "right") { second },
            Schema.field(record, "plain") { third },
            ::Triple
        )
        val unions = Schema.record(
            Schema.field(union.description("Left value").format("left"), "left") { first },
            Schema.field(union.description("Right value").format("right"), "right") { second },
            Schema.field(union, "plain") { third },
            ::Triple
        )

        listOf(records, unions).forEach { schema ->
            val json = schema.toJsonSchema()
            val properties = json.properties!!
            assertEquals("Left value", properties.getValue("left").description)
            assertEquals("left", properties.getValue("left").format)
            assertEquals("Right value", properties.getValue("right").description)
            assertEquals("right", properties.getValue("right").format)
            val plain = properties.getValue("plain")
            assertNull(plain.description)
            assertNull(plain.format)
            val shared = json.defs!!.getValue(plain.ref!!.substringAfterLast('/'))
            assertNull(shared.description)
            assertNull(shared.format)
        }
    }

    @Test
    fun `nullable transformed fields preserve format and description`() {
        data class Durations(val before: Duration?, val after: Duration?)

        val schema = Schema.record(
            Schema.field(
                Schema.duration().description("ISO duration").optional(),
                "before"
            ) { before },
            Schema.field(
                Schema.string().description("ISO duration")
                    .transform({ Duration.parseIsoString(it) }) { it.toIsoString() }
                    .optional().format("duration"),
                "after"
            ) { after },
            ::Durations
        )

        assertEquals(
            Json.parseToJsonElement(
                """
                {
                  "type": "object",
                  "properties": {
                    "before": {"type": ["string", "null"], "description": "ISO duration", "format": "duration"},
                    "after": {"type": ["string", "null"], "description": "ISO duration", "format": "duration"}
                  },
                  "required": ["before", "after"],
                  "additionalProperties": false
                }
                """.trimIndent()
            ),
            schema.toJsonSchema().encodeToJsonElement()
        )
    }

    @Test
    fun `dynamic schema`() {
        assertEquals("""{}""", Schema.dynamic().toJsonSchema().encodeToJsonString())
    }

    @Test
    fun `nullable dynamic schema`() {
        // Dynamic already accepts any value (including null), so optional doesn't change the output
        assertEquals("""{}""", Schema.dynamic().optional().toJsonSchema().encodeToJsonString())
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
                    "a": {"type": ["integer", "null"]},
                    "b": {"type": ["string", "null"]}
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
        val expected = Json.parseToJsonElement(
            """
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
        """.trimIndent()
        )
        val actual = Schema.person().toJsonSchema().encodeToJsonElement()
        assertEquals(expected, actual)
    }

    @Test
    fun `nullable record type is object or null`() {
        data class WithNullableRecordField(
            val record: RecordSmall?
        )

        val schema = Schema.record(
            Schema.field(Schema.recordSmall().optional(), "record") { record },
            ::WithNullableRecordField
        )

        val expected = Json.parseToJsonElement(
            $$"""
            {
              "type": "object",
              "properties": {
                "record": {
                  "$ref": "#/$defs/io.github.bbasinsk.schema.jsonschema.RecordSmall"
                }
              },
              "required": ["record"],
              "additionalProperties": false,
              "$defs": {
                "io.github.bbasinsk.schema.jsonschema.RecordSmall": {
                  "type": ["object", "null"],
                  "properties": {
                    "a": {"type": "integer"},
                    "b": {"type": "string"}
                  },
                  "required": ["a", "b"],
                  "additionalProperties": false
                }
              }
            }
            """.trimIndent()
        )

        assertEquals(expected, schema.toJsonSchema().encodeToJsonElement().also { println(it) })
    }

    @Test
    fun `orElse produces anyOf with both branches`() {
        val schema = Schema.double().orElse(Schema.string()) { it.toDouble() }

        assertEquals(
            Json.parseToJsonElement(
                """
                {
                  "anyOf": [
                    {"type": "number"},
                    {"type": "string"}
                  ]
                }
                """.trimIndent()
            ),
            schema.toJsonSchema().encodeToJsonElement()
        )
    }

    @Test
    fun `optional orElse adds null to anyOf`() {
        val schema = Schema.double().orElse(Schema.string()) { it.toDouble() }.optional()

        assertEquals(
            Json.parseToJsonElement(
                """
                {
                  "anyOf": [
                    {"type": "number"},
                    {"type": "string"},
                    {"type": "null"}
                  ]
                }
                """.trimIndent()
            ),
            schema.toJsonSchema().encodeToJsonElement()
        )
    }

    @Test
    fun `optional orElse preserves outer and branch metadata`() {
        val schema = Schema.duration().description("ISO duration")
            .orElse(Schema.int().description("Seconds")) { it.seconds }
            .format("duration").description("Delay").optional()

        assertEquals(
            Json.parseToJsonElement(
                """
                {
                  "description": "Delay",
                  "format": "duration",
                  "anyOf": [
                    {"type": "string", "description": "ISO duration", "format": "duration"},
                    {"type": "integer", "description": "Seconds"},
                    {"type": "null"}
                  ]
                }
                """.trimIndent()
            ),
            schema.toJsonSchema().encodeToJsonElement()
        )
    }

    @Test
    fun `orElse in record field`() {
        data class WithOrElse(val value: Double)

        val schema = Schema.record(
            Schema.field(
                Schema.double().orElse(Schema.string()) { it.toDouble() },
                "value"
            ) { value },
            ::WithOrElse
        )

        assertEquals(
            Json.parseToJsonElement(
                """
                {
                  "type": "object",
                  "properties": {
                    "value": {
                      "anyOf": [
                        {"type": "number"},
                        {"type": "string"}
                      ]
                    }
                  },
                  "required": ["value"],
                  "additionalProperties": false
                }
                """.trimIndent()
            ),
            schema.toJsonSchema().encodeToJsonElement()
        )
    }

    @Test
    fun `nullable union adds subtype of null type`() {
        data class WithNullableUnionField(
            val person: Person?
        )

        val schema = Schema.record(
            Schema.field(Schema.person().optional(), "person") { person },
            ::WithNullableUnionField
        )

        val expected = Json.parseToJsonElement(
            """
            {
              "type": "object",
              "properties": {
                "person": {
                  "${'$'}ref": "#/${'$'}defs/io.github.bbasinsk.schema.jsonschema.Person"
                }
              },
              "additionalProperties": false,
              "required": ["person"],
              "${'$'}defs": {
                "io.github.bbasinsk.schema.jsonschema.Person": {
                  "anyOf": [
                    {
                      "type": "object",
                      "description": "A customer description",
                      "properties": {
                        "type": {"enum": ["Customer"]},
                        "id": {"type": "integer"},
                        "email": {"type": ["string", "null"]}
                      },
                      "additionalProperties": false,
                      "required": ["type","id","email"]
                    },
                    {
                      "type": "object",
                      "description": "An employee description",
                      "properties": {
                        "type": {"enum": ["Employee"]},
                        "id": {"type": "integer"}
                      },
                      "additionalProperties": false,
                      "required": ["type","id"]
                    },
                    {"type": "null"}
                  ]
                }
              }
            }
            """.trimIndent()
        )

        assertEquals(expected, schema.toJsonSchema().encodeToJsonElement().also { println(it) })
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
        field(long(), "a") { a },
        { b, a -> RecordSmall(a, b) }
    )

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
