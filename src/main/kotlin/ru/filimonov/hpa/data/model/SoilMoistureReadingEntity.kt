package ru.filimonov.hpa.data.model

import jakarta.persistence.*
import ru.filimonov.hpa.core.toCalendar
import ru.filimonov.hpa.core.toTimestamp
import ru.filimonov.hpa.domain.model.SoilMoistureReading
import java.sql.Timestamp
import java.time.Instant
import java.util.*

@Entity
@Table(name = "soil_moisture_readings")
data class SoilMoistureReadingEntity(
    val reading: Float,
    @Column(name = "device")
    val deviceId: UUID,
    @Temporal(value = TemporalType.TIMESTAMP)
    val timestamp: Timestamp = Timestamp.from(Instant.now()),
    @Id
    val uuid: UUID = UUID.randomUUID()
) {
    companion object {
        fun SoilMoistureReading.toEntity() = SoilMoistureReadingEntity(
            reading = reading,
            deviceId = deviceId,
            timestamp = timestamp.toTimestamp(),
            uuid = uuid,
        )
    }

    fun toDomain(): SoilMoistureReading {
        return SoilMoistureReading(
            uuid = uuid,
            deviceId = deviceId,
            reading = reading,
            timestamp = timestamp.toCalendar(),
        )
    }
}
