package io.github.bbasinsk.schema.java

import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.transform
import java.nio.ByteBuffer
import java.util.UUID

fun Schema.Companion.uuid() =
    string().transform(
        encode = { it.toString() },
        decode = { UUID.fromString(it) }
    )

fun Schema.Companion.byteBuffer() =
    byteArray().transform(
        encode = { it.array() },
        decode = { ByteBuffer.wrap(it) }
    )
