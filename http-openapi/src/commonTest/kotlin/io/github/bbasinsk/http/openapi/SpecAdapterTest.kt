package io.github.bbasinsk.http.openapi

import io.github.bbasinsk.http.Http
import io.github.bbasinsk.http.ResponseSchema
import io.github.bbasinsk.http.ResponseStatus
import io.github.bbasinsk.http.ResponseStatus.Companion.Ok
import io.github.bbasinsk.schema.Schema
import kotlinx.serialization.encodeToString
import kotlin.test.Test
import kotlin.test.assertEquals

class SpecAdapterTest {

    val info = Info(title = "API", version = "1.0.0")

    @Test
    fun `should convert to OpenApiSpec`() {
        val http = Http.get { Root / "test" }
            .input(Schema.string())
            .output(ResponseSchema.status(ResponseStatus.Ok, Schema.string()))

        val result = listOf(http).toOpenApiSpec(info)

        val json = OpenApiJson.encodeToString(result)

        val expected = """
            {
                "openapi": "3.0.0",
                "info": {
                    "version": "1.0.0",
                    "title": "API"
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
                                },
                                "204": {
                                    "description": "Empty",
                                    "content": {}
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
            .input(personSchema)
            .output(ResponseSchema.status(Ok, personSchema))

        val result = listOf(http).toOpenApiSpec(info)

        val json = OpenApiJson.encodeToString(result)

        val expected = """
            {
                "openapi": "3.0.0",
                "info": {
                    "version": "1.0.0",
                    "title": "API"
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
                                },
                                "204": {
                                    "description": "Empty",
                                    "content": {}
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
            .input(Schema.string())
            .output(ResponseSchema.status(Ok, Schema.list(personSchema)))

        val result = listOf(http).toOpenApiSpec(info)

        val json = OpenApiJson.encodeToString(result).also {
            println("BLAH")
            println(it)
        }

        val expected = """
            {
                "openapi": "3.0.0",
                "info": {
                    "version": "1.0.0",
                    "title": "API"
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
                                },
                                "204": {
                                    "description": "Empty",
                                    "content": {}
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
}
