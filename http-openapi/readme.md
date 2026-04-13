# Http OpenAPI

OpenAPI 3.1 spec generation for Katalyst `Http` endpoint definitions, plus helpers for serving the spec and built-in doc UIs (Swagger, ReDoc, Stoplight Elements) from the same `Http` DSL used for everything else.

## Generating a spec

Any `List<Http<*, *, *, *, *>>` can be turned into an `OpenAPI` model:

```kotlin
import io.github.bbasinsk.http.openapi.Info
import io.github.bbasinsk.http.openapi.Server
import io.github.bbasinsk.http.openapi.toOpenApiSpec

val spec: OpenAPI = listOf(personEndpoint, listEndpoint)
    .toOpenApiSpec(
        info = Info(title = "My API", version = "1.0.0"),
        servers = listOf(Server(url = "https://api.example.com"))
    )
```

`spec` is a `@Serializable` data class; serialize it with the module's pre-configured `OpenApiJson`:

```kotlin
import kotlinx.serialization.encodeToString

val json: String = OpenApiJson.encodeToString(spec)
```

`OpenApiJson` is a `kotlinx.serialization.json.Json` instance configured to match the OpenAPI spec formatting (`prettyPrint`, `encodeDefaults`, `explicitNulls = false`).

## Hosting the spec and UIs

Katalyst does not auto-mount anything. The spec endpoint and its UIs are declared as regular `Http` values and registered with `handle(...)` — so they participate in the same auth, tagging, and routing machinery as the rest of your API.

The `OpenapiDocs` class bundles a spec endpoint with its `info`/`servers` and the three built-in UI renderers. The UI pages always point at the spec's derived URL path, so you cannot accidentally point a UI at a stale or wrong path.

```kotlin
import io.github.bbasinsk.http.Http
import io.github.bbasinsk.http.ContentType
import io.github.bbasinsk.http.openapi.Info
import io.github.bbasinsk.http.openapi.OpenapiDocs
import io.github.bbasinsk.http.openapi.Server

// 1. Declare the routes as plain Http definitions
val openApiFile = Http.get { Root / "openapi.json" }
    .output { status(Ok) { bytes(ContentType.Json) } }
    .tag("docs")

val swaggerUi = Http.get { Root / "swagger" }
    .output { status(Ok) { html() } }
    .tag("docs")

val redocUi = Http.get { Root / "redoc" }
    .output { status(Ok) { html() } }
    .tag("docs")

val stoplightUi = Http.get { Root / "stoplight" }
    .output { status(Ok) { html() } }
    .tag("docs")

// 2. Bind them to info/servers
val docs = OpenapiDocs(
    spec = openApiFile,
    info = Info(title = "My API", version = "1.0.0"),
    servers = listOf(Server(url = "http://localhost:8080")),
)

// 3. Register (ktor-3 shown — same shape for other servers)
endpoints {
    personEndpoints()
    // ... other endpoints ...

    handle(openApiFile) { _ -> Response.success(docs.specJson(apis())) }
    handle(swaggerUi)   { _ -> Response.success(docs.swaggerHtml()) }
    handle(redocUi)     { _ -> Response.success(docs.redocHtml()) }
    handle(stoplightUi) { _ -> Response.success(docs.stoplightHtml()) }
}
```

### Why `bytes(ContentType.Json)` on the spec endpoint?

The spec is serialized once per request and the handler hands back the raw bytes — there is no useful schema to run it through. Modeling the output as `bytes(ContentType.Json)` tells the server to write those bytes verbatim with `Content-Type: application/json` instead of re-encoding them through the `Schema` layer.

### Why call `apis()` inside the handler?

`apis()` returns every endpoint registered in the enclosing `endpoints { }` block at the time of the call. By the time a request arrives the block has already finished building, so the list always contains every endpoint — including the docs routes themselves.

## Putting the docs behind auth

Because the docs routes are plain `Http` values, the existing `.auth { }` DSL works verbatim. Attach whichever auth scheme you already use for the rest of your API and register with the matching `AuthHandler`:

