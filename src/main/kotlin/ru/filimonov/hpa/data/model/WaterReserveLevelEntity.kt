package ru.filimonov.hpa.data.model

import jakarta.persistence.*
import java.sql.Timestamp
import java.time.Instant
import java.util.*

@Entity
@Table(name = "water_reserve_readings")
data class WaterReserveLevelEntity(
    val enoughWater: Boolean,
    @Column(name = "device")
    val deviceId: UUID,
    @Temporal(value = TemporalType.TIMESTAMP)
    val timestamp: Timestamp = Timestamp.from(Instant.now()),
    @Id
    val uuid: UUID = UUID.randomUUID()
)
