package ru.filimonov.hpa.common

import org.springframework.data.domain.Range
import java.sql.Time
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.*
import java.util.*

fun Timestamp.toCalendar(): Calendar {
    return toInstant().toEpochMilli().toCalendar()
}

fun Timestamp.toZonedDateTime(zoneId: ZoneId = ZoneId.systemDefault()): ZonedDateTime {
    return ZonedDateTime.of(toLocalDateTime(), zoneId)
}

fun Long.toCalendar(): Calendar {
    return Calendar.getInstance().apply {
        timeInMillis = this@toCalendar
    }
}

val CALENDAR_MIN: Calendar
    get() = Long.MIN_VALUE.toCalendar()

val CALENDAR_MAX: Calendar
    get() = Long.MAX_VALUE.toCalendar()

fun Range<Calendar>.bounds(): Pair<Calendar, Calendar> {
    val leftBound = lowerBound.value.orElse(CALENDAR_MIN)
    val rightBound = upperBound.value.orElse(CALENDAR_MAX)
    return leftBound to rightBound
}

fun Calendar.toTimestamp(): Timestamp {
    return Timestamp.from(this.toInstant())
}

fun ZonedDateTime.toTimestamp(): Timestamp {
    return Timestamp.valueOf(this.toLocalDateTime())
}

fun Calendar.floorToHour() {
    set(
        get(Calendar.YEAR),
        get(Calendar.MONTH),
        get(Calendar.DAY_OF_MONTH),
        get(Calendar.HOUR_OF_DAY),
        0,
        0
    )
}

fun Calendar.floorToDay() {
    set(
        get(Calendar.YEAR),
        get(Calendar.MONTH),
        get(Calendar.DAY_OF_MONTH),
        0,
        0,
        0
    )
}

private val simpleDateTimeFormatter: SimpleDateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())

fun Calendar.toDateTimeString(): String {
    return simpleDateTimeFormatter.format(this.time)
}

fun Long.toZonedDateTime(zoneId: ZoneId = ZoneId.systemDefault()): ZonedDateTime =
    ZonedDateTime.ofInstant(Instant.ofEpochMilli(this), zoneId)

fun Long.toZonedDateTime(otherDate: ZonedDateTime): ZonedDateTime =
    ZonedDateTime.ofInstant(Instant.ofEpochMilli(this), otherDate.zone)

fun Long.toLocalTime(zoneId: ZoneId = ZoneId.systemDefault()): LocalTime {
    return LocalTime.ofInstant(Instant.ofEpochMilli(this), zoneId)
}

fun ZonedDateTime.toEpochMillis() = this.toInstant().toEpochMilli()

fun Time.toLocalTime(zoneId: ZoneId = ZoneId.systemDefault()): LocalTime {
    return LocalTime.ofInstant(this.toInstant(), zoneId)
}

fun LocalTime.toTime(): Time {
    return Time.valueOf(this)
}

fun LocalTime.nextTimePointFromNow(zoneId: ZoneId = ZoneId.systemDefault()): ZonedDateTime {
    val todayThisTime = ZonedDateTime.of(LocalDate.now(zoneId), this, zoneId)
    return if (LocalTime.now() > this) {
        todayThisTime.plusDays(1)
    } else {
        todayThisTime
    }
}

fun ZonedDateTime.toCalendar() = toEpochMillis().toCalendar()
