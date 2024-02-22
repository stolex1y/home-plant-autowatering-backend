package ru.filimonov.hpa.data

import ru.filimonov.hpa.data.model.SoilMoistureReadingEntity
import ru.filimonov.hpa.domain.model.SensorReading

fun SoilMoistureReadingEntity.toSensorReading(): SensorReading<Float> {
    return SensorReading(reading = reading, timestamp = timestamp)
}
