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
When annotations repeat on the same schema, the last call wins, including overrides of built-in formats.
Format annotations apply to the annotated node, including composite and unconstrained schemas.
Collection descriptions, formats, and nullability do not apply to items. Annotate the item schema separately.
Annotated record and union references keep annotations on an `anyOf` wrapper around `$ref`, not in shared `$defs`.
Definitions do not inherit a reference's nullability. Optional references use `anyOf` with a null branch, independent of field order.
The built-in Kotlin `duration()`, `instant()`, and `uuid()` codecs include `duration`, `date-time`, and `uuid` formats, respectively.
For `orElse`, annotations on the combined schema remain beside `anyOf`. Branch annotations remain on their branches.

Format metadata does not change parsing, encoding, or validation.
Consumer support depends on the JSON Schema dialect and validator.
The OpenAPI converter does not emit custom format metadata.
