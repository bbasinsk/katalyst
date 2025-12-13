# Path Schema Parameter Type Simplification

## Problem Statement

The current `PathSchema<A>` type tracks **all** path components in its type parameter using nested `Pair` types, including segments that contribute `Unit`:

```kotlin
// Current: Root / "users" / param("id") / "posts" / param("postId")
// Type: PathSchema<Pair<Pair<Pair<Pair<Unit, Unit>, String>, Unit>, Int>>
```

This requires the `tupleValues` function to handle every possible combination of `Unit` positions to extract just the meaningful parameter values. With 12 path components:

- **4,095 overloads** in TupleValues12.kt alone (2^12 - 1 combinations)
- **12,320 lines** of generated code in a single file
- IDE autocomplete becomes unusable when typing `tupleVal...`

## Goals

1. **Eliminate exponential overload growth** - Reduce from O(2^n) to O(n) tupleValues overloads
2. **Improve IDE experience** - Autocomplete should show a reasonable number of options
3. **Preserve type safety** - Users should still get compile-time guarantees that parameters match
4. **Maintain ergonomic API** - The DSL syntax `Root / "segment" / param("id")` should remain unchanged
5. **Keep destructuring simple** - Users should never need to filter out `Unit` values:
   ```kotlin
   // GOOD: val (personId, thing) = tupleValues(request.params)
   // BAD:  val (_, personId, _, thing) = tupleValues(request.params)
   ```

## Current Architecture

### PathSchema Type Hierarchy
```kotlin
// http/src/commonMain/kotlin/io/github/bbasinsk/http/ParamsSchema.kt
sealed interface PathSchema<A> : ParamsSchema<A> {
    data object RootSchema : PathSchema<Unit>
    data class Segment(val name: String) : PathSchema<Unit>
    data class Parameter<A>(val param: ParamSchema<A>) : PathSchema<A>
    data class Combine<A, B>(val left: PathSchema<A>, val right: PathSchema<B>) : PathSchema<Pair<A, B>>

    operator fun div(segment: String): PathSchema<Pair<A, Unit>> = Combine(this, Segment(segment))
    operator fun <B> div(param: ParamSchema<B>): PathSchema<Pair<A, B>> = Combine(this, Parameter(param))
}
```

### Type Evolution Example (Current - Problematic)
```
Root                                    → PathSchema<Unit>
Root / "users"                          → PathSchema<Pair<Unit, Unit>>
Root / "users" / param("id")            → PathSchema<Pair<Pair<Unit, Unit>, String>>
Root / "users" / param("id") / "posts"  → PathSchema<Pair<Pair<Pair<Unit, Unit>, String>, Unit>>
```

### The tupleValues Problem
For every depth `n`, we need overloads for every subset of positions that could contain real values vs `Unit`. This is the power set minus the empty set = 2^n - 1 overloads.

---

## Proposed Solution: Sealed Empty/NonEmpty Hierarchy

**Core Idea**: Use two sealed subtypes of `PathSchema` - one for paths with no params yet (`EmptyPathSchema`), one for paths with params (`NonEmptyPathSchema<A>`). The `div` operator on each returns the appropriate type.

### Desired Type Evolution (New)
```
Root                                    → EmptyPathSchema (: PathSchema<Unit>)
Root / "users"                          → EmptyPathSchema
Root / "users" / param("id")            → NonEmptyPathSchema<String>
Root / "users" / param("id") / "posts"  → NonEmptyPathSchema<String>
Root / "users" / param("id") / "posts" / param("postId")
                                        → NonEmptyPathSchema<Pair<String, Int>>
```

### Implementation Design

```kotlin
sealed interface PathSchema<out A> : ParamsSchema<A>

// Empty path state - no parameters yet
sealed interface EmptyPathSchema : PathSchema<Unit> {
    data object Root : EmptyPathSchema
    data class Segment(val name: String, val prefix: EmptyPathSchema) : EmptyPathSchema

    // Adding segment stays empty
    operator fun div(segment: String): EmptyPathSchema = Segment(segment, this)

    // Adding first param transitions to NonEmpty - the param becomes the whole type
    operator fun <B> div(param: ParamSchema<B>): NonEmptyPathSchema.First<B> =
        NonEmptyPathSchema.First(param, this)
}

// Non-empty path state - has at least one param
sealed interface NonEmptyPathSchema<out A> : PathSchema<A> {
    // First param - wraps the empty prefix
    data class First<A>(val param: ParamSchema<A>, val prefix: EmptyPathSchema) : NonEmptyPathSchema<A>

    // Segment after params - doesn't change type
    data class Segment<A>(val name: String, val prefix: NonEmptyPathSchema<A>) : NonEmptyPathSchema<A>

    // Additional param - creates Pair type
    data class Next<A, B>(val prefix: NonEmptyPathSchema<A>, val param: ParamSchema<B>) : NonEmptyPathSchema<Pair<A, B>>

    // Adding segment keeps same type
    operator fun div(segment: String): NonEmptyPathSchema<A> = Segment(segment, this)

    // Adding next param creates Pair
    operator fun <B> div(param: ParamSchema<B>): NonEmptyPathSchema<Pair<A, B>> = Next(this, param)
}

// Companion for nice syntax
companion object {
    val Root: EmptyPathSchema = EmptyPathSchema.Root
}
```

