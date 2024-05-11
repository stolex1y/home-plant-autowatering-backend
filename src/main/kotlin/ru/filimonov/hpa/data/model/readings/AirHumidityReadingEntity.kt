package ru.filimonov.hpa.data.model.readings

import jakarta.persistence.Entity
import jakarta.persistence.IdClass
import jakarta.persistence.Table
import ru.filimonov.hpa.common.toCalendar
import ru.filimonov.hpa.common.toTimestamp
import ru.filimonov.hpa.domain.model.readings.AirHumidityReading
import java.sql.Timestamp
import java.util.*

@Entity
@Table(name = "air_humidity_readings")
@IdClass(ReadingEntityId::class)
class AirHumidityReadingEntity(
    reading: Float,
    deviceId: UUID,
    timestamp: Timestamp,
) : ReadingEntity<Float>(
    reading = reading,
    deviceId = deviceId,
    timestamp = timestamp,
) {
    fun toDomain() = AirHumidityReading(
        reading = reading,
        deviceId = deviceId,
        timestamp = timestamp.toCalendar(),
    )
}

fun AirHumidityReading.toEntity() = AirHumidityReadingEntity(
    reading = reading,
    deviceId = deviceId,
    timestamp = timestamp.toTimestamp(),
)
