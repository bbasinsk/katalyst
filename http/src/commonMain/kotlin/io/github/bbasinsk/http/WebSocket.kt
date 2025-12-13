package io.github.bbasinsk.http

data class WebSocket<Params, In, Out>(
    val params: ParamsSchema<Params>,
    val input: BodySchema<In>,
    val output: BodySchema<Out>,
    val metadata: HttpMetadata
) {
    fun summary(summary: String): WebSocket<Params, In, Out> =
        copy(metadata = metadata.copy(summary = summary))

    fun deprecated(reason: String): WebSocket<Params, In, Out> =
        copy(metadata = metadata.copy(deprecatedReason = reason))

    fun tag(vararg tag: String): WebSocket<Params, In, Out> =
        copy(metadata = metadata.copy(tags = metadata.tags + tag.toList()))

    fun <In2> input(input: BodySchema.Companion.() -> BodySchema<In2>): WebSocket<Params, In2, Out> =
        WebSocket(
            params = params,
            input = BodySchema.input(),
            output = output,
            metadata = metadata
        )

    fun <Out2> output(output: BodySchema.Companion.() -> BodySchema<Out2>): WebSocket<Params, In, Out2> =
        WebSocket(
            params = params,
            input = input,
            output = BodySchema.output(),
            metadata = metadata
        )

    companion object {
        fun <Path> route(path: PathDsl.() -> PathSchema<Path>): WebSocket<Path, Nothing?, Nothing?> =
            WebSocket(
                params = PathDsl.path(),
                input = BodySchema.empty(),
                output = BodySchema.empty(),
                metadata = HttpMetadata()
            )
    }
}
