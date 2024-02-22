package ru.filimonov.hpa.domain.model

import ru.filimonov.hpa.core.toCalendar
import ru.filimonov.hpa.core.toDateTimeString
import java.sql.Timestamp
import java.util.*

data class SensorReading<ReadingType>(
    val reading: ReadingType,
    val timestamp: Calendar
) {
    constructor(
        reading: ReadingType,
        timestamp: Timestamp
    ) : this(reading = reading, timestamp = timestamp.toCalendar())

    override fun toString(): String {
        return "SensorReading(reading=$reading, timestamp=${timestamp.toDateTimeString()})"
    }

}
