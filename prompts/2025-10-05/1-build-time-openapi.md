I have this project that defines a declarative HTTP endpoint `Http`.
- @http/src/commonMain/kotlin/io/github/bbasinsk/http/Http.kt 

I then have an implementation of that Http endpoint in `HttpEndpoint`.
- @http/src/commonMain/kotlin/io/github/bbasinsk/http/HttpEndpoint.kt 

And we configure it for Ktor routing:
- @http-ktor-3/src/commonMain/kotlin/io/github/bbasinsk/http/ktor3/HttpEndpoints.kt
- @http-ktor-3/src/commonMain/kotlin/io/github/bbasinsk/http/ktor3/Endpoints.kt

This has worked nicely for runtime OpenAPI generation (i.e.: serving up the OpenAPI spec).
- @http-ktor-3/src/jvmMain/kotlin/io/github/bbasinsk/http/ktor3/Example.kt

---

However, now I'd like to generate the OpenAPI spec at build time. 
This means that I need a way to accumulate the Http definitions at build time, without constructing all the service dependencies that are needed to define the Http handlers.

The simplest solution would be to define a list of Http definitions and then generate the OpenAPI spec from that, but that is not ideal because it requires all the Http endpoints to two places:
```kt 
val allHttp: Http<*, *, *, *> = listOf(
    findPersonById,
    // ...
)

fun HttpEndpoints.exampleEndpoints(domainStuff: () -> Person) = httpEndpoints("Person") {
    handle(findPersonById) { request ->
        val (name) = tupleValues(request.params)
        success(domainStuff().copy(name = name))
    }
}
```

How can we re-design so that we get a single place to define a group of implemented Http endpoints, with each endpoint grouping defining a static list of Http definitions?
