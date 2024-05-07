package ru.filimonov.hpa.data.model.readings

import jakarta.persistence.Entity
import jakarta.persistence.IdClass
import jakarta.persistence.Table
import ru.filimonov.hpa.core.toCalendar
import ru.filimonov.hpa.core.toTimestamp
import ru.filimonov.hpa.domain.model.readings.AirTempReading
import java.sql.Timestamp
import java.util.*

@Entity
@Table(name = "air_temp_readings")
@IdClass(ReadingEntityId::class)
class AirTempReadingEntity(
    reading: Float,
    deviceId: UUID,
    timestamp: Timestamp,
) : ReadingEntity<Float>(
    reading = reading,
    deviceId = deviceId,
    timestamp = timestamp,
) {
    fun toDomain() = AirTempReading(
        reading = reading,
        deviceId = deviceId,
        timestamp = timestamp.toCalendar(),
    )
}

fun AirTempReading.toEntity() = AirTempReadingEntity(
    reading = reading,
    deviceId = deviceId,
    timestamp = timestamp.toTimestamp(),
)
