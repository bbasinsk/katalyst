package io.github.bbasinsk.http

data class HttpEndpoint<Params, Input, Error, Output, Auth, Context>(
    val api: Http<Params, Input, Error, Output, Auth>,
    val authHandler: AuthHandler<Auth>?,
    val handle: suspend Response.Companion.(request: Request<Params, Input, Auth, Context>) -> Response<Error, Output>
)
