package ru.filimonov.hpa.data.model.readings

import jakarta.persistence.Entity
import jakarta.persistence.IdClass
import jakarta.persistence.Table
import ru.filimonov.hpa.common.toCalendar
import ru.filimonov.hpa.common.toTimestamp
import ru.filimonov.hpa.domain.model.readings.WaterReserveReading
import java.sql.Timestamp
import java.util.*

@Entity
@Table(name = "water_reserve_readings")
@IdClass(ReadingEntityId::class)
class WaterReserveReadingEntity(
    reading: Float,
    deviceId: UUID,
    timestamp: Timestamp,
) : ReadingEntity<Float>(
    reading = reading,
    deviceId = deviceId,
    timestamp = timestamp,
) {
    fun toDomain() = WaterReserveReading(
        reading = reading,
        deviceId = deviceId,
        timestamp = timestamp.toCalendar(),
    )
}

fun WaterReserveReading.toEntity() = WaterReserveReadingEntity(
    reading = reading,
    deviceId = deviceId,
    timestamp = timestamp.toTimestamp(),
)
