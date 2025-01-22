package io.github.bbasinsk.schema.java

import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.transform
import java.nio.ByteBuffer
import java.util.UUID

fun Schema.Companion.uuid() =
    string().transform(
        decode = { UUID.fromString(it) }
    ) { it.toString() }

fun Schema.Companion.byteBuffer() =
    byteArray().transform(
        decode = { ByteBuffer.wrap(it) }
    ) { it.array() }
