package io.github.bbasinsk.http

data class HttpEndpoint<Params, Input, Error, Output, Context>(
    val api: Http<Params, Input, Error, Output>,
    val handle: suspend Response.Companion.(request: Request<Params, Input, Context>) -> Response<Error, Output>
)
