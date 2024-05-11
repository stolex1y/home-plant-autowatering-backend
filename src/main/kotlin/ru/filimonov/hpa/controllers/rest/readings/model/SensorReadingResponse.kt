package ru.filimonov.hpa.controllers.rest.readings.model

import ru.filimonov.hpa.domain.model.readings.SensorReading

data class SensorReadingResponse<ReadingType>(
    val reading: ReadingType,
    val timestamp: Long,
)

fun <ReadingType : Number> SensorReading<ReadingType>.toResponse() = SensorReadingResponse(
    reading = reading,
    timestamp = timestamp.timeInMillis
)
