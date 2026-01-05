package io.github.bbasinsk.http.ktor3

import io.github.bbasinsk.http.AuthValidator
import io.github.bbasinsk.http.Http
import io.github.bbasinsk.http.Response
import io.github.bbasinsk.http.auth
import io.github.bbasinsk.http.optional
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class AuthTest {
    data class User(val id: String, val name: String)

    private val userValidator = AuthValidator<User> { token ->
        if (token == "valid-token") User("1", "Test User") else null
    }

    private val basicValidator = AuthValidator<User> { base64Credentials ->
        if (base64Credentials == "dXNlcjpwYXNz") User("1", "Basic User") else null
    }

    private val apiKeyValidator = AuthValidator<User> { key ->
        if (key == "secret-api-key") User("1", "API User") else null
    }

    private val cookieValidator = AuthValidator<User> { cookieValue ->
        if (cookieValue == "valid-session") User("1", "Cookie User") else null
    }

    @Test
    fun bearerAuthSuccess() = testApplication {
        val api = Http.get { Root / "profile" }
            .auth { bearer<User>() }
            .output { status(Ok) { plain { string() } } }

        application {
            endpoints {
                handle(api, userValidator) { request ->
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
                handle(api, userValidator) { request ->
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
                handle(api, basicValidator) { request ->
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
                handle(api, apiKeyValidator) { request ->
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
                handle(api, apiKeyValidator) { request ->
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
                handle(api, cookieValidator) { request ->
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
                handle(api, cookieValidator) { request ->
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
                handle(api, cookieValidator) { request ->
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
                handle(api, cookieValidator) { request ->
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
                handle(api, cookieValidator) { request ->
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
                handle(api, userValidator) { request ->
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
                handle(api, userValidator) { request ->
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
                handle(api, userValidator) { request ->
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
}
