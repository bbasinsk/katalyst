package io.github.bbasinsk.http

sealed class ContentType(val mimeType: String) {
    data object Json : ContentType("application/json")
    data object Avro : ContentType("avro/binary")
    data object Any : ContentType("*/*")
}
