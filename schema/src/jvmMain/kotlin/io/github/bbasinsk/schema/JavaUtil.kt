package io.github.bbasinsk.schema

import java.nio.ByteBuffer
import java.util.UUID

fun Schema.Companion.uuid(): Schema<UUID> =
    string().transform(
        encode = { it.toString() },
        decode = { UUID.fromString(it) }
    )

fun Schema.Companion.byteBuffer(): Schema<ByteBuffer> =
    byteArray().transform(
        encode = { it.array() },
        decode = { ByteBuffer.wrap(it) }
    )
