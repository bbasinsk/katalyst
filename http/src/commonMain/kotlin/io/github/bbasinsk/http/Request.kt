package io.github.bbasinsk.http

data class Request<Params, Input, Auth, Context>(
    val params: Params,
    val input: Input,
    val auth: Auth,
    val context: Context
)
