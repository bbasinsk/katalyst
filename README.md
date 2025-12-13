# Katalyst

Katalyst is a collection of powerful Kotlin libraries designed to accelerate development with type-safe, declarative APIs. Built for Kotlin Multiplatform, these libraries provide a unified approach to schema definitions, validation, HTTP service creation, and serialization across multiple formats.

## Core Philosophy

Katalyst focuses on:
- **Type safety**: Leverage Kotlin's type system for compile-time guarantees
- **Schema-first design**: Define your data structures once, use them everywhere
- **Multiplatform support**: Build for JVM, Native, and more
- **Declarative APIs**: Write expressive, concise code

## Modules

### Core Modules

- **schema**: Define data structures as values, enabling code generation, validation, and serialization
- **validation**: Functional data validation with error accumulation
- **tuple**: Utilities for working with tuple-like structures

### Schema Format Modules

- **schema-json**: JSON serialization/deserialization
- **schema-json-kotlinx**: Integration with kotlinx.serialization for JSON
- **schema-avro**: Apache Avro binary format support
- **schema-hocon**: HOCON configuration format support
- **schema-kotlinx-datetime**: Date/time type support for kotlinx.datetime
- **schema-json-schema**: JSONSchema generation from schemas

### HTTP Modules

- **http**: Core HTTP abstractions
- **http-ktor-2**: Ktor 2.x integration
- **http-ktor-3**: Ktor 3.x integration
- **http-openapi**: OpenAPI specification generation

## Getting Started

### Installation

Add the required dependencies to your `build.gradle.kts`:

```kotlin
// For schema definitions
implementation("io.github.bbasinsk:schema:{{version}}")

// For validation
implementation("io.github.bbasinsk:validation:{{version}}")

// For HTTP services with Ktor 3
implementation("io.github.bbasinsk:http:{{version}}")
implementation("io.github.bbasinsk:http-ktor-3:{{version}}")
```

### Basic Schema Definition

```kotlin
// Define a simple data class
data class Person(val name: String, val age: Int)

// Define a schema for the Person class
val personSchema = with(Schema) {
    record(
        field(string(), "name") { name },
        field(int(), "age") { age },
        ::Person
    )
}
```

### Creating an HTTP Endpoint

```kotlin
// Define an API endpoint
val getPerson = Http.get { Root / "person" / param("id") { int() } }
    .output { status(Ok) { json { personSchema } } }
    .error { status(NotFound) { json { string() } } }

fun main() {
    embeddedServer(CIO, port = 8080) {
        endpoints {
            handle(getPerson) { req ->
                val (id) = tupleValues(req.params)
                if (id > 0) {
                    success(Person("John Doe", 42))
                } else {
                    error("Person not found")
                }
            }
        }
    }.start(wait = true)
}
```

### Streaming with Server-Sent Events (SSE)

Katalyst supports real-time streaming via SSE:

```kotlin
val streamApi = Http.get { Root / "events" }
    .output { streaming { json { messageSchema } } }

// In your server setup:
handle(streamApi) {
    Response.streamingSuccessData(flow {
        repeat(10) { i ->
            emit(Message("Event $i", System.currentTimeMillis()))
            delay(1000)
        }
    })
}
```

See [http/readme.md](http/readme.md) for detailed SSE documentation including keepalive, error handling, and best practices.

### Validation

```kotlin
fun Validation.Companion.validatePerson(name: String, age: Int): Validation<String, Person> =
    build(
        nonEmpty(name) { "Name must not be empty" },
        nonNegative(age) { "Age must be non-negative" },
        ::Person
    )

// Helper validators
fun <E> Validation.Companion.nonEmpty(value: String, error: () -> E): Validation<E, String> =
    if (value.isNotBlank()) Validation.valid(value) else Validation.invalid(error())

fun <E> Validation.Companion.nonNegative(value: Int, error: () -> E): Validation<E, Int> =
    if (value >= 0) Validation.valid(value) else Validation.invalid(error())
```

## Features

### Schema Definitions

Katalyst's schema system enables:
- Defining data structures as values
- Generating serializers/deserializers
- Creating OpenAPI documentation
- Validating inputs/outputs

Supported types include:
- Primitives: String, Int, Long, Float, Double, Boolean, ByteArray
- Collections: List, Map
- Records (objects with fields)
- Unions (oneOf)
- Optional values
- Default values
- Custom types via transformations

### HTTP APIs

The HTTP modules provide:
- Type-safe endpoint definitions
- Automatic request/response parsing
- OpenAPI documentation generation
- Built-in validation
- Framework integration (Ktor 2.x and 3.x)

### Serialization Formats

Katalyst supports multiple serialization formats:
- JSON (with kotlinx.serialization)
- Avro binary format
- HOCON configuration
- JSONSchema

### AI Generation

Katalyst can also generate response schemas for AI models such as OpenAI, Claude, Gemini, etc. 

**Gemini Example:**
```kotlin
@Serializable
data class GeminiGenerationConfig(
    val temperature: Double? = null,
    val responseMimeType: String? = null,
    val responseSchema: JsonElement? = null,
    // other fields...
)

// See above for the personSchema definition
val personResponseSchema = OpenApiJson.encodeToJsonElement(
    personSchema.toSchemaObject(OutputOptions.gemini)
)

val generationConfig = GeminiGenerationConfig(
    responseMimeType = "application/json",
    responseSchema = personResponseSchema
)
```

## Examples

Check out the full examples in the codebase:
- [HTTP Example](http-ktor-3/src/jvmMain/kotlin/io/github/bbasinsk/http/ktor3/Example.kt)
- [Gemini Example](http-openapi/src/commonTest/kotlin/io/github/bbasinsk/http/openapi/GoogleSchemaTest.kt)

## TODO

### High Priority
- [ ] JSON settings
- [ ] More documentation
- [ ] Get gradle `api` dependencies work with published packages

### Lower Priority
- [ ] Kafka Serde
- [ ] CSV Reader/Writer
- [ ] Kotest integration
- [ ] Better Hocon parsing errors

## License
Katalyst is released under the [Apache License 2.0](LICENSE).

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.
