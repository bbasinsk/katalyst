package io.github.bbasinsk.http

data class HttpMetadata(
    val description: String? = null,
    val deprecatedReason: String? = null,
)

data class Http<Params, Input, Error, Output>(
    val method: HttpMethod,
    val params: ParamsSchema<Params>,
    val input: RequestSchema<Input>,
    val error: ResponsesSchema<Error>,
    val output: ResponsesSchema<Output>,
    val metadata: HttpMetadata
) {

    fun description(description: String): Http<Params, Input, Error, Output> =
        copy(metadata = metadata.copy(description = description))

    fun deprecated(reason: String): Http<Params, Input, Error, Output> =
        copy(metadata = metadata.copy(deprecatedReason = reason))

    fun <Params2> query(query: ParamSchema.Companion.() -> ParamSchema<Params2>): Http<Pair<Params, Params2>, Input, Error, Output> =
        Http(
            method = method,
            params = params.withQuery(ParamSchema.query()),
            input = input,
            error = error,
            output = output,
            metadata = metadata
        )

    fun <Params2> header(header: ParamSchema.Companion.() -> ParamSchema<Params2>): Http<Pair<Params, Params2>, Input, Error, Output> =
        Http(
            method = method,
            params = params.withHeader(ParamSchema.header()),
            input = input,
            error = error,
            output = output,
            metadata = metadata
        )

    fun <Input2> input(input: RequestSchema.Companion.() -> RequestSchema<Input2>): Http<Params, Input2, Error, Output> =
        Http(
            method = method,
            params = params,
            input = RequestSchema.input(),
            error = error,
            output = output,
            metadata = metadata
        )

    fun <Output2> output(output: ResponsesSchema.Companion.() -> ResponsesSchema<Output2>): Http<Params, Input, Error, Output2> =
        Http(
            method = method,
            params = params,
            input = input,
            error = error,
            output = ResponsesSchema.output(),
            metadata = metadata
        )

    fun <Error2> error(error: ResponsesSchema.Companion.() -> ResponsesSchema<Error2>): Http<Params, Input, Error2, Output> =
        Http(
            method = method,
            params = params,
            input = input,
            error = ResponsesSchema.error(),
            output = output,
            metadata = metadata
        )

    companion object {

        fun <Path> get(path: PathSchema.Companion.() -> PathSchema<Path>): Http<Path, Nothing?, Nothing, Nothing?> =
            Http(
                method = HttpMethod.GET,
                params = PathSchema.path(),
                input = RequestSchema.empty(),
                error = ResponsesSchema.nothing(),
                output = ResponsesSchema.empty(),
                metadata = HttpMetadata()
            )

        fun <Path> post(path: PathSchema.Companion.() -> PathSchema<Path>): Http<Path, Nothing?, Nothing, Nothing?> =
            Http(
                method = HttpMethod.POST,
                params = PathSchema.path(),
                input = RequestSchema.empty(),
                error = ResponsesSchema.nothing(),
                output = ResponsesSchema.empty(),
                metadata = HttpMetadata()
            )

        fun <Path> put(path: PathSchema.Companion.() -> PathSchema<Path>): Http<Path, Nothing?, Nothing, Nothing?> =
            Http(
                method = HttpMethod.PUT,
                params = PathSchema.path(),
                input = RequestSchema.empty(),
                error = ResponsesSchema.nothing(),
                output = ResponsesSchema.empty(),
                metadata = HttpMetadata()
            )

        fun <Path> delete(path: PathSchema.Companion.() -> PathSchema<Path>): Http<Path, Nothing?, Nothing, Nothing?> =
            Http(
                method = HttpMethod.DELETE,
                params = PathSchema.path(),
                input = RequestSchema.empty(),
                error = ResponsesSchema.nothing(),
                output = ResponsesSchema.empty(),
                metadata = HttpMetadata()
            )
    }
}
