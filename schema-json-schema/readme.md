# Schema: Json-Schema

Convert a `Schema` to JSON Schema with `toJsonSchema()`.

```kotlin
import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.jsonschema.toJsonSchema

val schema = Schema.string()
    .format("duration")
    .description("ISO 8601 duration")
    .optional()
    .toJsonSchema()
```

This schema emits `type: ["string", "null"]`, `format: "duration"`, and the description.
Format and description annotations compose in either order and survive transforms and optional wrappers.
The built-in `io.github.bbasinsk.schema.kotlin.duration()` codec includes `format: "duration"`.
For `orElse`, annotations on the combined schema remain beside `anyOf`. Branch annotations remain on their branches.

Format metadata does not change parsing, encoding, or validation.
Consumer support depends on the JSON Schema dialect and validator.
The OpenAPI converter does not emit custom format metadata.