### Why This Works

1. **No overload ambiguity**: `EmptyPathSchema.div(param)` and `NonEmptyPathSchema.div(param)` are on different receiver types, so Kotlin resolves them unambiguously.

2. **Type evolution is natural**:
   - Empty + segment → Empty
   - Empty + param → NonEmpty<A>
   - NonEmpty<A> + segment → NonEmpty<A>
   - NonEmpty<A> + param → NonEmpty<Pair<A, B>>

3. **Segments are tracked for routing**: Each variant stores its prefix, so the full path structure is preserved for parsing/matching.

### Simplified tupleValues

With no Unit positions, `tupleValues` only needs **one overload per arity**:

```kotlin
// Arity 1 - single value (no Pair)
fun <A> tupleValues(value: A): TupleValues1<A> = TupleValues1(value)

// Arity 2 - simple Pair
fun <A, B> tupleValues(pair: Pair<A, B>): TupleValues2<A, B> =
    TupleValues2(pair.first, pair.second)

// Arity 3 - nested Pair
fun <A, B, C> tupleValues(pair: Pair<Pair<A, B>, C>): TupleValues3<A, B, C> =
    TupleValues3(pair.first.first, pair.first.second, pair.second)

// ... up to arity 12
```

**Total: 12 overloads** instead of 4,095+

### Query/Header: Overloaded Extension Functions

To eliminate `Pair<Unit, ...>` when combining empty paths with query/header, use **overloaded extension functions** instead of interface methods:

```kotlin
// Remove from ParamsSchema interface:
// fun <B> withQuery(right: ParamSchema<B>): ParamsSchema<Pair<A, B>>

// Specific overload for EmptyPathSchema - returns B directly, not Pair<Unit, B>
fun <B> EmptyPathSchema.withQuery(right: ParamSchema<B>): ParamsSchema<B> =
    ParamsSchema.QuerySchema(right)

fun <B> EmptyPathSchema.withHeader(right: ParamSchema<B>): ParamsSchema<B> =
    ParamsSchema.HeaderSchema(right)

// General overload for all other ParamsSchema - returns Pair<A, B>
fun <A, B> ParamsSchema<A>.withQuery(right: ParamSchema<B>): ParamsSchema<Pair<A, B>> =
    ParamsSchema.Combine(this, ParamsSchema.QuerySchema(right))

fun <A, B> ParamsSchema<A>.withHeader(right: ParamSchema<B>): ParamsSchema<Pair<A, B>> =
    ParamsSchema.Combine(this, ParamsSchema.HeaderSchema(right))
```

Kotlin's overload resolution picks the more specific one for `EmptyPathSchema`:

| Expression | Current Type | New Type |
|------------|--------------|----------|
| `(Root / "health").withQuery(page)` | `Pair<Pair<Unit, Unit>, Int>` | `Int` |
| `(Root / "health").withQuery(p1).withQuery(p2)` | `Pair<Pair<...>, Int>` | `Pair<Int, Int>` |
| `(Root / param("id")).withQuery(page)` | `Pair<Pair<...>, Int>` | `Pair<String, Int>` |

**Result**: `Unit` is completely eliminated from the type system.

### Parsing Logic

The `parse` function walks the structure recursively:

```kotlin
fun <A> PathSchema<A>.parse(rawPath: MutableList<String>, ...): A = when (this) {
    is EmptyPathSchema.Root -> Unit as A
    is EmptyPathSchema.Segment -> {
        prefix.parse(rawPath, ...)
        consumeSegment(rawPath, name)
        Unit as A
    }
    is NonEmptyPathSchema.First -> {
        prefix.parse(rawPath, ...)  // returns Unit, discarded
        param.parse(rawPath)         // returns A
    }
    is NonEmptyPathSchema.Segment -> {
        val prefixValue = prefix.parse(rawPath, ...)
        consumeSegment(rawPath, name)
        prefixValue  // type A unchanged
    }
    is NonEmptyPathSchema.Next -> {
        val prefixValue = prefix.parse(rawPath, ...)  // type A
        val nextValue = param.parse(rawPath)           // type B
        Pair(prefixValue, nextValue) as A              // type Pair<A, B>
    }
}
```

