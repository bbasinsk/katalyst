package io.github.bbasinsk.http

data class Request<Params, Input, Context>(
    val params: Params,
    val input: Input,
    val context: Context
)
