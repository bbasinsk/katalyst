# Source-based JSON Decoder

## Goal

Replace the kotlinx.serialization-dependent decoding path (`String → JsonElement → A`) with a decoder that uses `kotlinx.io.Source` directly (`Source → SchemaValue → A`), living entirely in the `schema-json` module. This mirrors the existing `SinkEncoder` and removes the need for `schema-json-kotlinx` for decoding.

## Architecture

Two independent components composed together:

```
Source ──→ SchemaValue ──→ Validation<InvalidJson, A>
         (JSON parser)     (Schema decoder)
```

### Component 1: JSON Parser (`SourceDecoding.kt`)

A stateful parser that reads UTF-8 bytes from `kotlinx.io.Source` and produces `SchemaValue`.

```kotlin
data class JsonDecodingConfig(
    val allowTrailingCommas: Boolean = false,
    val allowComments: Boolean = false,  // single-line // comments
)

fun decodeSchemaValueFromSource(source: Source, config: JsonDecodingConfig = JsonDecodingConfig()): SchemaValue
```

Tokenization by first byte:
- `"` → string (with escape handling including `\uXXXX`)
- `t`/`f` → boolean literal
- `n` → null literal
- `-`/`0-9` → number (`toLong()` first → `SchemaValue.Integer`, fallback `toDouble()` → `SchemaValue.Decimal`)
- `{` → object → `SchemaValue.Obj`
- `[` → array → `SchemaValue.Arr`
- `/` → comment (if `allowComments`, skip to EOL; otherwise error)

Throws `IllegalArgumentException` on malformed JSON (syntax errors are unrecoverable, not schema errors).

### Component 2: Schema Decoder (`SchemaValueDecoding.kt`)

Mirrors the existing `decodeFromJsonElement` in `schema-json-kotlinx` but operates on `SchemaValue`.

```kotlin
fun <A> Schema<A>.decodeFromSchemaValue(value: SchemaValue): Validation<InvalidJson, A>
```

Same recursive dispatch-on-Schema pattern, same `Validation<InvalidJson, A>` accumulation, same `Segment`-based path tracking, same `OrElse`/`Transform`/`Default`/`Optional` handling.

Key difference from the kotlinx decoder: `SchemaValue` has typed values (`Integer(Long)`, `Decimal(Double)`, `Bool(Boolean)`), so primitive decoding can match directly without string parsing. Falls through to string-based path for cross-type cases.

### Component 3: Public API (`Decoding.kt`)

```kotlin
// Core
fun <A> Schema<A>.decodeFromSource(source: Source, config: JsonDecodingConfig = JsonDecodingConfig()): Validation<InvalidJson, A>

// Convenience
fun <A> Schema<A>.decodeFromJsonString(str: String, config: JsonDecodingConfig = JsonDecodingConfig()): Validation<InvalidJson, A>
fun <A> Schema<A>.decodeFromJsonBytes(bytes: ByteArray, config: JsonDecodingConfig = JsonDecodingConfig()): Validation<InvalidJson, A>

// SchemaValue convenience
fun decodeSchemaValueFromString(str: String, config: JsonDecodingConfig = JsonDecodingConfig()): SchemaValue
fun decodeSchemaValueFromBytes(bytes: ByteArray, config: JsonDecodingConfig = JsonDecodingConfig()): SchemaValue
```

### Breaking change in `schema-json-kotlinx`

Remove the default value from the `json: Json` parameter on `decodeFromJsonString` in `schema-json-kotlinx/Decoding.kt`:

```kotlin
// Before
fun <A> Schema<A>.decodeFromJsonString(str: String, json: Json = Json.Default): Validation<InvalidJson, A>

// After
fun <A> Schema<A>.decodeFromJsonString(str: String, json: Json): Validation<InvalidJson, A>
```

This ensures `Schema<A>.decodeFromJsonString(str)` resolves to the new `schema-json` version by default.

## Benchmark

A simple JVM test using `measureTimeMillis` with warmup, comparing:
1. kotlinx path: `Json.parseToJsonElement` → `decodeFromJsonElement`
2. Source path: new `decodeFromJsonString` (Source → SchemaValue → A)

DTO shape exercising all Schema types:
```
Order(
    id: String, amount: Double, currency: String,
    customer: Customer(name, email, age, verified, tags: List<String>),
    items: List<LineItem(sku, name, qty, price, discount: Double?)>,
    shipping: Address(street, city, state, zip, country),
    billing: Address,
    status: OrderStatus (union: Pending | Shipped(trackingId) | Delivered(date)),
    notes: String?,
    metadata: Map<String, String>
)
```

Warmup ~1000 iterations, measured ~10000 iterations, print avg/min/max.

## Testing

| Test file | Coverage |
|---|---|
| `SourceDecodingTest.kt` | JSON parser: all value types, escaping, nesting, lenient mode, error cases |
| `SchemaValueDecodingTest.kt` | Schema decoder: round-trips (`decode(encode(v)) == v`), error accumulation, path tracking |
| Cross-decoder equivalence | Assert new decoder produces same `Validation` as kotlinx decoder for all cases |
| `DecodingBenchmarkTest.kt` | Performance comparison, large DTO x10000 |

## Files

| File | Module | Purpose |
|---|---|---|
| `SourceDecoding.kt` | schema-json | JSON parser: Source → SchemaValue |
| `SchemaValueDecoding.kt` | schema-json | Schema decoder: SchemaValue → Validation |
| `Decoding.kt` | schema-json | Public API conveniences |
| `JsonDecodingConfig.kt` | schema-json | Lenient parsing config |
| `Decoding.kt` | schema-json-kotlinx | Remove default on `json: Json` param |
| `SourceDecodingTest.kt` | schema-json (test) | Parser unit tests |
| `SchemaValueDecodingTest.kt` | schema-json (test) | Decoder + round-trip tests |
| `DecodingBenchmarkTest.kt` | schema-json (test) | Benchmark vs kotlinx |
