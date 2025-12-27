package io.github.bbasinsk.http

import kotlin.jvm.JvmName

data class HttpMetadata(
    val summary: String? = null,
    val deprecatedReason: String? = null,
    val tags: List<String> = emptyList()
)

data class Http<Params, Input, Error, Output, Auth>(
    val method: HttpMethod,
    val params: ParamsSchema<Params>,
    val input: BodySchema<Input>,
    val error: ResponseSchema<Error>,
    val output: ResponseSchema<Output>,
    val auth: AuthSchema<Auth>,
    val metadata: HttpMetadata
) {

    fun summary(summary: String): Http<Params, Input, Error, Output, Auth> =
        copy(metadata = metadata.copy(summary = summary))

    fun deprecated(reason: String): Http<Params, Input, Error, Output, Auth> =
        copy(metadata = metadata.copy(deprecatedReason = reason))

    fun tag(vararg tag: String): Http<Params, Input, Error, Output, Auth> =
        copy(metadata = metadata.copy(tags = metadata.tags + tag.toList()))

    fun <Input2> input(input: BodySchema.Companion.() -> BodySchema<Input2>): Http<Params, Input2, Error, Output, Auth> =
        Http(
            method = method,
            params = params,
            input = BodySchema.input(),
            error = error,
            output = output,
            auth = auth,
            metadata = metadata
        )

    fun <Output2> output(output: ResponseSchema.Companion.() -> ResponseSchema<Output2>): Http<Params, Input, Error, Output2, Auth> =
        Http(
            method = method,
            params = params,
            input = input,
            error = error,
            output = ResponseSchema.output(),
            auth = auth,
            metadata = metadata
        )

    fun <Error2> error(error: ResponseSchema.Companion.() -> ResponseSchema<Error2>): Http<Params, Input, Error2, Output, Auth> =
        Http(
            method = method,
            params = params,
            input = input,
            error = ResponseSchema.error(),
            output = output,
            auth = auth,
            metadata = metadata
        )

    companion object {

        fun <Path> get(path: PathDsl.() -> PathSchema<Path>): Http<Path, Nothing?, Nothing, Nothing, Unit> =
            Http(
                method = HttpMethod.GET,
                params = PathDsl.path(),
                input = BodySchema.empty(),
                error = ResponseSchema.nothing(),
                output = ResponseSchema.nothing(),
                auth = AuthSchema.None,
                metadata = HttpMetadata()
            )

        fun <Path> post(path: PathDsl.() -> PathSchema<Path>): Http<Path, Nothing?, Nothing, Nothing, Unit> =
            Http(
                method = HttpMethod.POST,
                params = PathDsl.path(),
                input = BodySchema.empty(),
                error = ResponseSchema.nothing(),
                output = ResponseSchema.nothing(),
                auth = AuthSchema.None,
                metadata = HttpMetadata()
            )

        fun <Path> put(path: PathDsl.() -> PathSchema<Path>): Http<Path, Nothing?, Nothing, Nothing, Unit> =
            Http(
                method = HttpMethod.PUT,
                params = PathDsl.path(),
                input = BodySchema.empty(),
                error = ResponseSchema.nothing(),
                output = ResponseSchema.nothing(),
                auth = AuthSchema.None,
                metadata = HttpMetadata()
            )

        fun <Path> delete(path: PathDsl.() -> PathSchema<Path>): Http<Path, Nothing?, Nothing, Nothing, Unit> =
            Http(
                method = HttpMethod.DELETE,
                params = PathDsl.path(),
                input = BodySchema.empty(),
                error = ResponseSchema.nothing(),
                output = ResponseSchema.nothing(),
                auth = AuthSchema.None,
                metadata = HttpMetadata()
            )
    }
}


// Extension functions for query/header - handle Unit specially to avoid Pair<Unit, ...>

@JvmName("queryEmpty")
fun <Input, Error, Output, Auth, Params2> Http<Unit, Input, Error, Output, Auth>.query(
    query: ParamSchema.Companion.() -> ParamSchema<Params2>
): Http<Params2, Input, Error, Output, Auth> = Http(
    method = method,
    params = params.withQuery(ParamSchema.query()),
    input = input,
    error = error,
    output = output,
    auth = auth,
    metadata = metadata
)

fun <Params, Input, Error, Output, Auth, Params2> Http<Params, Input, Error, Output, Auth>.query(
    query: ParamSchema.Companion.() -> ParamSchema<Params2>
): Http<Pair<Params, Params2>, Input, Error, Output, Auth> = Http(
    method = method,
    params = params.withQuery(ParamSchema.query()),
    input = input,
    error = error,
    output = output,
    auth = auth,
    metadata = metadata
)

@JvmName("headerEmpty")
fun <Input, Error, Output, Auth, Params2> Http<Unit, Input, Error, Output, Auth>.header(
    header: ParamSchema.Companion.() -> ParamSchema<Params2>
): Http<Params2, Input, Error, Output, Auth> = Http(
    method = method,
    params = params.withHeader(ParamSchema.header()),
    input = input,
    error = error,
    output = output,
    auth = auth,
    metadata = metadata
)

fun <Params, Input, Error, Output, Auth, Params2> Http<Params, Input, Error, Output, Auth>.header(
    header: ParamSchema.Companion.() -> ParamSchema<Params2>
): Http<Pair<Params, Params2>, Input, Error, Output, Auth> = Http(
    method = method,
    params = params.withHeader(ParamSchema.header()),
    input = input,
    error = error,
    output = output,
    auth = auth,
    metadata = metadata
)

// Extension function for auth - only available when Auth = Unit
fun <Params, Input, Error, Output, Auth2> Http<Params, Input, Error, Output, Unit>.auth(
    auth: AuthSchema.Companion.() -> AuthSchema<Auth2>
): Http<Params, Input, Error, Output, Auth2> = Http(
    method = method,
    params = params,
    input = input,
    error = error,
    output = output,
    auth = AuthSchema.auth(),
    metadata = metadata
)
