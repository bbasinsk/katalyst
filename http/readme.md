# Http

Type-safe HTTP API definitions with support for authentication and streaming via Server-Sent Events (SSE).

## Request Headers

Declare typed request headers with `.header { schema(...) }`. Headers compose the same way as `.query {}` — each call adds another header, and the endpoint's `params` type grows to include them.

```kotlin
val updatePerson = Http.put { Root / "person" }
    .header { schema("X-Request-Id") { string() } }
    .header { schema("X-Client-Version") { int() } }
    .input { json { personSchema } }
    .output { status(Ok) { json { personSchema } } }

handle(updatePerson) { request ->
    val (requestId, clientVersion) = tupleValues(request.params)
    // ...
}
```

Header values support the same `ParamSchema` combinators as path/query params — including `.optional()` for headers that may be absent:

```kotlin
.header { schema("X-Trace-Id") { string().optional() } }
```

The client automatically sends declared headers when you invoke `call()` / `stream()` — no extra configuration is needed. On the OpenAPI side, headers surface as `in: header` parameters.

## Parameter and Body Metadata

Any path, query, header, or body schema can be annotated with `.description()`, `.example()`, and `.deprecated()`. These show up in the generated OpenAPI spec and help API consumers understand each field:

```kotlin
val api = Http.get { Root / "search" }
    .query {
        schema("q") { string() }
            .description("Full-text search query")
            .example("recent", "kotlin multiplatform")
    }
    .header {
        schema("X-API-Version") { int() }
            .deprecated("Use Accept-Version instead")
    }
    .output {
        status(Ok) {
            json { resultSchema }
                .description("Matching results")
                .example("empty", emptyList())
        }
    }
```

Multiple `.example(name, value)` calls accumulate — each named example is rendered separately in the OpenAPI output.

## Endpoint Metadata

Attach OpenAPI metadata directly on the endpoint with `.summary()`, `.deprecated()`, and `.tag()`:

```kotlin
val updatePerson = Http.put { Root / "person" / param("id") { uuid() } }
    .summary("Update a person")
    .deprecated("Use PATCH /person/{id} instead")
    .tag("people", "admin")
    .input { json { personSchema } }
    .output { status(Ok) { json { personSchema } } }
```

- `summary` becomes the operation's short description in the OpenAPI spec
- `deprecated(reason)` marks the operation as deprecated and records the reason
- `tag(...)` accepts varargs and appends to any tags already set — useful for grouping endpoints in Swagger UI

## Request Body Formats

`json` and `plain` are the most common body formats, but the `input {}` / output `status(...) {}` blocks accept several others:

| Format | DSL | Content-Type |
|--------|-----|--------------|
| JSON | `json { schema }` | `application/json` |
| Plain text | `plain { schema }` | `text/plain` |
| HTML | `html()` | `text/html` |
| Avro | `avro { schema }` | `application/avro` |
| Raw bytes | `bytes(contentType)` | caller-supplied |
| Multipart | `multipart { recordSchema }` | `multipart/form-data` |
| URL-encoded form | `formUrlEncoded { recordSchema }` | `application/x-www-form-urlencoded` |
| Empty | `empty()` | — |

### Multipart

`multipart` takes a record schema; each field becomes one form part. Use `Schema.byteArray()` for file uploads and `list(byteArray())` for multiple files under a single field name:

```kotlin
data class UploadRequest(val name: String, val files: List<ByteArray>)

val upload = Http.post { Root / "upload" }
    .input {
        multipart {
            record(
                field(string(), "name") { name },
                field(list(byteArray()), "files") { files },
                ::UploadRequest
            )
        }
    }
    .output { status(Ok) { plain { string() } } }
```

### Form URL-encoded

```kotlin
val login = Http.post { Root / "login" }
    .input {
        formUrlEncoded {
            record(
                field(string(), "username") { username },
                field(string(), "password") { password },
                ::LoginForm
            )
        }
    }
    .output { status(Ok) { json { tokenSchema } } }
```

### Raw bytes

Use `bytes(...)` when the body is opaque to Katalyst and you want to pass it through untouched:

```kotlin
val uploadImage = Http.post { Root / "image" }
    .input { bytes(ContentType.Image.Png) }
    .output { status(Ok) { empty() } }
```

Available content types are defined in `ContentType` — `Json`, `Plain`, `Html`, `Avro`, `MultipartFormData`, `FormUrlEncoded`, `EventStream`, and the `ContentType.Image` family (`Jpeg`, `Png`, `Gif`, `Webp`).

## Authentication

Katalyst provides type-safe authentication with phantom types to ensure compile-time safety between your auth schema and validator.

### Auth Types

#### Bearer Token

```kotlin
data class User(val id: String, val name: String)

val api = Http.get { Root / "profile" }
    .auth { bearer<User>(format = "JWT") }
    .output { status(Ok) { json { userSchema } } }
```

#### Basic Auth

```kotlin
data class Credentials(val username: String, val roles: List<String>)

val api = Http.get { Root / "admin" }
    .auth { basic<Credentials>() }
    .output { status(Ok) { json { dataSchema } } }
```

