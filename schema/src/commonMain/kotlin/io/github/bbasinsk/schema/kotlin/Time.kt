package io.github.bbasinsk.schema.kotlin

import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.transform
import kotlin.time.Duration
import kotlin.time.DurationUnit

fun Schema.Companion.duration() =
    string().transform({ it.toIsoString() }) { Duration.parseIsoString(it) }

fun Schema.Companion.durationUnit() =
    enumeration<DurationUnit>()