---

## Success Criteria

1. TupleValues files should have O(n) overloads, not O(2^n)
2. `tupleValues(request.params)` should work identically from the user's perspective
3. Path DSL syntax remains: `Root / "users" / param("id") { string() }`
4. No runtime performance regression in path parsing
5. Types in IDE tooltips should be readable (e.g., `Pair<String, Int>` not `Pair<Pair<Pair<Unit, String>, Unit>, Int>`)

## Files to Modify

- `http/src/commonMain/kotlin/io/github/bbasinsk/http/ParamsSchema.kt` - Main schema types
- `tuple/src/commonMain/kotlin/io/github/bbasinsk/tuple/TupleValues*.kt` - Simplified overloads (can delete most, keep simplified versions)
- `tuple/src/jvmTest/kotlin/io/github/bbasinsk/tuple/TupleGenerator.kt` - Simplified generator
- Tests in `http/src/commonTest/` and `http-ktor-*/src/jvmTest/`

---

## Implementation Gotchas Analysis

### Gotcha 1: DO NOT Use Covariance (`out A`)

**Problem**: If `PathSchema<out A>` is covariant but extends `ParamsSchema<A>` (invariant), the inherited `withQuery`/`withHeader` methods cause type mismatches.

```kotlin
// BAD - causes issues:
sealed interface PathSchema<out A> : ParamsSchema<A>

val path: PathSchema<Dog> = ...
val pathAsAnimal: PathSchema<Animal> = path  // Valid due to covariance
val result = pathAsAnimal.withQuery(...)     // Returns ParamsSchema<Pair<Animal, B>>
// But runtime creates ParamsSchema<Pair<Dog, B>> - TYPE MISMATCH!
```

**Solution**: Keep `PathSchema<A>` **invariant** (no `out`). This is fine - we don't need subtype polymorphism for path schemas.

---

### Gotcha 2: Pattern Matching Breakage (6 files affected)

Current code pattern-matches on `PathSchema.Segment`, `PathSchema.Parameter`, etc. in:

| File | Function | Lines |
|------|----------|-------|
| `ParamsSchema.kt` | `pathSchemas()` | 17-26 |
| `ParamsSchema.kt` | `parse()` | 73-110 |
| `KtorAdapter.kt` (ktor-2) | `toSelector()` | 66-71 |
| `KtorAdapter.kt` (ktor-3) | `toSelector()` | 75-80 |
| `SpecAdapter.kt` | `toFormattedPath()` | 38-45 |
| `SpecAdapter.kt` | `pathParams()` | 74-82 |

**Solution**: Introduce marker interfaces `PathSegment` and `PathParam` that the new types implement:

```kotlin
sealed interface PathSegment {
    val name: String
}

sealed interface PathParam {
    val param: ParamSchema<*>
}

// EmptyPathSchema.Segment implements PathSegment
// NonEmptyPathSchema.Segment implements PathSegment
// NonEmptyPathSchema.First implements PathParam
// NonEmptyPathSchema.Next implements PathParam
```

Callers update from:
```kotlin
// OLD
when (x) {
    is PathSchema.Segment -> x.name
    is PathSchema.Parameter -> x.param
}

// NEW - minimal change
when (x) {
    is PathSegment -> x.name
    is PathParam -> x.param
}
```

---

### Gotcha 3: Unchecked Casts (Same as Current)

The `parse()` function already has unchecked casts:
```kotlin
is PathSchema.RootSchema -> Unit as A
is PathSchema.Segment -> { ... return Unit as A }
is PathSchema.Combine<*, *> -> { ... return Pair(left, right) as A }
```

**Assessment**: The new design has **similar number of casts** - not worse. Type erasure with `<*, *>` is unavoidable in the `when` expressions.

---

### Gotcha 4: pathSchemas() Traversal Logic

The new structure stores components via `prefix` references. Traversal for `pathSchemas()`:

```kotlin
fun PathSchema<*>.pathSchemas(): List<PathSchema<*>> = when (this) {
    is EmptyPathSchema.Root -> emptyList()
    is EmptyPathSchema.Segment -> prefix.pathSchemas() + listOf(this)
    is NonEmptyPathSchema.First<*> -> prefix.pathSchemas() + listOf(this)
    is NonEmptyPathSchema.Segment<*> -> prefix.pathSchemas() + listOf(this)
    is NonEmptyPathSchema.Next<*, *> -> prefix.pathSchemas() + listOf(this)
}
```

Each node adds itself after its prefix's components - correct order preserved.

---

### Gotcha 5: withQuery/withHeader Become Extension Functions

