@file:OptIn(ExperimentalUuidApi::class)

package io.github.bbasinsk.schema

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

fun Schema.Companion.uuid(): Schema<Uuid> =
    string().transform(
        encode = { it.toString() },
        decode = { Uuid.parse(it) }
    )
