package ru.filimonov.hpa.data.model

import jakarta.persistence.*
import java.sql.Timestamp
import java.time.Instant
import java.util.*

@Entity
@Table(name = "light_lux_readings")
data class LightLevelLuxEntity(
    val reading: Int,
    @Column(name = "device")
    val deviceId: UUID,
    @Temporal(value = TemporalType.TIMESTAMP)
    val timestamp: Timestamp = Timestamp.from(Instant.now()),
    @Id
    val uuid: UUID = UUID.randomUUID()
)
