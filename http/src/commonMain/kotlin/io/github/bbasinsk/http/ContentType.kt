package io.github.bbasinsk.http

sealed class ContentType(open val contentType: String, open val contentSubtype: String) {
    val mimeType: String
        get() = "$contentType/$contentSubtype"

    data object Json : ContentType("application", "json")
    data object Avro : ContentType("avro", "binary")
    data object Plain : ContentType("text", "plain")
    data object Html : ContentType("text", "html")
    data object MultipartFormData : ContentType("multipart", "form-data")

    sealed class Image(override val contentType: String, override val contentSubtype: String) : ContentType(contentType, contentSubtype) {
        data object Jpeg : Image("image", "jpeg")
        data object Png : Image("image", "png")
        data object Gif : Image("image", "gif")
        data object Webp : Image("image", "webp")
    }
}
