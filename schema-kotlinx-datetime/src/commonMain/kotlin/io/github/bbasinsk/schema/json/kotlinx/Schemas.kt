package io.github.bbasinsk.schema.json.kotlinx

import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.transform
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.DateTimePeriod
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlin.time.Instant

// https://github.com/Kotlin/kotlinx-datetime#types

fun Schema.Companion.instant(): Schema<Instant> =
    string().transform({ Instant.parse(it) }) { it.toString() }

fun Schema.Companion.localDateTime(): Schema<LocalDateTime> =
    string().transform({ LocalDateTime.parse(it) }) { it.toString() }

fun Schema.Companion.localDate(): Schema<LocalDate> =
    string().transform({ LocalDate.parse(it) }) { it.toString() }

fun Schema.Companion.localTime(): Schema<LocalTime> =
    string().transform({ LocalTime.parse(it) }) { it.toString() }

fun Schema.Companion.timeZone(): Schema<TimeZone> =
    string().transform({ TimeZone.of(it) }) { it.toString() }

fun Schema.Companion.dateTimePeriod(): Schema<DateTimePeriod> =
    string().transform({ DateTimePeriod.parse(it) }) { it.toString() }

fun Schema.Companion.datePeriod(): Schema<DatePeriod> =
    string().transform({ DatePeriod.parse(it) }) { it.toString() }

fun Schema.Companion.dayOfWeek(): Schema<DayOfWeek> =
    enumeration()

fun Schema.Companion.month(): Schema<Month> =
    enumeration()