To eliminate `Pair<Unit, ...>`, `withQuery`/`withHeader` move from interface methods to overloaded extension functions:

```kotlin
// EmptyPathSchema overload - returns B directly
fun <B> EmptyPathSchema.withQuery(right: ParamSchema<B>): ParamsSchema<B> =
    ParamsSchema.QuerySchema(right)

// General overload - returns Pair<A, B>
fun <A, B> ParamsSchema<A>.withQuery(right: ParamSchema<B>): ParamsSchema<Pair<A, B>> =
    ParamsSchema.Combine(this, ParamsSchema.QuerySchema(right))
```

Kotlin picks the more specific overload for `EmptyPathSchema`, so:
- `(Root / "health").withQuery(p)` → `ParamsSchema<Int>` (not `Pair<Unit, Int>`)
- `(Root / param("id")).withQuery(p)` → `ParamsSchema<Pair<String, Int>>`

---

## Refined Implementation Design

```kotlin
// Marker interfaces for clean pattern matching
sealed interface PathSegment { val name: String }
sealed interface PathParam { val param: ParamSchema<*> }

sealed interface PathSchema<A> : ParamsSchema<A>  // INVARIANT, not covariant

sealed interface EmptyPathSchema : PathSchema<Unit> {
    data object Root : EmptyPathSchema

    data class Segment(
        override val name: String,
        val prefix: EmptyPathSchema
    ) : EmptyPathSchema, PathSegment

    operator fun div(segment: String): EmptyPathSchema = Segment(segment, this)
    operator fun <B> div(param: ParamSchema<B>): NonEmptyPathSchema.First<B> =
        NonEmptyPathSchema.First(param, this)
}

sealed interface NonEmptyPathSchema<A> : PathSchema<A> {
    data class First<A>(
        override val param: ParamSchema<A>,
        val prefix: EmptyPathSchema
    ) : NonEmptyPathSchema<A>, PathParam

    data class Segment<A>(
        override val name: String,
        val prefix: NonEmptyPathSchema<A>
    ) : NonEmptyPathSchema<A>, PathSegment

    data class Next<A, B>(
        val prefix: NonEmptyPathSchema<A>,
        override val param: ParamSchema<B>
    ) : NonEmptyPathSchema<Pair<A, B>>, PathParam

    operator fun div(segment: String): NonEmptyPathSchema<A> = Segment(segment, this)
    operator fun <B> div(param: ParamSchema<B>): NonEmptyPathSchema<Pair<A, B>> = Next(this, param)
}

val Root: EmptyPathSchema = EmptyPathSchema.Root

// Overloaded extension functions for withQuery/withHeader
fun <B> EmptyPathSchema.withQuery(right: ParamSchema<B>): ParamsSchema<B> =
    ParamsSchema.QuerySchema(right)

fun <B> EmptyPathSchema.withHeader(right: ParamSchema<B>): ParamsSchema<B> =
    ParamsSchema.HeaderSchema(right)

fun <A, B> ParamsSchema<A>.withQuery(right: ParamSchema<B>): ParamsSchema<Pair<A, B>> =
    ParamsSchema.Combine(this, ParamsSchema.QuerySchema(right))

fun <A, B> ParamsSchema<A>.withHeader(right: ParamSchema<B>): ParamsSchema<Pair<A, B>> =
    ParamsSchema.Combine(this, ParamsSchema.HeaderSchema(right))
```

---

## Files to Modify (with specific changes)

| File | Changes |
|------|---------|
| `http/.../ParamsSchema.kt` | Replace PathSchema hierarchy, remove `withQuery`/`withHeader` from interface, add extension overloads, update `pathSchemas()` and `parse()` |
| `http-ktor-2/.../KtorAdapter.kt` | Update `toSelector()` to match on `PathSegment`/`PathParam` |
| `http-ktor-3/.../KtorAdapter.kt` | Update `toSelector()` to match on `PathSegment`/`PathParam` |
| `http-openapi/.../SpecAdapter.kt` | Update `toFormattedPath()` and `pathParams()` |
| `tuple/.../TupleValues*.kt` | Delete all, regenerate with simple 12-overload version |
| `tuple/.../TupleGenerator.kt` | Simplify to generate only nested-Pair overloads |

---

## Migration Notes

- **Breaking change**: `PathSchema` sealed subtypes change
- **Pattern matching update**: Use `is PathSegment` / `is PathParam` instead of `is PathSchema.Segment` / `is PathSchema.Parameter`
- **User code unchanged**: `val (a, b) = tupleValues(request.params)` continues working
- **DSL unchanged**: `Root / "users" / param("id") { string() }` continues working
- **Type readability improved**: `Pair<String, Int>` instead of `Pair<Pair<Pair<Unit, String>, Unit>, Int>`
