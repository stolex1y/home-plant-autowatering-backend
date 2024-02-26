package ru.filimonov.hpa.data.model

import jakarta.persistence.*
import ru.filimonov.hpa.domain.model.SensorReading
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
    fun toDomain(): SensorReading<Float> {
        return SensorReading(reading = reading, timestamp = timestamp)
    }
}
