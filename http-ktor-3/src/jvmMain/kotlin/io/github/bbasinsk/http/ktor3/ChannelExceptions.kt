package io.github.bbasinsk.http.ktor3

import io.ktor.util.cio.ChannelWriteException
import io.ktor.utils.io.ClosedWriteChannelException
import java.nio.channels.ClosedChannelException

internal actual fun Throwable.isChannelClosedException(): Boolean =
    generateSequence(this) { it.cause }.any {
        it is ChannelWriteException ||
            it is ClosedWriteChannelException ||
            it is ClosedChannelException
    }
