package ru.filimonov.hpa.rest.readings.model

import ru.filimonov.hpa.domain.model.readings.SensorReading

data class SensorReadingResponse<ReadingType>(
    val reading: ReadingType,
    val timestamp: Long,
)

fun <ReadingType> SensorReading<ReadingType>.toResponse() = SensorReadingResponse(
    reading = reading,
    timestamp = timestamp.timeInMillis
)
