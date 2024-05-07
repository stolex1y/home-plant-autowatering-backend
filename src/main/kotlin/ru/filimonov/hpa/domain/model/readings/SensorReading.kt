package ru.filimonov.hpa.domain.model.readings

import jakarta.validation.constraints.PastOrPresent
import ru.filimonov.hpa.core.toCalendar
import ru.filimonov.hpa.core.toDateTimeString
import java.sql.Timestamp
import java.util.*

open class SensorReading<ReadingType>(
    open val reading: ReadingType,

    @PastOrPresent
    open val timestamp: Calendar
) {
    constructor(
        reading: ReadingType,
        timestamp: Timestamp
    ) : this(reading = reading, timestamp = timestamp.toCalendar())

    override fun toString(): String {
        return "SensorReading(reading=$reading, timestamp=${timestamp.toDateTimeString()})"
    }
}
