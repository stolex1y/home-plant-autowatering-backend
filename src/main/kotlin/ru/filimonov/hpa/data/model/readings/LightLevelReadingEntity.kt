package ru.filimonov.hpa.data.model.readings

import jakarta.persistence.Entity
import jakarta.persistence.IdClass
import jakarta.persistence.Table
import ru.filimonov.hpa.core.toCalendar
import ru.filimonov.hpa.core.toTimestamp
import ru.filimonov.hpa.domain.model.readings.LightLevelReading
import java.sql.Timestamp
import java.util.*

@Entity
@Table(name = "light_level_readings")
@IdClass(ReadingEntityId::class)
class LightLevelReadingEntity(
    reading: Int,
    deviceId: UUID,
    timestamp: Timestamp,
) : ReadingEntity<Int>(
    reading = reading,
    deviceId = deviceId,
    timestamp = timestamp,
) {
    fun toDomain() = LightLevelReading(
        reading = reading,
        deviceId = deviceId,
        timestamp = timestamp.toCalendar(),
    )
}

fun LightLevelReading.toEntity() = LightLevelReadingEntity(
    reading = reading,
    deviceId = deviceId,
    timestamp = timestamp.toTimestamp(),
)
