package ru.filimonov.hpa.data.model.readings

import jakarta.persistence.Entity
import jakarta.persistence.Table
import ru.filimonov.hpa.common.toCalendar
import ru.filimonov.hpa.common.toTimestamp
import ru.filimonov.hpa.domain.model.readings.BatteryChargeReading
import java.sql.Timestamp
import java.util.*

@Entity
@Table(name = "battery_charge_readings")
class BatteryChargeReadingEntity(
    reading: Float,
    deviceId: UUID,
    timestamp: Timestamp,
) : ReadingEntity<Float>(
    reading = reading,
    deviceId = deviceId,
    timestamp = timestamp,
) {
    fun toDomain() = BatteryChargeReading(
        reading = reading,
        deviceId = deviceId,
        timestamp = timestamp.toCalendar(),
    )
}

fun BatteryChargeReading.toEntity() = BatteryChargeReadingEntity(
    reading = reading,
    deviceId = deviceId,
    timestamp = timestamp.toTimestamp(),
)
