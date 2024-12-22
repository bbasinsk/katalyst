@file:OptIn(ExperimentalUuidApi::class)

package io.github.bbasinsk.http.ktor3

import io.github.bbasinsk.http.Http
import io.github.bbasinsk.http.openapi.Info
import io.github.bbasinsk.http.openapi.Server
import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.kotlin.uuid
import io.github.bbasinsk.schema.transform
import io.github.bbasinsk.tuple.tupleValues
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.install
import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer
import io.ktor.server.plugins.calllogging.CallLogging
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import kotlin.random.Random
import kotlin.random.nextInt
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid


data class PersonId(val value: Uuid)

data class Person(
    val id: PersonId,
    val name: String,
    val age: Int,
)

fun Schema.Companion.personId(): Schema<PersonId> =
    uuid().transform({ it.value }, ::PersonId)

fun Schema.Companion.person(): Schema<Person> =
    record(
        field(personId(), "id") { id },
        field(string(), "name") { name },
        field(int(), "age") { age },
        ::Person
    )

val updatePerson =
    Http.put { Root / "person" }
        .tag("Person")
        .summary("Update a person")
        .deprecated("some reason")
        .query { schema("id") { uuid().optional() } }
        .header {
            schema("name") { string() }
                .example("asdf", "fds")
                .example("two", "2")
                .deprecated("some reason")
                .description("blah blah")
        }
        .header {
            schema("age") { int() }
                .example("blah", 123)
                .deprecated("some reason")
                .description("blah blah")
        }
        .input { json { person() } }
        .output { status(Ok) { json { person() } } }
        .error { status(NotFound) { json { int() } } }

val findPersonById =
    Http.get { Root / "person" / param("personId") { string() } }
        .tag("Person")
        .output { status(Ok) { json { person() } } }
        .error { status(NotFound) { json { int() } } }

val multipleErrors =
    Http.get { Root / "error" / param("name") { string() } }
        .output { status(Ok.description("asdf")) { json { person() } } }
        .error {
            oneOf<MultipleErrors>(
                case(NotFound.description("Custom not found")) { json { notFoundSchema() } },
                case(BadRequest) { json { badRequestSchema() } },
            )
        }

val avroPersonEcho =
    Http.post { Root / "person" / "avro" / "echo" }
        .tag("Person")
        .input { avro { person() } }
        .output {
            status(Ok) {
                avro { person() }.example(
                    "person a",
                    Person(PersonId(Uuid.parse("00000000-0000-0000-0000-000000000000")), "John Doe", 100)
                )
            }
        }

val avroPersonOut =
    Http.get { Root / "person" / "avro" / "out" }
        .tag("Person")
        .output { status(Ok) { avro { person() } } }

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

    handle(multipleErrors) { req ->
        val (name) = tupleValues(req.params)
        when (name) {
            "not-found" -> error(MultipleErrors.NotFound(Random.nextInt()))
            "bad-request" -> error(MultipleErrors.BadRequest("Bad request"))
            else -> success(domainStuff())
        }
    }

    handle(avroPersonEcho) { request ->
        println(request.input)
        success(request.input)
    }

    handle(avroPersonOut) {
        val person = Person(
            id = PersonId(Uuid.parse("00000000-0000-0000-0000-000000000000")),
            name = "John Doe",
            age = 100
        )
        success(person)
    }
}


fun main() {
    embeddedServer(CIO, port = 33333) {
        install(CallLogging)
        install(ContentNegotiation) {
            json()
        }

        val domainService = {
            Person(
                id = PersonId(Uuid.random()),
                name = "John Doe",
                age = Random.nextInt(0..100)
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