#### API Key (Header)

```kotlin
data class ApiToken(val clientId: String, val scopes: List<String>)

val api = Http.get { Root / "data" }
    .auth { apiKeyHeader<ApiToken>("X-API-Key") }
    .output { status(Ok) { json { dataSchema } } }
```

Note: API keys are only supported in headers (not query params) for security reasons.

### Security Best Practices

#### Cookie Authentication
- Always set `HttpOnly` and `Secure` flags on session cookies to prevent XSS attacks and ensure HTTPS-only transmission
- Use `SameSite=Strict` or `SameSite=Lax` to protect against CSRF attacks
- Implement short expiration times and session rotation
- Store session tokens securely server-side (not user data in the cookie itself)

#### General Recommendations
- Use HTTPS for all authenticated endpoints
- Implement rate limiting to prevent brute-force attacks
- Validate and sanitize all input in validators
- Log authentication failures for monitoring and alerting
- Consider token rotation and refresh mechanisms for long-lived sessions

#### Cookie

```kotlin
data class Session(val userId: String, val roles: List<String>)

val api = Http.get { Root / "dashboard" }
    .auth { cookie<Session>("token") }
    .output { status(Ok) { json { dashboardSchema } } }
```

### Composite Authentication

Use `or` to accept auth from multiple sources. The server extracts the first available token and validates it with a single handler:

```kotlin
val api = Http.get { Root / "profile" }
    .auth { bearer<User>() or cookie("session") }
    .output { status(Ok) { json { userSchema } } }

// Single handler validates whichever token was found
handle(api, authHandler) { request ->
    val user: User = request.auth
    Response.success(user)
}
```

The server tries each scheme's extraction in order. In the example above, it checks the `Authorization: Bearer` header first, then the `session` cookie. The first non-null token is passed to the handler. If that token fails validation, the server returns 401 without trying subsequent schemes.

This is useful when the same endpoint serves both browser clients (which send cookies) and API clients (which send bearer tokens), and both use the same token format.

Composite auth composes with `.optional()`:

```kotlin
val api = Http.get { Root / "feed" }
    .auth { (bearer<User>() or cookie("session")).optional() }
    .output { status(Ok) { json { feedSchema } } }
```

Chain multiple schemes:

```kotlin
.auth { bearer<User>() or cookie("session") or apiKeyHeader("X-API-Key") }
```

OpenAPI generates OR-style security requirements:

```json
{
  "security": [
    { "bearerAuth": [] },
    { "cookieAuth": [] }
  ]
}
```

### Optional Authentication

Wrap any auth type with `.optional()` to allow unauthenticated access:

```kotlin
val api = Http.get { Root / "content" }
    .auth { bearer<User>().optional() }
    .output { status(Ok) { json { contentSchema } } }

// In handler, auth is nullable
handle(api, authHandler) { request ->
    val user: User? = request.auth
    if (user != null) {
        Response.success(personalizedContent(user))
    } else {
        Response.success(publicContent())
    }
}
```

### Implementing Auth Handlers

Provide an `AuthHandler` when registering handlers. The handler receives the token (or null if missing) and returns an `AuthResult`:

```kotlin
// Standard handler: returns Unauthorized on missing/invalid token
val bearerHandler = AuthHandler.standard<User> { token ->
    // Validate JWT and extract user, return null if invalid
    jwtService.validateAndDecode(token)
}

// Redirect handler: redirects to login page on failure
val redirectHandler = AuthHandler.withRedirect<User>("/login") { token ->
    jwtService.validateAndDecode(token)
}

// Dev mode: always succeeds with a static user
val devHandler = AuthHandler.static(User("dev", "Developer"))

// Custom handler: full control over AuthResult
val customHandler = AuthHandler<User> { token ->
    when {
        token == null -> AuthResult.Redirect("/login")
        else -> when (val user = jwtService.validateAndDecode(token)) {
            null -> AuthResult.Unauthorized
            else -> AuthResult.Success(user)
        }
    }
}
```

#### Example Handlers

```kotlin
val basicHandler = AuthHandler.standard<Credentials> { base64Credentials ->
    // Note: Credentials are passed as base64-encoded "username:password"
    // You must decode before validating
    val decoded = Base64.getDecoder().decode(base64Credentials).decodeToString()
    val (username, password) = decoded.split(":", limit = 2)
    authService.authenticate(username, password)
}

val apiKeyHandler = AuthHandler.standard<ApiToken> { key ->
    // Look up API key and return token info
    apiKeyService.validate(key)
}

val cookieHandler = AuthHandler.withRedirect<Session>("/login") { cookieValue ->
    // Look up session by cookie value
    sessionService.validateSession(cookieValue)
}
```

See [`http-openapi/readme.md`](../http-openapi/readme.md) for how auth schemas surface as OpenAPI security schemes, how to generate and serialize a spec, and how to host it alongside the built-in UIs.

## Server-Sent Events (SSE) Streaming

Katalyst supports SSE streaming endpoints for real-time data delivery to clients.

### Defining Streaming Endpoints

