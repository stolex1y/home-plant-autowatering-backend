package ru.filimonov.hpa.data.model

import jakarta.persistence.*
import java.sql.Timestamp
import java.time.Instant
import java.util.*

@Entity
@Table(name = "air_humidity_readings")
data class AirHumidityReadingEntity(
    val reading: Float,
    @Column(name = "device")
    val deviceId: UUID,
    @Temporal(value = TemporalType.TIMESTAMP)
    val timestamp: Timestamp = Timestamp.from(Instant.now()),
    @Id
    val uuid: UUID = UUID.randomUUID()
)
