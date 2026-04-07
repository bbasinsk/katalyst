@file:OptIn(ExperimentalUuidApi::class)

package io.github.bbasinsk.http.ktor3

import io.github.bbasinsk.http.HttpEndpointGroup
import io.github.bbasinsk.http.header
import io.github.bbasinsk.http.query
import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.kotlin.uuid
import io.github.bbasinsk.schema.transform
import io.github.bbasinsk.tuple.tupleValues
import kotlin.random.Random
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid


data class PersonId(val value: Uuid)

data class Person(
    val id: PersonId,
    val name: String,
    val age: Int,
)

fun Schema.Companion.personId(): Schema<PersonId> =
    uuid().transform(::PersonId) { it.value }

fun Schema.Companion.person(): Schema<Person> =
    record(
        field(personId(), "id") { id },
        field(string(), "name") { name },
        field(int(), "age") { age },
        ::Person
    )

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

object PersonEndpoints : HttpEndpointGroup("Person") {
    val findPersonById = http {
        get { Root / "person" / param("personId") { string() } }
            .output { status(Ok) { json { person() } } }
            .error { status(NotFound) { plain { int() } } }
    }

    val personIdAndThing = http {
        get { Root / param("personId") { string() } / "blah" / param("thing") { int() } }
            .output { status(Ok) { json { person() } } }
            .error { status(NotFound) { plain { int() }.example("test1", 123) } }
    }

    val updatePerson = http {
        put { Root / "person" }
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
    }

    val multipleErrors = http {
        get { Root / "error" / param("name") { string() } }
            .output { status(Ok.description("asdf")) { json { person() } } }
            .error {
                oneOf<MultipleErrors>(
                    case(NotFound.description("Custom not found")) { json { notFoundSchema() } },
                    case(BadRequest) { json { badRequestSchema() } },
                )
            }
    }

    val emptyResponse = http {
        get { Root / "empty" }
            .output { status(Ok) { empty() } }
            .error { status(NoContent) { empty() } }
    }

    val avroPersonEcho = http {
        post { Root / "person" / "avro" / "echo" }
            .input { avro { person() } }
            .output {
                status(Ok) {
                    avro { person() }.example(
                        "person a",
                        Person(PersonId(Uuid.parse("00000000-0000-0000-0000-000000000000")), "John Doe", 100)
                    )
                }
            }
    }

    val avroPersonOut = http {
        get { Root / "person" / "avro" / "out" }
            .output { status(Ok) { avro { person() } } }
    }
}

fun HttpEndpoints.personEndpoints(domainStuff: () -> Person) {
    handle(PersonEndpoints.findPersonById) { request ->
        val (name) = tupleValues(request.params)
        success(domainStuff().copy(name = name))
    }

    handle(PersonEndpoints.personIdAndThing) { request ->
        val (personId, thing) = tupleValues(request.params)
        println("personId: $personId, thing: $thing")
        success(domainStuff().copy(name = personId))
    }

    handle(PersonEndpoints.updatePerson) { request ->
        val (id, name) = tupleValues(request.params)
        println("id: $id, name: $name")
        success(domainStuff().copy(name = request.input.name))
    }

    handle(PersonEndpoints.multipleErrors) { req ->
        val (name) = tupleValues(req.params)
        when (name) {
            "not-found" -> error(MultipleErrors.NotFound(Random.nextInt()))
            "bad-request" -> error(MultipleErrors.BadRequest("Bad request"))
            else -> success(domainStuff())
        }
    }

    handle(PersonEndpoints.emptyResponse) {
        if (Random.nextBoolean()) success() else error()
    }

    handle(PersonEndpoints.avroPersonEcho) { request ->
        println(request.input)
        success(request.input)
    }

    handle(PersonEndpoints.avroPersonOut) {
        val person = Person(
            id = PersonId(Uuid.parse("00000000-0000-0000-0000-000000000000")),
            name = "John Doe",
            age = 100
        )
        success(person)
    }
}
