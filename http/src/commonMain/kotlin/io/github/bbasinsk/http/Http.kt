package io.github.bbasinsk.http

import io.github.bbasinsk.schema.Schema

data class HttpMetadata(
    val description: Metadata.Description? = null,
    val deprecated: Metadata.Deprecated? = null,
)

data class Http<Params, Input, Error, Output>(
    val method: HttpMethod,
    val params: ParamsSchema<Params>,
    val input: Schema<Input>,
    val error: ResponseSchema<Error>,
    val output: ResponseSchema<Output>,
    val metadata: HttpMetadata
) {

    fun description(description: String): Http<Params, Input, Error, Output> =
        copy(metadata = metadata.copy(description = Metadata.Description(description)))

    fun deprecated(reason: String): Http<Params, Input, Error, Output> =
        copy(metadata = metadata.copy(deprecated = Metadata.Deprecated(reason)))

    fun <Params2> query(query: QuerySchema.Companion.() -> QuerySchema<Params2>): Http<Pair<Params, Params2>, Input, Error, Output> =
        Http(
            method = method,
            params = params + QuerySchema.query(),
            input = input,
            error = error,
            output = output,
            metadata = metadata
        )

    fun <Params2> header(header: HeaderSchema.Companion.() -> HeaderSchema<Params2>): Http<Pair<Params, Params2>, Input, Error, Output> =
        Http(
            method = method,
            params = params + HeaderSchema.header(),
            input = input,
            error = error,
            output = output,
            metadata = metadata
        )

    fun <Input2> input(input: Schema.Companion.() -> Schema<Input2>): Http<Params, Input2, Error, Output> =
        Http(
            method = method,
            params = params,
            input = Schema.input(),
            error = error,
            output = output,
            metadata = metadata
        )

    fun <Output2> output(output: ResponseSchema.Companion.() -> ResponseSchema<Output2>): Http<Params, Input, Error, Output2> =
        Http(
            method = method,
            params = params,
            input = input,
            error = error,
            output = ResponseSchema.output(),
            metadata = metadata
        )

    fun <Error2> error(error: ResponseSchema.Companion.() -> ResponseSchema<Error2>): Http<Params, Input, Error2, Output> =
        Http(
            method = method,
            params = params,
            input = input,
            error = ResponseSchema.error(),
            output = output,
            metadata = metadata
        )

    companion object {

        fun <Path> get(path: PathSchema.Companion.() -> PathSchema<Path>): Http<Path, Nothing?, Nothing?, Nothing?> =
            Http(HttpMethod.GET, PathSchema.path(), Schema.empty(), ResponseSchema.empty(), ResponseSchema.empty(), HttpMetadata())

        fun <Path> post(path: PathSchema.Companion.() -> PathSchema<Path>): Http<Path, Nothing?, Nothing?, Nothing?> =
            Http(HttpMethod.POST, PathSchema.path(), Schema.empty(), ResponseSchema.empty(), ResponseSchema.empty(), HttpMetadata())

        fun <Path> put(path: PathSchema.Companion.() -> PathSchema<Path>): Http<Path, Nothing?, Nothing?, Nothing?> =
            Http(HttpMethod.PUT, PathSchema.path(), Schema.empty(), ResponseSchema.empty(), ResponseSchema.empty(), HttpMetadata())

        fun <Path> delete(path: PathSchema.Companion.() -> PathSchema<Path>): Http<Path, Nothing?, Nothing?, Nothing?> =
            Http(HttpMethod.DELETE, PathSchema.path(), Schema.empty(), ResponseSchema.empty(), ResponseSchema.empty(), HttpMetadata())
    }
}