Use the `streaming` response schema to define an SSE endpoint:

```kotlin
data class Message(val text: String, val timestamp: Long)

val messageSchema = Schema.record(
    Schema.field(Schema.string(), "text") { text },
    Schema.field(Schema.long(), "timestamp") { timestamp },
    ::Message
)

val streamApi = Http.get { Root / "events" }
    .output { sse(Ok) { json { messageSchema } } }
```

### Returning Streaming Responses

Use `Response.streamingSuccessData()` for simple data streams:

```kotlin
handle(streamApi) {
    Response.streamingSuccessData(flow {
        repeat(10) { i ->
            emit(Message("Event $i", System.currentTimeMillis()))
            delay(1000)
        }
    })
}
```

For full control over SSE fields, use `Response.streamingSuccess()` with `SSEEvent`:

```kotlin
handle(streamApi) {
    Response.streamingSuccess(flow {
        emit(SSEEvent.data(Message("Hello", System.currentTimeMillis())))
        emit(SSEEvent.typed("update", Message("Update", System.currentTimeMillis())))
    })
}
```

### SSEEvent API

The `SSEEvent` class provides several factory methods:

| Method | Description |
|--------|-------------|
| `SSEEvent.data(value)` | Simple event with just data |
| `SSEEvent.typed(eventType, value)` | Event with custom type for client-side filtering |
| `SSEEvent.full(data, event, id, retry, comment)` | Event with all SSE fields |
| `SSEEvent.keepalive(comment)` | Comment-only event for connection keepalive |

Example with all fields:

```kotlin
SSEEvent(
    data = message,
    event = "update",           // Custom event type
    id = "msg-123",             // Event ID for resumption
    retry = 5000L,              // Client reconnection time (ms)
    comment = "debug info"      // Comment (ignored by EventSource)
)
```

### Error Handling

**Structured errors:** Use `Response.streamingError()` to send typed error events:

```kotlin
val api = Http.get { Root / "stream" }
    .output { status(Ok) { json { dataSchema } } }
    .error { sse(Ok) { json { errorSchema } } }

handle(api) {
    Response.streamingError(flow {
        emit(SSEEvent.typed("error", ErrorInfo(500, "Service unavailable")))
    })
}
```

**Exception handling:** If an exception occurs during streaming, a sanitized error event is sent to prevent information leakage:
- Client receives: `event: error` with `data: An error occurred`
- Server logs the full exception for debugging

### Best Practices

#### Keepalive for Long Connections

Prevent proxy/load balancer timeouts with periodic keepalive events:

```kotlin
Response.streamingSuccess(flow {
    while (isActive) {
        val data = pollForUpdates()
        if (data != null) {
            emit(SSEEvent.data(data))
        } else {
            emit(SSEEvent.keepalive("heartbeat"))
        }
        delay(15_000) // Every 15 seconds
    }
})
```

#### Resumable Streams with Event IDs

Include event IDs so clients can resume from where they left off:

```kotlin
Response.streamingSuccess(flow {
    var eventId = 0
    dataSource.collect { data ->
        emit(SSEEvent(
            data = data,
            id = "event-${eventId++}"
        ))
    }
})
```

Clients send `Last-Event-ID` header when reconnecting. Access it in your handler to resume from the correct position.

#### Client Reconnection

Set the `retry` field to control client reconnection behavior:

```kotlin
// First event sets reconnection time to 3 seconds
emit(SSEEvent(data = "connected", retry = 3000L))
```

#### Authentication for Long-Lived Connections

SSE connections can remain open for extended periods. For sensitive data, validate authorization periodically:

```kotlin
Response.streamingSuccess(flow {
    while (isActive) {
        // Re-validate auth before sending sensitive data
        if (!authService.isSessionValid(sessionId)) {
            break // Close stream, client will get connection closed
        }
        emit(SSEEvent.data(fetchSensitiveData()))
        delay(5000)
    }
})
```

#### Backpressure and Rate Limiting

Kotlin Flow provides natural backpressure. For rate limiting:

```kotlin
Response.streamingSuccessData(
    dataSource
        .buffer(capacity = 100) // Buffer up to 100 items
        .sample(100)            // Sample every 100ms for high-frequency sources
)
```

Consider connection limits at the server level to prevent resource exhaustion from too many open SSE connections.

### Plain Text Streaming

For plain text SSE (instead of JSON):

```kotlin
val api = Http.get { Root / "log" }
    .output { sse(Ok) { plain { string() } } }

handle(api) {
    Response.streamingSuccessData(flow {
        emit("Log line 1")
        emit("Log line 2")
    })
}
```

### Mixed Response Types

Use `oneOf` to allow both regular and streaming responses:

```kotlin
val api = Http.get { Root / "data" }
    .output {
        oneOf(
            status(Ok) { json { resultSchema } },      // Regular response
            sse(Ok) { json { resultSchema } }         // Or streaming
        )
    }

handle(api) {
    if (shouldStream) {
        Response.streamingSuccessData(dataFlow)
    } else {
        Response.success(singleResult)
    }
}
```
