package io.github.bbasinsk.http.openapi

import io.github.bbasinsk.http.Http
import io.github.bbasinsk.schema.Schema
import kotlinx.serialization.encodeToString
import kotlin.test.Test
import kotlin.test.assertEquals

class SpecAdapterTest {

    val info = Info(title = "API", version = "1.0.0")

    @Test
    fun `should convert to OpenApiSpec`() {
        val http = Http.get { Root / "test" }
            .input { schema { string() } }
            .output { status(Ok) { string() } }

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
                            },
                            "deprecated": false
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
            .input { schema { personSchema } }
            .output { status(Ok) { personSchema } }

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
                            },
                            "deprecated": false
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
    fun `should return refs for list records`() {
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
            .input { schema { string() } }
            .output { status(Ok) { list(personSchema) } }

        val result = listOf(http).toOpenApiSpec(info)

        val json = OpenApiJson.encodeToString(result).also {
            println("BLAH")
            println(it)
        }

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
                            },
                            "deprecated": false
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
                schema { Person.schema }
                    .example("example A", Person(123, "John"))
                    .example("example B", Person(456, "Jane"))
                    .deprecated("some reason")
                    .description("a person")
            }
            .output {
                status(Ok) { int() }
                    .example("output 1", 1)
                    .example("output 2", 2)
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
                                            "${'$'}ref": "#/components/schemas/io.github.bbasinsk.http.openapi.Person"
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
                            },
                            "deprecated": false
                        }
                    }
                },
                "components": {
                    "schemas": {
                        "io.github.bbasinsk.http.openapi.Person": {
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
}


data class Person(
    val id: Int,
    val name: String
) {
    companion object {
        val schema: Schema<Person> = with(Schema.Companion) {
            record(
                field(int(), "id") { id },
                field(string(), "name") { name },
                ::Person
            )
        }
    }
}
