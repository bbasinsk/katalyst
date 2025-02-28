package io.github.bbasinsk.schema.java

import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.transform
import java.time.*
import java.time.format.DateTimeFormatter

fun Schema.Companion.duration() =
    string().transform({ Duration.parse(it) }) { it.toString() }

fun Schema.Companion.instant(format: DateTimeFormatter = DateTimeFormatter.ISO_INSTANT) =
    string().transform({ format.parse(it, Instant::from) }) { format.format(it) }

fun Schema.Companion.localDate(format: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE): Schema<LocalDate> =
    string().transform({ format.parse(it, LocalDate::from) }) { format.format(it) }

fun Schema.Companion.localDateTime(format: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME): Schema<LocalDateTime> =
    string().transform({ format.parse(it, LocalDateTime::from) }) { format.format(it) }

fun Schema.Companion.zonedDateTime(format: DateTimeFormatter = DateTimeFormatter.ISO_ZONED_DATE_TIME): Schema<ZonedDateTime> =
    string().transform({ format.parse(it, ZonedDateTime::from) }) { format.format(it) }

fun Schema.Companion.offsetDateTime(format: DateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME): Schema<OffsetDateTime> =
    string().transform({ format.parse(it, OffsetDateTime::from) }) { format.format(it) }

fun Schema.Companion.offsetTime(format: DateTimeFormatter = DateTimeFormatter.ISO_OFFSET_TIME) =
    string().transform({ format.parse(it, OffsetTime::from) }) { format.format(it) }

fun Schema.Companion.localTime(format: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_TIME) =
    string().transform({ format.parse(it, LocalTime::from) }) { format.format(it) }

fun Schema.Companion.period(): Schema<Period> =
    string().transform({ Period.parse(it) }) { it.toString() }

fun Schema.Companion.zoneId(): Schema<ZoneId> =
    string().transform({ ZoneId.of(it) }) { it.toString() }

fun Schema.Companion.zoneOffset(): Schema<ZoneOffset> =
    string().transform({ ZoneOffset.of(it) }) { it.toString() }
