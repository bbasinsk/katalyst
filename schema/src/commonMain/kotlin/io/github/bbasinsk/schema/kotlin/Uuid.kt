@file:OptIn(ExperimentalUuidApi::class)

package io.github.bbasinsk.schema.kotlin

import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.transform
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

fun Schema.Companion.uuid() =
    string().transform(
        decode = { Uuid.parse(it) }
    ) { it.toString() }
