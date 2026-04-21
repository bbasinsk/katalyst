# Http Client (Ktor 3)

Type-safe HTTP client that turns shared `Http` endpoint definitions into callable client methods, powered by Ktor 3.

## Setup

```kotlin
// build.gradle.kts
dependencies {
    implementation("io.github.bbasinsk:http-client-ktor-3:$katalystVersion")
}
```

Create a client instance by wrapping any Ktor `HttpClient`:

```kotlin
val httpClient = HttpClient(CIO) {
    // configure as needed
}
val client = KatalystClient(httpClient)
```

## Defining Endpoints

Endpoints are defined once with the `Http` DSL and shared between client and server. The definition describes the HTTP method, path, params, input/output bodies, errors, and auth:

```kotlin
data class User(val name: String, val age: Int)

val userSchema = Schema.record(
    Schema.field(Schema.string(), "name") { name },
    Schema.field(Schema.int(), "age") { age },
    ::User
)

val getUser = Http.get { Root / "users" / param("id") { int() } }
    .output { status(Ok) { json { userSchema } } }

val createUser = Http.post { Root / "users" }
    .input { json { userSchema } }
    .output { status(Created) { json { userSchema } } }

val searchUsers = Http.get { Root / "search" }
    .query { schema("q") { string() } }
    .output { status(Ok) { plain { string() } } }
```

## Making Requests

Call `call()` with the endpoint definition, path/query params, and an optional request body:

```kotlin
// GET with path params
val result = client.call(getUser, 42)

// POST with JSON body
val result = client.call(createUser, User("Alice", 30))

// GET with query params
val result = client.call(searchUsers, "hello")
```

### Handling Results

Every `call()` returns an `HttpResult<E, O>`:

```kotlin
sealed interface HttpResult<out E, out A> {
    data class Success<A>(val value: A) : HttpResult<Nothing, A>
    data class Failure<E>(val status: Int, val error: E) : HttpResult<E, Nothing>
    data class NetworkError(val cause: Throwable) : HttpResult<Nothing, Nothing>
}
```

- **`Success`** - Response matched a declared output status and body was decoded
- **`Failure`** - Response matched a declared error status; typed error is available
- **`NetworkError`** - Connection failure, timeout, or malformed response body

```kotlin
when (result) {
    is HttpResult.Success -> println("Got user: ${result.value}")
    is HttpResult.Failure -> println("Error ${result.status}: ${result.error}")
    is HttpResult.NetworkError -> println("Network error: ${result.cause.message}")
}
```

### Error Responses

Define error schemas on the endpoint to get typed error handling:

```kotlin
data class ErrorMessage(val message: String)

val api = Http.get { Root / "fail" }
    .output { status(Ok) { json { userSchema } } }
    .error { status(NotFound) { json { errorSchema } } }

val result = client.call(api)
// result is HttpResult.Failure<ErrorMessage> when server returns 404
```

## SSE Streaming

For endpoints that return Server-Sent Events, use `stream()`. It suspends until the server responds with headers, then returns an `HttpResult<E, Flow<SSEEvent<O>>>` — mirroring `call()`'s result shape, with the success case carrying the event flow:

```kotlin
val streamApi = Http.get { Root / "stream" }
    .output { sse(Ok) { json { string() } } }
    .error { status(Conflict) { json { errorSchema } } }

when (val result = client.stream(streamApi)) {
    is HttpResult.Failure -> println("HTTP ${result.status}: ${result.error}")
    is HttpResult.NetworkError -> println("Transport error: ${result.cause}")
    is HttpResult.Success -> result.value
        .catch { e -> println("Mid-stream error: $e") }
        .collect { event -> println("Received: ${event.data}") }
}
```

A typed `Failure` or pre-flight `NetworkError` can only be the terminal outcome — once the connection is established, events flow through `Success` and mid-stream transport errors surface as exceptions on the inner flow (handle via `.catch { }`).

Each `SSEEvent` carries optional `data`, `event`, `id`, `retry`, and `comment` fields.

## Authentication

Pass an `AuthCredential` to `call()` or `stream()` to attach auth headers:

```kotlin
val api = Http.get { Root / "protected" }
    .auth { bearer<String>() }
    .output { status(Ok) { plain { string() } } }

// Bearer token
client.call(api, auth = AuthCredential.BearerToken("my-token"))

// Basic auth
client.call(api, auth = AuthCredential.BasicCredentials(encoded))

// API key header
client.call(api, auth = AuthCredential.ApiKey("my-key"))

// Cookie
client.call(api, auth = AuthCredential.CookieValue("session-id"))
```

The credential type must match the endpoint's auth schema — mismatches throw at runtime.

For composite auth (`or`), the client matches the credential type to the first compatible scheme:

```kotlin
val api = Http.get { Root / "profile" }
    .auth { bearer<String>() or cookie("session") }
    .output { status(Ok) { plain { string() } } }

// BearerToken matches the bearer scheme
client.call(api, auth = AuthCredential.BearerToken("my-token"))

// CookieValue matches the cookie scheme
client.call(api, auth = AuthCredential.CookieValue("session-id"))
```
