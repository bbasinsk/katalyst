# Http

Type-safe HTTP API definitions with support for authentication and streaming via Server-Sent Events (SSE).

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

### Optional Authentication

Wrap any auth type with `.optional()` to allow unauthenticated access:

```kotlin
val api = Http.get { Root / "content" }
    .auth { bearer<User>().optional() }
    .output { status(Ok) { json { contentSchema } } }

// In handler, auth is nullable
handle(api, validator) { request ->
    val user: User? = request.auth
    if (user != null) {
        Response.success(personalizedContent(user))
    } else {
        Response.success(publicContent())
    }
}
```

### Implementing Validators

Provide an `AuthValidator` when registering handlers:

```kotlin
val bearerValidator = AuthValidator<User> { token ->
    // Validate JWT and extract user, return null if invalid
    jwtService.validateAndDecode(token)
}

val basicValidator = AuthValidator<Credentials> { base64Credentials ->
    // Note: Credentials are passed as base64-encoded "username:password"
    // You must decode before validating
    val decoded = Base64.getDecoder().decode(base64Credentials).decodeToString()
    val (username, password) = decoded.split(":", limit = 2)
    authService.authenticate(username, password)
}

val apiKeyValidator = AuthValidator<ApiToken> { key ->
    // Look up API key and return token info
    apiKeyService.validate(key)
}

val cookieValidator = AuthValidator<Session> { cookieValue ->
    // Look up session by cookie value
    sessionService.validateSession(cookieValue)
}
```

### OpenAPI Generation

Auth schemas automatically generate OpenAPI security schemes:

```json
{
  "components": {
    "securitySchemes": {
      "bearerAuth": { "type": "http", "scheme": "bearer", "bearerFormat": "JWT" },
      "basicAuth": { "type": "http", "scheme": "basic" },
      "apiKeyAuth": { "type": "apiKey", "in": "header", "name": "X-API-Key" },
      "cookieAuth": { "type": "apiKey", "in": "cookie", "name": "token" }
    }
  }
}
```

#### Custom Scheme Names

Use the `schemeName` parameter to avoid collisions when multiple endpoints use different auth configurations of the same type:

```kotlin
// Two Bearer auth endpoints with different configurations
val jwtEndpoint = Http.get { Root / "jwt-protected" }
    .auth { bearer<User>(format = "JWT", schemeName = "jwtAuth") }

val opaqueEndpoint = Http.get { Root / "opaque-protected" }
    .auth { bearer<User>(schemeName = "opaqueAuth") }

// Two API Key endpoints with different header names
val apiKeyEndpoint = Http.get { Root / "api" }
    .auth { apiKeyHeader<ApiToken>("X-API-Key", schemeName = "apiKeyAuth") }

val customKeyEndpoint = Http.get { Root / "custom" }
    .auth { apiKeyHeader<ApiToken>("X-Custom-Key", schemeName = "customKeyAuth") }
```

This generates distinct security schemes in OpenAPI:

```json
{
  "securitySchemes": {
    "jwtAuth": { "type": "http", "scheme": "bearer", "bearerFormat": "JWT" },
    "opaqueAuth": { "type": "http", "scheme": "bearer" },
    "apiKeyAuth": { "type": "apiKey", "in": "header", "name": "X-API-Key" },
    "customKeyAuth": { "type": "apiKey", "in": "header", "name": "X-Custom-Key" }
  }
}
```

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
    .output { sse { json { messageSchema } } }
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
    .error { sse { json { errorSchema } } }

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
    .output { sse { plain { string() } } }

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
            sse { json { resultSchema } }         // Or streaming
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
