package io.github.bbasinsk.http.ktor3

import io.github.bbasinsk.http.HttpEndpointGroup
import io.github.bbasinsk.schema.Schema

data class MultipartInput(
    val id: PersonId?,
    val name: String,
    val age: Int
)

fun Schema.Companion.multipartInput(): Schema<MultipartInput> =
    record(
        field(personId().optional(), "id") { id },
        field(string(), "name") { name },
        field(int(), "age") { age },
        ::MultipartInput
    )

data class MultipartList(
    val images: List<ByteArray>
)

fun Schema.Companion.multipartList(): Schema<MultipartList> =
    record(
        field(list(byteArray()), "images") { images },
        ::MultipartList
    )

object MultipartEndpoints : HttpEndpointGroup("Multipart") {
    val multiPartInput = http {
        post { Root / "multipart" / "record" }
            .input { multipart { multipartInput() } }
            .output { status(Ok) { plain { string() } } }
    }

    val multiPartList = http {
        post { Root / "import" / "recipes" }
            .input { multipart { multipartList() } }
            .output { status(Ok) { plain { string() } } }
    }
}

fun HttpEndpoints.multipartEndpoints() {
    handle(MultipartEndpoints.multiPartInput) { request ->
        val (id, name, age) = request.input
        println("id: $id, name: $name, age: $age")
        success("Received multipart input with id: $id, name: $name, age: $age")
    }

    handle(MultipartEndpoints.multiPartList) { request ->
        val files = request.input.images
        println("Handling ${files.size} files: ${files.map { it.size }}")
        success("Received files: ${files.size}, first file size: ${files.first().size} bytes")
    }
}
