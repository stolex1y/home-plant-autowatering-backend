package ru.filimonov.hpa.rest.common.model

import ru.filimonov.hpa.domain.model.SensorReading

data class SensorReadingResponse<ReadingType>(
    val reading: ReadingType,
    val timestamp: Long
)

fun <ReadingType> SensorReading<ReadingType>.toResponse() = SensorReadingResponse<ReadingType>(
    reading = reading,
    timestamp = timestamp.timeInMillis
)
