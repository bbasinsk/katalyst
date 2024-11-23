@file:OptIn(ExperimentalUuidApi::class)

package io.github.bbasinsk.schema.kotlin

import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.transform
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

fun Schema.Companion.uuid(): Schema<Uuid> =
    string().transform(
        encode = { it.toString() },
        decode = { Uuid.parse(it) }
    )
