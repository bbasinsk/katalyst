# Katalyst OpenAPI Gradle Plugin

Generate OpenAPI specifications from your Katalyst HTTP endpoints at build time.

## Features

- 🔍 **Auto-discovery**: Automatically discovers all `HttpEndpointGroup` objects in your compiled classpath
- 🏗️ **Build-time generation**: Generates OpenAPI spec during build, no runtime overhead
- 📦 **Zero configuration**: Just define your endpoint groups and configure metadata
- 🎯 **Type-safe**: Uses your existing Katalyst HTTP definitions

## Installation

Add the plugin to your `build.gradle.kts`:

```kotlin
plugins {
    id("io.github.bbasinsk.http-openapi") version "..."
}
```

## Configuration

Configure the OpenAPI spec generation:

```kotlin
openApi {
    info.set(Info(
        title = "My API",
        version = "1.0.0",
        description = "API description"
    ))

    servers.set(listOf(
        Server(url = "https://api.example.com"),
        Server(url = "http://localhost:8080")
    ))

    // Optional: customize output location (default: build/generated/openapi/openapi.json)
    outputFile.set(file("src/main/resources/openapi.json"))
}
```

## How It Works

1. **Define endpoint groups** using `HttpEndpointGroup`:

```kotlin
object PersonEndpoints : HttpEndpointGroup("Person") {
    val findById = http {
        get { Root / "person" / param("id") { string() } }
            .output { status(Ok) { json { person() } } }
    }

    val create = http {
        post { Root / "person" }
            .input { json { person() } }
            .output { status(Created) { json { person() } } }
    }
}
```

2. **Run the build**:

```bash
./gradlew generateOpenApi
```

The plugin will:
- Scan your compiled classes for `HttpEndpointGroup` objects
- Collect all HTTP endpoint definitions from `.apis`
- Generate an OpenAPI 3.0 spec
- Write it to the configured output file

## Generated Spec

The plugin generates a complete OpenAPI 3.0 specification including:
- All paths and operations
- Request/response schemas
- Parameters (path, query, header)
- Examples and descriptions
- Tags from endpoint groups

## Build Integration

The plugin automatically runs during `./gradlew build`. You can also run it standalone:

```bash
./gradlew generateOpenApi
```
