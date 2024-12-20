@file:OptIn(ExperimentalUuidApi::class)

package io.github.bbasinsk.http.ktor

import io.github.bbasinsk.http.HeaderSchema
import io.github.bbasinsk.http.Http
import io.github.bbasinsk.http.QuerySchema
import io.github.bbasinsk.http.ResponseSchema.Companion.oneOf
import io.github.bbasinsk.http.ResponseSchema.Companion.status
import io.github.bbasinsk.http.ResponseStatus
import io.github.bbasinsk.http.openapi.Info
import io.github.bbasinsk.http.openapi.Server
import io.github.bbasinsk.http.responseCase
import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.kotlin.uuid
import io.github.bbasinsk.schema.transform
import io.github.bbasinsk.tuple.tupleValues
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.install
import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import kotlin.random.Random
import kotlin.random.nextInt
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.to
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
        .description("Update a person")
        .deprecated("some reason")
        .query(QuerySchema.int("id").optional())
        .header(
            HeaderSchema.string("name").example("asdf", "fds").example("two", "2").deprecated("some reason")
                .description("blah blah")
        )
        .header(HeaderSchema.int("other").example("blah", 123).deprecated("some reason").description("blah blah"))
        .input(Schema.person())
        .output(status(ResponseStatus.Ok, Schema.person()))
        .error(status(ResponseStatus.NotFound, Schema.int()))

val findPersonById =
    Http.get { Root / "person" / string("personId") }
        .output(status(ResponseStatus.Ok, Schema.person()))
        .error(status(ResponseStatus.NotFound, Schema.int()))

val multipleErrors =
    Http.get { Root / "error" / string("name") }
        .output(status(ResponseStatus.Ok.description("asdf"), Schema.person()))
        .error(
            oneOf<MultipleErrors>(
                ResponseStatus.Ok.description("Custom not found") to responseCase(Schema.notFoundSchema()),
                ResponseStatus.BadRequest to responseCase(Schema.badRequestSchema()),
            )
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

fun HttpEndpoints.exampleEndpoints(domainStuff: () -> Person) = httpEndpoints {
    handle(findPersonById) { request ->
        val (name) = tupleValues(request.params)
        success(domainStuff().copy(name = name))
    }

    handle(
        Http.get { Root / string("personId") / "blah" / int("thing") }
            .output(status(ResponseStatus.Ok, Schema.person()))
            .error(status(ResponseStatus.NotFound, Schema.int(), examples = mapOf("test1" to 123)))
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
            "not-found" -> kotlin.error(MultipleErrors.NotFound(Random.nextInt()))
            "bad-request" -> kotlin.error(MultipleErrors.BadRequest("Bad request"))
            else -> success(domainStuff())
        }
    }
}

class ExampleKtorTest {

    @Test
    @Ignore
    fun main() {
        embeddedServer(CIO, port = 33333) {
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

}
