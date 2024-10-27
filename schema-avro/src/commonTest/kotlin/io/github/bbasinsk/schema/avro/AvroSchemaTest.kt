package io.github.bbasinsk.schema.avro

import io.github.bbasinsk.schema.Schema
import kotlinx.serialization.json.Json
import org.junit.Test
import kotlin.test.assertEquals

class AvroSchemaTest {
    // https://avro.apache.org/docs/current/specification/

    @Test
    fun `primitive schema`() {
        assertEquals(""""null"""", Schema.empty().toAvroSchema().toString())
        assertEquals(""""boolean"""", Schema.boolean().toAvroSchema().toString())
        assertEquals(""""int"""", Schema.int().toAvroSchema().toString())
        assertEquals(""""long"""", Schema.long().toAvroSchema().toString())
        assertEquals(""""double"""", Schema.double().toAvroSchema().toString())
        assertEquals(""""float"""", Schema.float().toAvroSchema().toString())
        assertEquals(""""string"""", Schema.string().toAvroSchema().toString())
    }

    @Test
    fun `record schema`() {
        assertEquals(
            Json.parseToJsonElement(
                """
                    {
                        "type":"record",
                        "name":"Customer",
                        "namespace":"io.github.bbasinsk.schema.avro",
                        "fields":[
                            {"name":"id","type":"int"},
                            {"name":"email","type":["null","string"],"default":null}
                        ]
                    }
                """.trimIndent()
            ),
            Json.parseToJsonElement(
                Schema.customer().toAvroSchema().toString()
            )
        )
    }

    @Test
    fun `oneOf schema`() {
        assertEquals(
            Json.parseToJsonElement(
                """
                    [
                        {
                            "type": "record",
                            "name": "Customer",
                            "namespace":"io.github.bbasinsk.schema.avro",
                            "fields": [
                                { "name": "id", "type": "int" },
                                { "name": "email", "type": ["null", "string"], "default": null }
                            ]
                        },
                        {
                            "type": "record",
                            "name": "Employee",
                            "namespace":"io.github.bbasinsk.schema.avro",
                            "fields": [
                                { "name": "id", "type": "int" },
                                { "name": "role", "type": { "type": "enum", "name": "Role", "symbols": [ "DEVELOPER", "MANAGER" ] } }
                            ]
                        }
                    ]
                """.trimIndent()
            ),
            Json.parseToJsonElement(
                Schema.person().toAvroSchema().toString()
            )
        )
    }

    @Test
    fun `stringMap schema`() {
        assertEquals(
            Json.parseToJsonElement(
                """
                    {
                        "type": "map",
                        "values": {
                            "type": "record",
                            "name": "RecordSmall",
                            "namespace":"io.github.bbasinsk.schema.avro",
                            "fields": [
                                { "name": "a", "type": "long" },
                                { "name": "b", "type": "string" }
                            ]
                        }
                    }
                """.trimIndent()
            ),
            Json.parseToJsonElement(
                Schema.recordMap().toAvroSchema().toString()
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


data class RecordFlipped(
    val b: String,
    val a: Long
)

fun Schema.Companion.recordSmall(): Schema<RecordSmall> =
    record(
        field("a", long()) { it.a },
        field("b", string()) { it.b },
        ::RecordSmall
    )

fun Schema.Companion.recordFlipped(): Schema<RecordFlipped> =
    record(
        field("b", string()) { it.b },
        field("a", long()) { it.a },
        ::RecordFlipped
    )

sealed interface Person

data class Customer(
    val id: Int,
    val email: String?
) : Person

data class Employee(
    val id: Int,
    val role: Role
) : Person

enum class Role {
    DEVELOPER,
    MANAGER
}

fun Schema.Companion.customer(): Schema<Customer> =
    record(
        field("id", int()) { it.id },
        field("email", string().optional()) { it.email },
        ::Customer
    )

fun Schema.Companion.employee(): Schema<Employee> =
    record(
        field("id", int()) { it.id },
        field("role", enumeration()) { it.role },
        ::Employee
    )

fun Schema.Companion.person(): Schema<Person> =
    union(
        case(customer()),
        case(employee())
    )