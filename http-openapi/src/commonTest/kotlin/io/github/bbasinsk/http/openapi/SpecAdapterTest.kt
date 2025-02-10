package io.github.bbasinsk.http.openapi

import io.github.bbasinsk.http.Http
import io.github.bbasinsk.schema.Schema
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.encodeToJsonElement
import kotlin.test.Test
import kotlin.test.assertEquals

class SpecAdapterTest {

    private val info = Info(title = "API", version = "1.0.0")

    @Test
    fun `should convert to OpenApiSpec`() {
        val http = Http.get { Root / "test" }
            .input { json { string() } }
            .output { status(Ok) { json { string() } } }

        val result = listOf(http).toOpenApiSpec(info)

        val json = OpenApiJson.encodeToString(result)

        val expected = """
            {
                "openapi": "3.0.0",
                "info": {
                    "title": "API",
                    "version": "1.0.0"
                },
                "servers": [],
                "paths": {
                    "/test": {
                        "get": {
                            "parameters": [],
                            "requestBody": {
                                "required": true,
                                "content": {
                                    "text/plain": {
                                        "schema": {
                                            "type": "string"
                                        }
                                    }
                                }
                            },
                            "responses": {
                                "200": {
                                    "description": "OK",
                                    "content": {
                                        "text/plain": {
                                            "schema": {
                                                "type": "string"
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                },
                "components": {
                    "schemas": {}
                }
            }
        """.trimIndent()

        assertEquals(expected, json)
    }

    @Test
    fun `should return refs for records`() {
        data class Person(
            val name: String,
            val age: Int
        )

        val personSchema = with(Schema.Companion) {
            record(
                field(string(), "name") { name },
                field(int(), "age") { age },
                ::Person
            )
        }
        val http = Http.get { Root / "test" }
            .input { json { personSchema } }
            .output { status(Ok) { json { personSchema } } }

        val result = listOf(http).toOpenApiSpec(info)

        val json = OpenApiJson.encodeToString(result)

        val expected = """
            {
                "openapi": "3.0.0",
                "info": {
                    "title": "API",
                    "version": "1.0.0"
                },
                "servers": [],
                "paths": {
                    "/test": {
                        "get": {
                            "parameters": [],
                            "requestBody": {
                                "required": true,
                                "content": {
                                    "application/json": {
                                        "schema": {
                                            "${'$'}ref": "#/components/schemas/Person"
                                        }
                                    }
                                }
                            },
                            "responses": {
                                "200": {
                                    "description": "OK",
                                    "content": {
                                        "application/json": {
                                            "schema": {
                                                "${'$'}ref": "#/components/schemas/Person"
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                },
                "components": {
                    "schemas": {
                        "Person": {
                            "type": "object",
                            "properties": {
                                "name": {
                                    "type": "string"
                                },
                                "age": {
                                    "type": "integer",
                                    "format": "int32"
                                }
                            },
                            "required": [
                                "name",
                                "age"
                            ]
                        }
                    }
                }
            }
        """.trimIndent()

        assertEquals(expected, json)
    }

    @Test
    fun `should return refs for list records output`() {
        data class Person(
            val name: String,
            val age: Int
        )

        val personSchema = with(Schema.Companion) {
            record(
                field(string(), "name") { name },
                field(int(), "age") { age },
                ::Person
            )
        }

        val http = Http.get { Root / "test" }
            .input { json { string() } }
            .output { status(Ok) { json { list(personSchema) } } }

        val result = listOf(http).toOpenApiSpec(info)

        val json = OpenApiJson.encodeToString(result)

        val expected = """
            {
                "openapi": "3.0.0",
                "info": {
                    "title": "API",
                    "version": "1.0.0"
                },
                "servers": [],
                "paths": {
                    "/test": {
                        "get": {
                            "parameters": [],
                            "requestBody": {
                                "required": true,
                                "content": {
                                    "text/plain": {
                                        "schema": {
                                            "type": "string"
                                        }
                                    }
                                }
                            },
                            "responses": {
                                "200": {
                                    "description": "OK",
                                    "content": {
                                        "application/json": {
                                            "schema": {
                                                "type": "array",
                                                "items": {
                                                    "${'$'}ref": "#/components/schemas/Person"
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                },
                "components": {
                    "schemas": {
                        "Person": {
                            "type": "object",
                            "properties": {
                                "name": {
                                    "type": "string"
                                },
                                "age": {
                                    "type": "integer",
                                    "format": "int32"
                                }
                            },
                            "required": [
                                "name",
                                "age"
                            ]
                        }
                    }
                }
            }
        """.trimIndent()

        assertEquals(expected, json)
    }

