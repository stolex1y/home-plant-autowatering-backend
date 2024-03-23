package ru.filimonov.hpa.core

import org.springframework.data.domain.Range
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

fun Timestamp.toCalendar(): Calendar {
    return toInstant().toEpochMilli().toCalendar()
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
    //TODO добавить открытые границы
    val leftBound = lowerBound.value.orElse(CALENDAR_MIN)
    val rightBound = upperBound.value.orElse(CALENDAR_MAX)
    return leftBound to rightBound
}

fun Calendar.toTimestamp(): Timestamp {
    return Timestamp.from(this.toInstant())
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
