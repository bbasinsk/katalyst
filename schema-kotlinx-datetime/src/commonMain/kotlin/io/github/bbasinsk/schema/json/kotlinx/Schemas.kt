package io.github.bbasinsk.schema.json.kotlinx

import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.transform
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.DateTimePeriod
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone

// https://github.com/Kotlin/kotlinx-datetime#types

fun Schema.Companion.instant(): Schema<Instant> =
    string().transform({ it.toString() }) { Instant.parse(it) }

fun Schema.Companion.localDateTime(): Schema<LocalDateTime> =
    string().transform({ it.toString() }) { LocalDateTime.parse(it) }

fun Schema.Companion.localDate(): Schema<LocalDate> =
    string().transform({ it.toString() }) { LocalDate.parse(it) }

fun Schema.Companion.localTime(): Schema<LocalTime> =
    string().transform({ it.toString() }) { LocalTime.parse(it) }

fun Schema.Companion.timeZone(): Schema<TimeZone> =
    string().transform({ it.toString() }) { TimeZone.of(it) }

fun Schema.Companion.dateTimePeriod(): Schema<DateTimePeriod> =
    string().transform({ it.toString() }) { DateTimePeriod.parse(it) }

fun Schema.Companion.datePeriod(): Schema<DatePeriod> =
    string().transform({ it.toString() }) { DatePeriod.parse(it) }

fun Schema.Companion.dayOfWeek(): Schema<DayOfWeek> =
    enumeration()

fun Schema.Companion.month(): Schema<Month> =
    enumeration()
