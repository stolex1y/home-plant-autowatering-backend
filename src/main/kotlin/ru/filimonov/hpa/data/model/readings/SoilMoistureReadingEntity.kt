package ru.filimonov.hpa.data.model.readings

import jakarta.persistence.Entity
import jakarta.persistence.IdClass
import jakarta.persistence.Table
import ru.filimonov.hpa.core.toCalendar
import ru.filimonov.hpa.core.toTimestamp
import ru.filimonov.hpa.domain.model.readings.SoilMoistureReading
import java.sql.Timestamp
import java.util.*

@Entity
@Table(name = "soil_moisture_readings")
@IdClass(ReadingEntityId::class)
class SoilMoistureReadingEntity(
    reading: Float,
    deviceId: UUID,
    timestamp: Timestamp,
) : ReadingEntity<Float>(
    reading = reading,
    deviceId = deviceId,
    timestamp = timestamp,
) {
    fun toDomain(): SoilMoistureReading {
        return SoilMoistureReading(
            deviceId = deviceId,
            reading = reading,
            timestamp = timestamp.toCalendar(),
        )
    }
}

fun SoilMoistureReading.toEntity() = SoilMoistureReadingEntity(
    reading = reading,
    deviceId = deviceId,
    timestamp = timestamp.toTimestamp(),
)
