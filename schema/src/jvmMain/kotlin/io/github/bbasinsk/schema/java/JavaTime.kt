package io.github.bbasinsk.schema.java

import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.transform
import java.time.Instant
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun Schema.Companion.instant(format: DateTimeFormatter = DateTimeFormatter.ISO_INSTANT) =
    string().transform(
        decode = { format.parse(it, Instant::from) }
    ) { format.format(it) }

fun Schema.Companion.localDate(format: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE) =
    string().transform(
        decode = { format.parse(it, LocalDate::from) }
    ) { format.format(it) }

// TODO: Add support for all the other Java Time types
