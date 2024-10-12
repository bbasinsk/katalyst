package io.github.bbasinsk.schema

import java.time.Instant
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun Schema.Companion.instant(format: DateTimeFormatter = DateTimeFormatter.ISO_INSTANT): Schema<Instant> =
    string().transform(
        encode = { format.format(it) },
        decode = { format.parse(it, Instant::from) }
    )

fun Schema.Companion.localDate(format: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE): Schema<LocalDate> =
    string().transform(
        encode = { format.format(it) },
        decode = { format.parse(it, LocalDate::from) }
    )

// TODO: Add support for all the other Java Time types
