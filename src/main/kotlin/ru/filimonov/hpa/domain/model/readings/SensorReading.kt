package ru.filimonov.hpa.domain.model.readings

import java.util.*

interface SensorReading<ReadingType : Number> {
    val reading: ReadingType
    val timestamp: Calendar
}
