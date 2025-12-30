package io.github.bbasinsk.http.ktor3

import io.github.bbasinsk.http.*
import io.github.bbasinsk.schema.Schema
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.plugins.contentnegotiation.*

/**
 * Example demonstrating type-safe Bearer authentication with Katalyst HTTP.
 *
 * Key concepts:
 * 1. AuthSchema defines WHAT auth is required (Bearer, etc.) with phantom type for the principal
 * 2. AuthValidator is provided at handler registration to actually validate tokens
 * 3. The Request.auth field is type-safe - its type matches the AuthSchema declaration
 *
 * Test with:
 * ```
 * curl http://localhost:33334/public                                    # works
 * curl http://localhost:33334/profile                                   # 401
 * curl -H "Authorization: Bearer secret-token" localhost:33334/profile  # works
 * curl -H "Authorization: Bearer wrong" localhost:33334/profile         # 401
 * curl http://localhost:33334/feed                                      # works (anonymous)
 * curl -H "Authorization: Bearer secret-token" localhost:33334/feed     # works (personalized)
 * ```
 */

// The authenticated user type
data class User(val id: String, val name: String)

fun Schema.Companion.user(): Schema<User> = record(
    field(string(), "id") { id },
    field(string(), "name") { name },
    ::User
)

// Static API definitions - no runtime dependencies needed
object AuthExampleEndpoints : HttpEndpointGroup("Auth Example") {

    // Public endpoint - Auth = Unit (no auth)
    val publicEndpoint = http {
        Http.get { Root / "public" }
            .output { status(Ok) { plain { string() } } }
    }

    // Protected endpoint - Auth = User (phantom type)
    val profileEndpoint = http {
        Http.get { Root / "profile" }
            .output { status(Ok) { json { user() } } }
            .auth { bearer<User>() }
    }

    // Optional auth endpoint - Auth = User?
    val feedEndpoint = http {
        Http.get { Root / "feed" }
            .output { status(Ok) { plain { string() } } }
            .auth { bearer<User>().optional() }
    }
}

fun main() {
    // Runtime: validator is a dependency that knows how to validate tokens
    val userValidator = AuthValidator<User> { token ->
        // In production, this would decode a JWT, check a database, etc.
        if (token == "secret-token") {
            User(id = "user-123", name = "John Doe")
        } else {
            null
        }
    }

    embeddedServer(CIO, port = 33334) {
        install(ContentNegotiation) { json() }

        endpoints {
            // Public - no validator needed
            handle(AuthExampleEndpoints.publicEndpoint) {
                success("This is public content")
            }

            // Protected - validator required, type must match
            handle(AuthExampleEndpoints.profileEndpoint, userValidator) { req ->
                val user: User = req.auth  // Type-safe, guaranteed non-null
                success(user)
            }

            // Optional auth - validator provided, framework handles null case
            handle(AuthExampleEndpoints.feedEndpoint, userValidator) { req ->
                val user: User? = req.auth  // Type-safe, nullable
                if (user != null) {
                    success("Personalized feed for ${user.name}")
                } else {
                    success("Anonymous feed")
                }
            }
        }
    }.start(wait = true)
}