```kotlin
data class Admin(val name: String)

val adminAuth = AuthHandler.standard<Admin> { token ->
    adminService.validate(token)
}

val openApiFile = Http.get { Root / "openapi.json" }
    .output { status(Ok) { bytes(ContentType.Json) } }
    .auth { basic<Admin>() }
    .tag("docs")

val swaggerUi = Http.get { Root / "swagger" }
    .output { status(Ok) { html() } }
    .auth { basic<Admin>() }
    .tag("docs")

endpoints {
    handle(openApiFile, adminAuth) { _ -> Response.success(docs.specJson(apis())) }
    handle(swaggerUi,   adminAuth) { _ -> Response.success(docs.swaggerHtml()) }
}
```

Browsers will show a native credential prompt for Basic auth. For other schemes (bearer, cookie, api key, `or`-composed), follow the same pattern as any other protected endpoint — see [`http/readme.md`](../http/readme.md#authentication).

## Security schemes from auth

Auth schemas declared via the `Http` DSL are automatically rendered as OpenAPI security schemes. A set of endpoints using the four built-in schemes produces:

```json
{
  "components": {
    "securitySchemes": {
      "bearerAuth": { "type": "http", "scheme": "bearer", "bearerFormat": "JWT" },
      "basicAuth":  { "type": "http", "scheme": "basic" },
      "apiKeyAuth": { "type": "apiKey", "in": "header", "name": "X-API-Key" },
      "cookieAuth": { "type": "apiKey", "in": "cookie", "name": "token" }
    }
  }
}
```

Each endpoint that uses one of these schemes gets a matching `security` entry in its path item. Composite auth (`bearer() or cookie("session")`) produces OR-style requirements — see [`http/readme.md`](../http/readme.md#composite-authentication).

### Custom scheme names

Use the `schemeName` parameter when multiple endpoints use different configurations of the same auth type and you want them to appear as distinct security schemes in the spec:

```kotlin
val jwtEndpoint = Http.get { Root / "jwt-protected" }
    .auth { bearer<User>(format = "JWT", schemeName = "jwtAuth") }

val opaqueEndpoint = Http.get { Root / "opaque-protected" }
    .auth { bearer<User>(schemeName = "opaqueAuth") }

val apiKeyEndpoint = Http.get { Root / "api" }
    .auth { apiKeyHeader<ApiToken>("X-API-Key", schemeName = "apiKeyAuth") }

val customKeyEndpoint = Http.get { Root / "custom" }
    .auth { apiKeyHeader<ApiToken>("X-Custom-Key", schemeName = "customKeyAuth") }
```

Produces:

```json
{
  "securitySchemes": {
    "jwtAuth":       { "type": "http", "scheme": "bearer", "bearerFormat": "JWT" },
    "opaqueAuth":    { "type": "http", "scheme": "bearer" },
    "apiKeyAuth":    { "type": "apiKey", "in": "header", "name": "X-API-Key" },
    "customKeyAuth": { "type": "apiKey", "in": "header", "name": "X-Custom-Key" }
  }
}
```

Without `schemeName`, the two bearer endpoints would collide under the default `bearerAuth` key.

## `OpenapiDocs` reference

| Member | Description |
|---|---|
| `spec: Http<*, *, *, ByteArray, *>` | The spec endpoint definition. |
| `info: Info` | API metadata (title, version, description). |
| `servers: List<Server>` | Server URLs to advertise in the spec. Default: empty. |
| `specPath: String` | URL path of the spec endpoint, derived from `spec.params`. |
| `specJson(apis): ByteArray` | Serialized OpenAPI spec, ready to return from the spec handler. |
| `swaggerHtml(): String` | Swagger UI HTML pointing at `specPath`. |
| `redocHtml(): String` | ReDoc UI HTML pointing at `specPath`. |
| `stoplightHtml(): String` | Stoplight Elements UI HTML pointing at `specPath`. |

The three UI renderers interpolate `specPath` with HTML-escaping, so an exotic path segment cannot produce broken or injectable HTML.
