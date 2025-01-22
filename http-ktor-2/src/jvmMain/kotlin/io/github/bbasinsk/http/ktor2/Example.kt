package io.github.bbasinsk.http.ktor2

import io.github.bbasinsk.http.Http
import io.github.bbasinsk.http.openapi.Info
import io.github.bbasinsk.http.openapi.Server
import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.java.instant
import io.github.bbasinsk.schema.java.localDate
import io.github.bbasinsk.schema.java.uuid
import io.github.bbasinsk.schema.transform
import io.github.bbasinsk.tuple.tupleValues
import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer
import java.time.Instant
import java.time.LocalDate
import java.util.UUID
import kotlin.random.Random


data class PersonId(val value: UUID)

data class Person(
    val id: PersonId,
    val name: String,
    val birthDate: LocalDate,
    val createdAt: Instant
)

fun Schema.Companion.personId(): Schema<PersonId> =
    uuid().transform(::PersonId) { it.value }

fun Schema.Companion.person(): Schema<Person> =
    record(
        field(personId(), "id") { id },
        field(string(), "name") { name },
        field(localDate(), "birthDate") { birthDate },
        field(instant(), "createdAt") { createdAt },
        ::Person
    )

val updatePerson =
    Http.put { Root / "person" }
        .tag("Person")
        .summary("Update a person")
        .deprecated("some reason")
        .query {
            schema("id") { int().optional() }
        }
        .header {
            schema("name") { string() }
                .example("asdf", "fds")
                .example("two", "2")
                .deprecated("some reason")
                .description("blah blah")
        }
        .header {
            schema("other") { int() }
                .example("blah", 123)
                .deprecated("some reason")
                .description("blah blah")
        }
        .input { json { person() } }
        .output { status(Ok) { json { person() } } }
        .error { status(NotFound) { json { int() } } }

val findPersonById =
    Http.get { Root / "person" / param("personId") { string() } }
        .output { status(Ok) { json { person() } } }
        .error { status(NotFound) { json { int() } } }

val multipleErrors =
    Http.get { Root / "error" / param("name") { string() } }
        .output { status(Ok.description("asdf")) { json { person() } } }
        .error {
            oneOf(
                case(NotFound.description("Custom not found")) { json { notFoundSchema() } },
                case(BadRequest) { json { badRequestSchema() } }
            )
        }

sealed interface MultipleErrors {
    data class NotFound(val id: Int) : MultipleErrors
    data class BadRequest(val message: String) : MultipleErrors
}

fun Schema.Companion.notFoundSchema(): Schema<MultipleErrors.NotFound> =
    record(
        field(int(), "id") { id },
        MultipleErrors::NotFound
    )

fun Schema.Companion.badRequestSchema(): Schema<MultipleErrors.BadRequest> =
    record(
        field(string(), "message") { message },
        MultipleErrors::BadRequest
    )

fun HttpEndpoints.exampleEndpoints(domainStuff: () -> Person) = httpEndpoints {
    handle(findPersonById) { request ->
        val (name) = tupleValues(request.params)
        success(domainStuff().copy(name = name))
    }

    handle(
        Http.get { Root / param("personId") { string() } / "blah" / param("thing") { int() } }
            .output { status(Ok) { json { person() } } }
            .error { status(NotFound) { json { int() }.example("test1", 123) } }
    ) { request ->
        val (personId, thing) = tupleValues(request.params)
        println("personId: $personId, thing: $thing")
        success(domainStuff().copy(name = personId))
    }

    handle(updatePerson) { request ->
        val (id, name) = tupleValues(request.params)
        println("id: $id, name: $name")
        success(domainStuff().copy(name = request.input.name))
    }

    handle(
        Http.get { Root / "v1" / "estimated-delivery-date" / "package-id" / param("packageId") { string() } }
            .output { status(Ok) { json { person() } } }
            .error { status(NotFound) { json { int() } } }
    ) { request ->
        val (name) = tupleValues(request.params)
        success(domainStuff().copy(name = name))
    }

    handle(multipleErrors) { req ->
        val (name) = tupleValues(req.params)
        when (name) {
            "not-found" -> kotlin.error(MultipleErrors.NotFound(Random.nextInt()))
            "bad-request" -> kotlin.error(MultipleErrors.BadRequest("Bad request"))
            else -> success(domainStuff())
        }
    }
}

fun main() {
    embeddedServer(CIO, port = 33333) {

        val domainService = {
            Person(
                id = PersonId(UUID.randomUUID()),
                name = "John Doe",
                birthDate = LocalDate.now(),
                createdAt = Instant.now()
            )
        }

        endpoints {
            openApi(
                jsonSpecPath = "/openapi.json",
                info = Info(
                    title = "Person API",
                    version = "1.0.0",
                    description = "Molestiae occaecati molestiae at aut et consequatur ut facere commodi et eos fugiat. Vitae et labore est ex. Facere sed numquam blanditiis vel dolores sint sint ut quae officiis et. Alias dolorem sit odit aut animi ut eos consequatur sit quod suscipit est eum. Modi quo aliquid impedit architecto et laborum odit non non est et. Possimus in animi sunt maxime impedit ex ut odio qui odit laborum consequatur autem omnis quaerat. Nam dicta commodi rerum fuga ducimus et quas aut molestias illum. Ex asperiores perspiciatis facere voluptate est architecto non est ex quos unde adipisci vel ratione. Aut a ea placeat inventore atque id quisquam velit facere."
                ),
                servers = listOf(
                    Server(url = "http://localhost:33333")
                )
            )
            exampleEndpoints(domainService)
        }
    }.start(wait = true)
}
