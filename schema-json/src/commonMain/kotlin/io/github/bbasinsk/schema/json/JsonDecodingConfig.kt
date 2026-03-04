package io.github.bbasinsk.schema.json

data class JsonDecodingConfig(
    val allowTrailingCommas: Boolean = false,
    val allowComments: Boolean = false,
)
