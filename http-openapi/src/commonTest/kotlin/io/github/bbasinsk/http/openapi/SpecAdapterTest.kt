package io.github.bbasinsk.http.openapi

import io.github.bbasinsk.http.ContentType
import io.github.bbasinsk.http.Http
import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.transform
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
            .input { json { list(personSchema) } }
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
                        "${'$'}ref": "#/components/schemas/io.github.bbasinsk.http.openapi.Human.CustomerWithDiscriminator"
                      },
                      {
                        "${'$'}ref": "#/components/schemas/io.github.bbasinsk.http.openapi.Human.EmployeeWithDiscriminator"
                      }
                    ],
                    "discriminator": {
                      "propertyName": "type",
                      "mapping": {
                        "Customer": "#/components/schemas/io.github.bbasinsk.http.openapi.Human.CustomerWithDiscriminator",
                        "Employee": "#/components/schemas/io.github.bbasinsk.http.openapi.Human.EmployeeWithDiscriminator"
                      }
                    }
                  },
                  "io.github.bbasinsk.http.openapi.Human.Customer": {
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
                  "io.github.bbasinsk.http.openapi.Human.CustomerWithDiscriminator": {
                    "allOf": [
                      {
                        "type": "object",
                        "properties": {
                          "type": {
                            "type": "string",
                            "enum": [
                              "Customer"
                            ]
                          }
                        },
                        "required": [
                          "type"
                        ]
                      },
                      {
                        "${'$'}ref": "#/components/schemas/io.github.bbasinsk.http.openapi.Human.Customer"
                      }
                    ]
                  },
                  "io.github.bbasinsk.http.openapi.Human.Employee": {
                    "type": "object",
                    "properties": {
                      "id": {
                        "type": "integer",
                        "format": "int32"
                      },
                      "role": {
                        "type": "string",
                        "format": "enum",
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
                  },
                  "io.github.bbasinsk.http.openapi.Human.EmployeeWithDiscriminator": {
                    "allOf": [
                      {
                        "type": "object",
                        "properties": {
                          "type": {
                            "type": "string",
                            "enum": [
                              "Employee"
                            ]
                          }
                        },
                        "required": [
                          "type"
                        ]
                      },
                      {
                        "${'$'}ref": "#/components/schemas/io.github.bbasinsk.http.openapi.Human.Employee"
                      }
                    ]
                  },
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
                  },
                  "io.github.bbasinsk.http.openapi.Employee": {
                    "type": "object",
                    "properties": {
                      "id": {
                        "type": "integer",
                        "format": "int32"
                      },
                      "role": {
                        "type": "string",
                        "format": "enum",
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
                }
              }
            }
        """.trimIndent()

        assertEquals(
            OpenApiJson.parseToJsonElement(expected),
            OpenApiJson.encodeToJsonElement(result)
        )
    }

    @Test
    fun `should encode type for nested oneOf`() {
        val http = Http.get { Root / "nested-oneof" }
            .input { json { Wrapper.schema } }
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
                "/nested-oneof": {
                  "get": {
                    "parameters": [],
                    "requestBody": {
                      "required": true,
                      "content": {
                        "application/json": {
                          "schema": {
                            "${'$'}ref": "#/components/schemas/io.github.bbasinsk.http.openapi.Wrapper"
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
                  "io.github.bbasinsk.http.openapi.Wrapper": {
                    "type": "object",
                    "properties": {
                      "human": {
                        "${'$'}ref": "#/components/schemas/io.github.bbasinsk.http.openapi.Human",
                        "nullable": true
                      }
                    },
                    "required": []
                  },
                  "io.github.bbasinsk.http.openapi.Human": {
                    "oneOf": [
                      {
                        "${'$'}ref": "#/components/schemas/io.github.bbasinsk.http.openapi.Human.CustomerWithDiscriminator"
                      },
                      {
                        "${'$'}ref": "#/components/schemas/io.github.bbasinsk.http.openapi.Human.EmployeeWithDiscriminator"
                      }
                    ],
                    "discriminator":{
                        "propertyName":"type",
                        "mapping":{
                            "Customer":"#/components/schemas/io.github.bbasinsk.http.openapi.Human.CustomerWithDiscriminator",
                            "Employee":"#/components/schemas/io.github.bbasinsk.http.openapi.Human.EmployeeWithDiscriminator"
                        }
                    }
                  },
                  "io.github.bbasinsk.http.openapi.Human.CustomerWithDiscriminator": {
                    "allOf": [
                      {
                        "type": "object",
                        "properties": {
                          "type": {
                            "type": "string",
                            "enum": ["Customer"]
                          }
                        },
                        "required": ["type"]
                      },
                      {
                        "${'$'}ref": "#/components/schemas/io.github.bbasinsk.http.openapi.Human.Customer"
                      }
                    ]
                  },
                  "io.github.bbasinsk.http.openapi.Human.EmployeeWithDiscriminator": {
                    "allOf": [
                      {
                        "type": "object",
                        "properties": {
                          "type": {
                            "type": "string",
                            "enum": ["Employee"]
                          }
                        },
                        "required": ["type"]
                      },
                      {
                        "${'$'}ref": "#/components/schemas/io.github.bbasinsk.http.openapi.Human.Employee"
                      }
                    ]
                  },
                  "io.github.bbasinsk.http.openapi.Human.Customer": {
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
                  "io.github.bbasinsk.http.openapi.Human.Employee": {
                    "type": "object",
                    "properties": {
                      "id": {
                        "type": "integer",
                        "format": "int32"
                      },
                      "role": {
                        "type": "string",
                        "format": "enum",
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
                  },
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
                  },
                  "io.github.bbasinsk.http.openapi.Employee": {
                    "type": "object",
                    "properties": {
                      "id": {
                        "type": "integer",
                        "format": "int32"
                      },
                      "role": {
                        "type": "string",
                        "format": "enum",
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
                }
              }
            }
        """.trimIndent()

        assertEquals(
            OpenApiJson.parseToJsonElement(expected),
            OpenApiJson.encodeToJsonElement(result)
        )
    }

    @Test
    fun `it uses refs for nested records`() {
        val http = Http.get { Root / "nested-test" }
            .input { json { Business.schema } }
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
                "/nested-test": {
                  "get": {
                    "parameters": [],
                    "requestBody": {
                      "required": true,
                      "content": {
                        "application/json": {
                          "schema": {
                            "${'$'}ref": "#/components/schemas/io.github.bbasinsk.http.openapi.Business"
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
                  "io.github.bbasinsk.http.openapi.Business": {
                    "type": "object",
                    "properties": {
                      "employees": {
                        "type": "array",
                        "items": {
                          "${'$'}ref": "#/components/schemas/io.github.bbasinsk.http.openapi.Employee"
                        }
                      },
                      "customers": {
                        "type": "array",
                        "items": {
                          "${'$'}ref": "#/components/schemas/io.github.bbasinsk.http.openapi.Customer"
                        }
                      }
                    },
                    "required": [
                      "employees",
                      "customers"
                    ]
                  },
                  "io.github.bbasinsk.http.openapi.Employee": {
                    "type": "object",
                    "properties": {
                      "id": {
                        "type": "integer",
                        "format": "int32"
                      },
                      "role": {
                        "type": "string",
                        "format": "enum",
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
                  },
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

        assertEquals(
            OpenApiJson.parseToJsonElement(expected),
            OpenApiJson.encodeToJsonElement(result)
        )
    }

    @Test
    fun `handles plain image request body`() {
        val http = Http.post { Root / "image-test" }
            .input { bytes(ContentType.Image.Jpeg) }
            .output { status(Ok) { json { string() } } }

        val result = listOf(http).toOpenApiSpec(info)

        val json = OpenApiJson.encodeToJsonElement(result)

        val expected = OpenApiJson.parseToJsonElement(
            """
            {
                "openapi": "3.0.0",
                "info": {
                    "title": "API",
                    "version": "1.0.0"
                },
                "servers": [],
                "paths": {
                    "/image-test": {
                        "post": {
                            "parameters": [],
                            "requestBody": {
                                "required": true,
                                "content": {
                                    "image/jpeg": {
                                        "schema": {
                                            "type": "string",
                                            "format": "binary"
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
                    }
                }
            }
            """.trimIndent()
        )

        assertEquals(expected, json)
    }

    @Test
    fun `handles multipart request body`() {
        data class MyByteArray(val bytes: List<Byte>)
        data class MultipartData(val fileName: String, val contents: MyByteArray)

        fun Schema.Companion.myByteArray(): Schema<MyByteArray> =
            byteArray().transform(
                decode = { MyByteArray(it.toList()) },
                encode = { it.bytes.toByteArray() }
            )

        fun Schema.Companion.multipartData(): Schema<MultipartData> = with(Schema.Companion) {
            record(
                field(string(), "fileName") { fileName },
                field(myByteArray(), "contents") { contents },
                ::MultipartData
            )
        }

        val http = Http.post { Root / "multipart-test" }
            .input { multipart { multipartData() } }
            .output { status(Ok) { json { string() } } }

        val result = listOf(http).toOpenApiSpec(info)

        val json = OpenApiJson.encodeToJsonElement(result)

        val expected = OpenApiJson.parseToJsonElement(
            """
            {
                "openapi": "3.0.0",
                "info": {
                    "title": "API",
                    "version": "1.0.0"
                },
                "servers": [],
                "paths": {
                    "/multipart-test": {
                        "post": {
                            "parameters": [],
                            "requestBody": {
                                "required": true,
                                "content": {
                                    "multipart/form-data": {
                                        "schema": {
                                            "${'$'}ref":"#/components/schemas/MultipartData"
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
                        "MultipartData": { 
                            "type": "object",
                            "properties": {
                                "fileName": {
                                    "type": "string"
                                },
                                "contents": {
                                    "type": "string",
                                    "format": "binary"
                                }
                            },
                            "required": [
                                "fileName",
                                "contents"
                            ]
                        }
                    }
                }
            }
            """.trimIndent()
        )

        assertEquals(expected, json)
    }

    @Test
    fun `handles multipart list request body`() {
        data class MyByteArray(val bytes: List<Byte>)
        data class MultipartData(val images: List<MyByteArray>)

        fun Schema.Companion.myByteArray(): Schema<MyByteArray> =
            byteArray().transform(
                decode = { MyByteArray(it.toList()) },
                encode = { it.bytes.toByteArray() }
            )

        fun Schema.Companion.multipartData(): Schema<MultipartData> = with(Schema.Companion) {
            record(
                field(list(myByteArray()), "images") { images },
                ::MultipartData
            )
        }

        val http = Http.post { Root / "multipart-list" }
            .input { multipart { multipartData() } }
            .output { status(Ok) { json { string() } } }

        val result = listOf(http).toOpenApiSpec(info)

        val json = OpenApiJson.encodeToJsonElement(result)

        val expected = OpenApiJson.parseToJsonElement(
            """
            {
                "openapi": "3.0.0",
                "info": {
                    "title": "API",
                    "version": "1.0.0"
                },
                "servers": [],
                "paths": {
                    "/multipart-list": {
                        "post": {
                            "parameters": [],
                            "requestBody": {
                                "required": true,
                                "content": {
                                    "multipart/form-data": {
                                        "schema": {
                                            "${'$'}ref":"#/components/schemas/MultipartData"
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
                        "MultipartData": {
                            "type": "object",
                            "properties": {
                                "images": {
                                    "type": "array",
                                    "items": {
                                        "type": "string",
                                        "format": "binary"
                                    }
                                }
                            },
                            "required": [
                                "images"
                            ]
                        }
                    }
                }
            }
            """.trimIndent()
        )

        assertEquals(expected, json)
    }

    @Test
    fun `handles mix of json and bytes`() {
        data class MyByteArray(val bytes: List<Byte>)

        fun Schema.Companion.myByteArray(): Schema<MyByteArray> =
            byteArray().transform(
                decode = { MyByteArray(it.toList()) },
                encode = { it.bytes.toByteArray() }
            )

        data class MixedData(
            val json: Customer,
            val image: MyByteArray
        )

        fun Schema.Companion.mixedData(): Schema<MixedData> = with(Schema.Companion) {
            record(
                field(Customer.schema, "json") { json },
                field(myByteArray(), "image") { image },
                ::MixedData
            )
        }

        val http = Http.post { Root / "multi-part" / "mixed-test" }
            .input { multipart { mixedData() } }
            .output { status(Ok) { json { string() } } }

        val result = listOf(http).toOpenApiSpec(info)

        val json = OpenApiJson.encodeToJsonElement(result)

        val expected = OpenApiJson.parseToJsonElement(
            """
            {
                "openapi": "3.0.0",
                "info": {
                    "title": "API",
                    "version": "1.0.0"
                },
                "servers": [],
                "paths": {
                    "/multi-part/mixed-test": {
                        "post": {
                            "parameters": [],
                            "requestBody": {
                                "required": true,
                                "content": {
                                    "multipart/form-data": {
                                        "schema": {
                                            "${'$'}ref":"#/components/schemas/MixedData"
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
                        "MixedData": {
                            "type": "object",
                            "properties": {
                                "json": {
                                    "${'$'}ref":"#/components/schemas/io.github.bbasinsk.http.openapi.Customer"
                                },
                                "image": {
                                    "type":"string",
                                    "format":"binary"
                                }
                            },
                            "required": [
                                "json",
                                "image"
                            ]
                        },
                        "io.github.bbasinsk.http.openapi.Customer":{
                            "type":"object",
                            "properties":{
                                "id":{
                                    "type":"integer",
                                    "format":"int32"
                                },
                                "name":{
                                    "type":"string"
                                }
                            },
                            "required":["id","name"]
                        }
                    }
                }
            }
            """.trimIndent()
        )

        assertEquals(expected, json)
    }
}

data class Wrapper(
    val human: Human?
) {
    companion object {
        val schema: Schema<Wrapper> = with(Schema.Companion) {
            record(
                field(Human.schema.optional(), "human") { human },
                ::Wrapper
            )
        }
    }
}

sealed interface Human {
    companion object {
        val schema: Schema<Human> = with(Schema.Companion) {
            union(
                case(Customer.schema.description("Customer"), "Customer"),
                case(Employee.schema.description("Employee"), "Employee"),
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

data class Business(
    val employees: List<Employee>,
    val customers: List<Customer>
) {
    companion object {
        val schema: Schema<Business> = with(Schema.Companion) {
            record(
                field(list(Employee.schema), "employees") { employees },
                field(list(Customer.schema), "customers") { customers },
                ::Business
            )
        }
    }
}

class SSESpecAdapterTest {

    private val info = Info(title = "API", version = "1.0.0")

    @Test
    fun `should generate OpenAPI for SSE streaming endpoint with simple schema`() {
        val http = Http.get { Root / "stream" }
            .output { streaming { json { string() } } }

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
                    "/stream": {
                        "get": {
                            "parameters": [],
                            "responses": {
                                "200": {
                                    "description": "Server-Sent Events stream",
                                    "content": {
                                        "text/event-stream": {
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
    fun `should generate OpenAPI for SSE streaming endpoint with record schema`() {
        data class Message(val text: String, val timestamp: Long)

        val messageSchema = with(Schema.Companion) {
            record(
                field(string(), "text") { text },
                field(long(), "timestamp") { timestamp },
                ::Message
            )
        }

        val http = Http.get { Root / "messages" }
            .output { streaming { json { messageSchema } } }

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
                    "/messages": {
                        "get": {
                            "parameters": [],
                            "responses": {
                                "200": {
                                    "description": "Server-Sent Events stream",
                                    "content": {
                                        "text/event-stream": {
                                            "schema": {
                                                "${'$'}ref": "#/components/schemas/Message"
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
                        "Message": {
                            "type": "object",
                            "properties": {
                                "text": {
                                    "type": "string"
                                },
                                "timestamp": {
                                    "type": "integer",
                                    "format": "int64"
                                }
                            },
                            "required": [
                                "text",
                                "timestamp"
                            ]
                        }
                    }
                }
            }
        """.trimIndent()

        assertEquals(expected, json)
    }

    @Test
    fun `should generate OpenAPI for SSE streaming endpoint with plain text`() {
        val http = Http.get { Root / "plain-stream" }
            .output { streaming { plain { string() } } }

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
                    "/plain-stream": {
                        "get": {
                            "parameters": [],
                            "responses": {
                                "200": {
                                    "description": "Server-Sent Events stream",
                                    "content": {
                                        "text/event-stream": {
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
    fun `should generate OpenAPI for SSE streaming endpoint with examples`() {
        data class Event(val type: String, val data: String)

        val eventSchema = with(Schema.Companion) {
            record(
                field(string(), "type") { type },
                field(string(), "data") { data },
                ::Event
            )
        }

        val http = Http.get { Root / "events" }
            .output {
                streaming {
                    json { eventSchema }
                        .example("greeting", Event("greeting", "Hello"))
                        .example("farewell", Event("farewell", "Goodbye"))
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
                    "/events": {
                        "get": {
                            "parameters": [],
                            "responses": {
                                "200": {
                                    "description": "Server-Sent Events stream",
                                    "content": {
                                        "text/event-stream": {
                                            "schema": {
                                                "${'$'}ref": "#/components/schemas/Event"
                                            },
                                            "examples": {
                                                "greeting": {
                                                    "summary": "greeting",
                                                    "value": {
                                                        "type": "greeting",
                                                        "data": "Hello"
                                                    }
                                                },
                                                "farewell": {
                                                    "summary": "farewell",
                                                    "value": {
                                                        "type": "farewell",
                                                        "data": "Goodbye"
                                                    }
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
                        "Event": {
                            "type": "object",
                            "properties": {
                                "type": {
                                    "type": "string"
                                },
                                "data": {
                                    "type": "string"
                                }
                            },
                            "required": [
                                "type",
                                "data"
                            ]
                        }
                    }
                }
            }
        """.trimIndent()

        assertEquals(expected, json)
    }

    @Test
    fun `should handle mixed regular and streaming endpoints`() {
        data class Message(val text: String)

        val messageSchema = with(Schema.Companion) {
            record(
                field(string(), "text") { text },
                ::Message
            )
        }

        val regularHttp = Http.get { Root / "regular" }
            .output { status(Ok) { json { messageSchema } } }

        val streamingHttp = Http.get { Root / "stream" }
            .output { streaming { json { messageSchema } } }

        val result = listOf(regularHttp, streamingHttp).toOpenApiSpec(info)

        val json = OpenApiJson.encodeToJsonElement(result)

        val expected = OpenApiJson.parseToJsonElement(
            """
            {
                "openapi": "3.0.0",
                "info": {
                    "title": "API",
                    "version": "1.0.0"
                },
                "servers": [],
                "paths": {
                    "/regular": {
                        "get": {
                            "parameters": [],
                            "responses": {
                                "200": {
                                    "description": "OK",
                                    "content": {
                                        "application/json": {
                                            "schema": {
                                                "${'$'}ref": "#/components/schemas/Message"
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    },
                    "/stream": {
                        "get": {
                            "parameters": [],
                            "responses": {
                                "200": {
                                    "description": "Server-Sent Events stream",
                                    "content": {
                                        "text/event-stream": {
                                            "schema": {
                                                "${'$'}ref": "#/components/schemas/Message"
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
                        "Message": {
                            "type": "object",
                            "properties": {
                                "text": {
                                    "type": "string"
                                }
                            },
                            "required": ["text"]
                        }
                    }
                }
            }
            """.trimIndent()
        )

        assertEquals(expected, json)
    }
}
