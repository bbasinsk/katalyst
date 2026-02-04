package io.github.bbasinsk.http.ktor3

import io.github.bbasinsk.http.AuthHandler
import io.github.bbasinsk.http.Http
import io.github.bbasinsk.http.Response
import io.github.bbasinsk.http.auth
import io.github.bbasinsk.http.optional
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class AuthTest {
    data class User(val id: String, val name: String)

    private val userHandler = AuthHandler.standard<User> { token ->
        if (token == "valid-token") User("1", "Test User") else null
    }

    private val basicHandler = AuthHandler.standard<User> { base64Credentials ->
        if (base64Credentials == "dXNlcjpwYXNz") User("1", "Basic User") else null
    }

    private val apiKeyHandler = AuthHandler.standard<User> { key ->
        if (key == "secret-api-key") User("1", "API User") else null
    }

    private val cookieHandler = AuthHandler.standard<User> { cookieValue ->
        if (cookieValue == "valid-session") User("1", "Cookie User") else null
    }

    @Test
    fun bearerAuthSuccess() = testApplication {
        val api = Http.get { Root / "profile" }
            .auth { bearer<User>() }
            .output { status(Ok) { plain { string() } } }

        application {
            endpoints {
                handle(api, userHandler) { request ->
                    Response.success("Hello ${request.auth.name}")
                }
            }
        }

        val response = client.get("/profile") {
            header("Authorization", "Bearer valid-token")
        }
        assertEquals(200, response.status.value)
        assertEquals("Hello Test User", response.bodyAsText())
    }

    @Test
    fun bearerAuthMissingReturns401() = testApplication {
        val api = Http.get { Root / "profile" }
            .auth { bearer<User>() }
            .output { status(Ok) { plain { string() } } }

        application {
            endpoints {
                handle(api, userHandler) { request ->
                    Response.success("Hello ${request.auth.name}")
                }
            }
        }

        val response = client.get("/profile")
        assertEquals(401, response.status.value)
    }

    @Test
    fun basicAuthSuccess() = testApplication {
        val api = Http.get { Root / "admin" }
            .auth { basic<User>() }
            .output { status(Ok) { plain { string() } } }

        application {
            endpoints {
                handle(api, basicHandler) { request ->
                    Response.success("Hello ${request.auth.name}")
                }
            }
        }

        val response = client.get("/admin") {
            header("Authorization", "Basic dXNlcjpwYXNz")
        }
        assertEquals(200, response.status.value)
        assertEquals("Hello Basic User", response.bodyAsText())
    }

    @Test
    fun apiKeyHeaderAuthSuccess() = testApplication {
        val api = Http.get { Root / "data" }
            .auth { apiKeyHeader<User>("X-API-Key") }
            .output { status(Ok) { plain { string() } } }

        application {
            endpoints {
                handle(api, apiKeyHandler) { request ->
                    Response.success("Hello ${request.auth.name}")
                }
            }
        }

        val response = client.get("/data") {
            header("X-API-Key", "secret-api-key")
        }
        assertEquals(200, response.status.value)
        assertEquals("Hello API User", response.bodyAsText())
    }

    @Test
    fun apiKeyHeaderMissingReturns401() = testApplication {
        val api = Http.get { Root / "data" }
            .auth { apiKeyHeader<User>("X-API-Key") }
            .output { status(Ok) { plain { string() } } }

        application {
            endpoints {
                handle(api, apiKeyHandler) { request ->
                    Response.success("Hello ${request.auth.name}")
                }
            }
        }

        val response = client.get("/data")
        assertEquals(401, response.status.value)
    }

    @Test
    fun cookieAuthSuccess() = testApplication {
        val api = Http.get { Root / "dashboard" }
            .auth { cookie<User>("session") }
            .output { status(Ok) { plain { string() } } }

        application {
            endpoints {
                handle(api, cookieHandler) { request ->
                    Response.success("Hello ${request.auth.name}")
                }
            }
        }

        val response = client.get("/dashboard") {
            cookie("session", "valid-session")
        }
        assertEquals(200, response.status.value)
        assertEquals("Hello Cookie User", response.bodyAsText())
    }

    @Test
    fun cookieAuthMissingReturns401() = testApplication {
        val api = Http.get { Root / "dashboard" }
            .auth { cookie<User>("session") }
            .output { status(Ok) { plain { string() } } }

        application {
            endpoints {
                handle(api, cookieHandler) { request ->
                    Response.success("Hello ${request.auth.name}")
                }
            }
        }

        val response = client.get("/dashboard")
        assertEquals(401, response.status.value)
    }

    @Test
    fun cookieAuthInvalidReturns401() = testApplication {
        val api = Http.get { Root / "dashboard" }
            .auth { cookie<User>("session") }
            .output { status(Ok) { plain { string() } } }

        application {
            endpoints {
                handle(api, cookieHandler) { request ->
                    Response.success("Hello ${request.auth.name}")
                }
            }
        }

        val response = client.get("/dashboard") {
            cookie("session", "invalid-session")
        }
        assertEquals(401, response.status.value)
    }

    @Test
    fun optionalCookieAuthWithValidCookie() = testApplication {
        val api = Http.get { Root / "content" }
            .auth { cookie<User>("session").optional() }
            .output { status(Ok) { plain { string() } } }

        application {
            endpoints {
                handle(api, cookieHandler) { request ->
                    val greeting = request.auth?.name ?: "Anonymous"
                    Response.success("Hello $greeting")
                }
            }
        }

        val response = client.get("/content") {
            cookie("session", "valid-session")
        }
        assertEquals(200, response.status.value)
        assertEquals("Hello Cookie User", response.bodyAsText())
    }

    @Test
    fun optionalCookieAuthWithoutCookieReturnsNull() = testApplication {
        val api = Http.get { Root / "content" }
            .auth { cookie<User>("session").optional() }
            .output { status(Ok) { plain { string() } } }

        application {
            endpoints {
                handle(api, cookieHandler) { request ->
                    val greeting = request.auth?.name ?: "Anonymous"
                    Response.success("Hello $greeting")
                }
            }
        }

        val response = client.get("/content")
        assertEquals(200, response.status.value)
        assertEquals("Hello Anonymous", response.bodyAsText())
    }

    @Test
    fun optionalAuthWithValidToken() = testApplication {
        val api = Http.get { Root / "feed" }
            .auth { bearer<User>().optional() }
            .output { status(Ok) { plain { string() } } }

        application {
            endpoints {
                handle(api, userHandler) { request ->
                    val greeting = request.auth?.name ?: "Anonymous"
                    Response.success("Hello $greeting")
                }
            }
        }

        val response = client.get("/feed") {
            header("Authorization", "Bearer valid-token")
        }
        assertEquals(200, response.status.value)
        assertEquals("Hello Test User", response.bodyAsText())
    }

    @Test
    fun optionalAuthWithoutTokenReturnsNull() = testApplication {
        val api = Http.get { Root / "feed" }
            .auth { bearer<User>().optional() }
            .output { status(Ok) { plain { string() } } }

        application {
            endpoints {
                handle(api, userHandler) { request ->
                    val greeting = request.auth?.name ?: "Anonymous"
                    Response.success("Hello $greeting")
                }
            }
        }

        val response = client.get("/feed")
        assertEquals(200, response.status.value)
        assertEquals("Hello Anonymous", response.bodyAsText())
    }

    @Test
    fun optionalAuthWithInvalidTokenReturnsNull() = testApplication {
        val api = Http.get { Root / "feed" }
            .auth { bearer<User>().optional() }
            .output { status(Ok) { plain { string() } } }

        application {
            endpoints {
                handle(api, userHandler) { request ->
                    val greeting = request.auth?.name ?: "Anonymous"
                    Response.success("Hello $greeting")
                }
            }
        }

        val response = client.get("/feed") {
            header("Authorization", "Bearer invalid-token")
        }
        assertEquals(200, response.status.value)
        assertEquals("Hello Anonymous", response.bodyAsText())
    }

    @Test
    fun authWithRedirectOnFailure() = testApplication {
        val api = Http.get { Root / "protected" }
            .auth { bearer<User>() }
            .output { status(Ok) { plain { string() } } }

        val redirectHandler = AuthHandler.withRedirect<User>("/login") { token ->
            if (token == "valid-token") User("1", "Test User") else null
        }

        application {
            endpoints {
                handle(api, redirectHandler) { request ->
                    Response.success("Hello ${request.auth.name}")
                }
            }
        }

        val noRedirectClient = createClient {
            followRedirects = false
        }
        val response = noRedirectClient.get("/protected")
        assertEquals(302, response.status.value)
        assertEquals("/login", response.headers["Location"])
    }

    @Test
    fun authWithStaticHandler() = testApplication {
        val api = Http.get { Root / "dev" }
            .auth { bearer<User>() }
            .output { status(Ok) { plain { string() } } }

        val devHandler = AuthHandler.static(User("dev", "Dev User"))

        application {
            endpoints {
                handle(api, devHandler) { request ->
                    Response.success("Hello ${request.auth.name}")
                }
            }
        }

        val response = client.get("/dev")
        assertEquals(200, response.status.value)
        assertEquals("Hello Dev User", response.bodyAsText())
    }

    @Test
    fun staticIgnoresProvidedToken() = testApplication {
        val api = Http.get { Root / "dev" }
            .auth { bearer<User>() }
            .output { status(Ok) { plain { string() } } }

        val devHandler = AuthHandler.static(User("dev", "Dev User"))

        application {
            endpoints {
                handle(api, devHandler) { request ->
                    Response.success("Hello ${request.auth.name}")
                }
            }
        }

        val response = client.get("/dev") {
            header("Authorization", "Bearer any-token-is-ignored")
        }
        assertEquals(200, response.status.value)
        assertEquals("Hello Dev User", response.bodyAsText())
    }

    @Test
    fun withRedirectInvalidTokenReturnsRedirect() = testApplication {
        val api = Http.get { Root / "protected" }
            .auth { bearer<User>() }
            .output { status(Ok) { plain { string() } } }

        val redirectHandler = AuthHandler.withRedirect<User>("/login") { token ->
            if (token == "valid-token") User("1", "Test User") else null
        }

        application {
            endpoints {
                handle(api, redirectHandler) { request ->
                    Response.success("Hello ${request.auth.name}")
                }
            }
        }

        val noRedirectClient = createClient {
            followRedirects = false
        }
        val response = noRedirectClient.get("/protected") {
            header("Authorization", "Bearer invalid-token")
        }
        assertEquals(302, response.status.value)
        assertEquals("/login", response.headers["Location"])
    }

    @Test
    fun withRedirectValidTokenReturnsSuccess() = testApplication {
        val api = Http.get { Root / "protected" }
            .auth { bearer<User>() }
            .output { status(Ok) { plain { string() } } }

        val redirectHandler = AuthHandler.withRedirect<User>("/login") { token ->
            if (token == "valid-token") User("1", "Test User") else null
        }

        application {
            endpoints {
                handle(api, redirectHandler) { request ->
                    Response.success("Hello ${request.auth.name}")
                }
            }
        }

        val response = client.get("/protected") {
            header("Authorization", "Bearer valid-token")
        }
        assertEquals(200, response.status.value)
        assertEquals("Hello Test User", response.bodyAsText())
    }

    @Test
    fun standardHandlerCatchesValidationExceptions() = testApplication {
        val api = Http.get { Root / "profile" }
            .auth { bearer<User>() }
            .output { status(Ok) { plain { string() } } }

        val throwingHandler = AuthHandler.standard<User> { _ ->
            throw RuntimeException("JWT parsing failed")
        }

        application {
            endpoints {
                handle(api, throwingHandler) { request ->
                    Response.success("Hello ${request.auth.name}")
                }
            }
        }

        val response = client.get("/profile") {
            header("Authorization", "Bearer malformed-token")
        }
        assertEquals(401, response.status.value)
    }

    @Test
    fun optionalAuthWithRedirectHandlerAndInvalidTokenReturnsNull() = testApplication {
        val api = Http.get { Root / "feed" }
            .auth { bearer<User>().optional() }
            .output { status(Ok) { plain { string() } } }

        val redirectHandler = AuthHandler.withRedirect<User>("/login") { token ->
            if (token == "valid-token") User("1", "Test User") else null
        }

        application {
            endpoints {
                handle(api, redirectHandler) { request ->
                    val greeting = request.auth?.name ?: "Anonymous"
                    Response.success("Hello $greeting")
                }
            }
        }

        val response = client.get("/feed") {
            header("Authorization", "Bearer invalid-token")
        }
        assertEquals(200, response.status.value)
        assertEquals("Hello Anonymous", response.bodyAsText())
    }

    @Test
    fun withRedirectHandlerCatchesValidationExceptions() = testApplication {
        val api = Http.get { Root / "protected" }
            .auth { bearer<User>() }
            .output { status(Ok) { plain { string() } } }

        val throwingHandler = AuthHandler.withRedirect<User>("/login") { _ ->
            throw RuntimeException("JWT parsing failed")
        }

        application {
            endpoints {
                handle(api, throwingHandler) { request ->
                    Response.success("Hello ${request.auth.name}")
                }
            }
        }

        val noRedirectClient = createClient {
            followRedirects = false
        }
        val response = noRedirectClient.get("/protected") {
            header("Authorization", "Bearer malformed-token")
        }
        assertEquals(302, response.status.value)
        assertEquals("/login", response.headers["Location"])
    }
}
