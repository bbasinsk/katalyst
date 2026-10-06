# Katalyst

Kotlin Multiplatform libraries (group `io.github.bbasinsk`, JVM/Android/Native/JS) for schema-as-values, validation, type-safe HTTP endpoints (Ktor 2/3 servers, Ktor 3 client, SSE), OpenAPI generation, and JSON/Avro/HOCON/JSON-Schema serialization. Modules are in `settings.gradle.kts`; shared build config is in `convention-plugins/`.

## Modules

| Module | Purpose | Readme |
|---|---|---|
| `schema` | Schemas as values: records, unions, transforms | `schema/readme.md` |
| `schema-json`, `schema-json-kotlinx` | JSON codecs; kotlinx.serialization bridge | `schema-json/readme.md`, `schema-json-kotlinx/readme.md` |
| `schema-avro`, `schema-hocon` | Avro binary / HOCON config codecs | `schema-avro/readme.md`, `schema-hocon/readme.md` |
| `schema-json-schema` | JSON Schema output (also AI response schemas: OpenAI/Claude/Gemini) | `schema-json-schema/readme.md` |
| `schema-kotlinx-datetime` | kotlinx.datetime types for schemas | `schema-kotlinx-datetime/readme.md` |
| `validation` | Accumulating functional validation | `validation/readme.md` |
| `tuple` | Tuple helpers used by `http` params | `tuple/readme.md` |
| `http` | Endpoint DSL, request/response, SSE | `http/readme.md` |
| `http-server-ktor-2`, `http-server-ktor-3` | Ktor server adapters (`handle(endpoint)`) | — |
| `http-client-ktor-3` | Client from the same endpoint definitions | `http-client-ktor-3/readme.md` |
| `http-openapi` | OpenAPI spec from endpoints | `http-openapi/readme.md` |
| `http-openapi-gradle-plugin` | Gradle plugin `io.github.bbasinsk.http-openapi` (build-time spec) | `http-openapi-gradle-plugin/README.md` |

Changing the endpoint DSL → `http/readme.md`. Changing OpenAPI output → `http-openapi/readme.md`. Adding a schema type → `schema/readme.md` and every `schema-*` codec must handle it.

## Build

Task names follow the convention plugin a module applies (`convention-plugins/src/main/kotlin/`):

| Modules | Convention plugin → Kotlin plugin | Compile | Test |
|---|---|---|---|
| every `schema*`, `validation`, `tuple`, `http*` except the gradle plugin | `katalyst.library` → `kotlin("multiplatform")` | `:<m>:compileKotlinJvm` | `:<m>:jvmTest` |
| `http-openapi-gradle-plugin` | `katalyst.gradle-plugin` → `kotlin("jvm")` | `:http-openapi-gradle-plugin:compileKotlin` | `:http-openapi-gradle-plugin:test` |

- Multiplatform modules also have per-target tasks (`jsNodeTest`, `macosArm64Test`, …); `jsBrowserTest` is disabled by the convention plugin. Not every module has every target — check its `kotlin {}` block.
- CI (`.github/workflows/gradle.yml`) runs `./gradlew build` on JDK 21 with the Android SDK installed; the convention plugins pin `jvmToolchain(17)`. Local toolchain: `mise.toml`.
- Single test: `./gradlew :http:jvmTest --tests "io.github.bbasinsk.http.SomeTest"`.

## Kotlin
- Prefer `runCatching` over try-catch
- Expected failure modes (validation errors, business rule violations, known external failures you can recover from) are typed values, not thrown exceptions — they are part of the domain
- Let truly exceptional failures propagate as exceptions (DB unreachable, infra down, programmer errors, violated invariants) — don't create types for failures you can't predict or recover from
- Branch on sealed hierarchies with exhaustive `when`, never `as?`/`is` casts — exhaustive matching makes adding a subtype a compile error at every decision site

## Release
release-please (`release-please-config.json`, `.github/workflows/release-please.yml`) owns `CHANGELOG.md`, `.release-please-manifest.json`, and the `version=` line in `gradle.properties`; never hand-edit them. Use Conventional Commit messages (`feat:`/`fix:` drive the bump). Merged releases publish to Maven Central automatically.