    @Test
    fun `should return refs for list records input`() {
        data class Person(
            val name: String,
            val age: Int
        )

        val personSchema = with(Schema.Companion) {
            record(
                field(string(), "name") { name },
                field(int(), "age") { age },
                ::Person
            )
        }

        val http = Http.get { Root / "test" }
            .input { json { list(personSchema)  } }
            .output { status(Ok) { plain { string() } } }

        val result = listOf(http).toOpenApiSpec(info)

        val json = OpenApiJson.encodeToString(result)

        val expected = """
            {
                "openapi": "3.0.0",
                "info": {
                    "title": "API",
                    "version": "1.0.0"
                },
                "servers": [],
                "paths": {
                    "/test": {
                        "get": {
                            "parameters": [],
                            "requestBody": {
                                "required": true,
                                "content": {
                                    "application/json": {
                                        "schema": {
                                            "type": "array",
                                            "items": {
                                                "${'$'}ref": "#/components/schemas/Person"
                                            }
                                        }
                                    }
                                }
                            },
                            "responses": {
                                "200": {
                                    "description": "OK",
                                    "content": {
                                        "text/plain": {
                                            "schema": {
                                                "type": "string"
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                },
                "components": {
                    "schemas": {
                        "Person": {
                            "type": "object",
                            "properties": {
                                "name": {
                                    "type": "string"
                                },
                                "age": {
                                    "type": "integer",
                                    "format": "int32"
                                }
                            },
                            "required": [
                                "name",
                                "age"
                            ]
                        }
                    }
                }
            }
        """.trimIndent()

        assertEquals(expected, json)
    }

    @Test
    fun `should show examples`() {
        val http = Http.get { Root / "examples-test" }
            .input {
                json { Customer.schema }
                    .example("example A", Customer(123, "John"))
                    .example("example B", Customer(456, "Jane"))
                    .deprecated("some reason")
                    .description("a person")
            }
            .output {
                status(Ok) {
                    json { int() }
                        .example("output 1", 1)
                        .example("output 2", 2)
                }
            }

        val result = listOf(http).toOpenApiSpec(info)

        val json = OpenApiJson.encodeToString(result)

        val expected = """
            {
                "openapi": "3.0.0",
                "info": {
                    "title": "API",
                    "version": "1.0.0"
                },
                "servers": [],
                "paths": {
                    "/examples-test": {
                        "get": {
                            "parameters": [],
                            "requestBody": {
                                "description": "a person",
                                "required": true,
                                "content": {
                                    "application/json": {
                                        "schema": {
                                            "${'$'}ref": "#/components/schemas/io.github.bbasinsk.http.openapi.Customer"
                                        },
                                        "examples": {
                                            "example A": {
                                                "summary": "example A",
                                                "value": {
                                                    "id": 123,
                                                    "name": "John"
                                                }
                                            },
                                            "example B": {
                                                "summary": "example B",
                                                "value": {
                                                    "id": 456,
                                                    "name": "Jane"
                                                }
                                            }
                                        }
                                    }
                                }
                            },
                            "responses": {
                                "200": {
                                    "description": "OK",
                                    "content": {
                                        "text/plain": {
                                            "schema": {
                                                "type": "integer",
                                                "format": "int32"
                                            },
                                            "examples": {
                                                "output 1": {
                                                    "summary": "output 1",
                                                    "value": 1
                                                },
                                                "output 2": {
                                                    "summary": "output 2",
                                                    "value": 2
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                },
                "components": {
                    "schemas": {
                        "io.github.bbasinsk.http.openapi.Customer": {
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
                    }
                }
            }
        """.trimIndent()

        assertEquals(expected, json)
    }

    @Test
    fun `should work for oneOf`() {
        val http = Http.get { Root / "examples-test" }
            .input { json { Human.schema } }
            .output { status(Ok) { json { int() } } }

        val result = listOf(http).toOpenApiSpec(info)

        val expected = """
            {
              "openapi": "3.0.0",
              "info": {
                "title": "API",
                "version": "1.0.0"
              },
              "servers": [],
              "paths": {
                "/examples-test": {
                  "get": {
                    "parameters": [],
                    "requestBody": {
                      "required": true,
                      "content": {
                        "application/json": {
                          "schema": {
                            "${'$'}ref": "#/components/schemas/io.github.bbasinsk.http.openapi.Human"
                          }
                        }
                      }
                    },
                    "responses": {
                      "200": {
                        "description": "OK",
                        "content": {
                          "text/plain": {
                            "schema": {
                              "type": "integer",
                              "format": "int32"
                            }
                          }
                        }
                      }
                    }
                  }
                }
              },
              "components": {
                "schemas": {
                  "io.github.bbasinsk.http.openapi.Human": {
                    "oneOf": [
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
                          }
                        },
                        "required": [
                          "id",
                          "role"
                        ]
                      }
                    ],
                    "discriminator":{
                        "propertyName":"type",
                        "mapping":{
                            "Customer":"#/components/schemas/io.github.bbasinsk.http.openapi.Customer",
                            "Employee":"#/components/schemas/io.github.bbasinsk.http.openapi.Employee"
                        }
                    }
                  }
                }
              }
            }
        """.trimIndent()

        assertEquals(
            OpenApiJson.parseToJsonElement(expected),
            OpenApiJson.encodeToJsonElement(result)
        )
    }
}

sealed interface Human {
    companion object {
        val schema: Schema<Human> = with(Schema.Companion) {
            union(
                case(Customer.schema),
                case(Employee.schema)
            )
        }
    }
}

data class Customer(
    val id: Int,
    val name: String
) : Human {
    companion object {
        val schema: Schema<Customer> = with(Schema.Companion) {
            record(
                field(int(), "id") { id },
                field(string(), "name") { name },
                ::Customer
            )
        }
    }
}

enum class Role {
    Admin,
    User
}

data class Employee(
    val id: Int,
    val role: Role
) : Human {
    companion object {
        val schema: Schema<Employee> = with(Schema.Companion) {
            record(
                field(int(), "id") { id },
                field(enumeration(), "role") { role },
                ::Employee
            )
        }
    }
}

