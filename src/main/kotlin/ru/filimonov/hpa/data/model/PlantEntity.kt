package ru.filimonov.hpa.data.model

import jakarta.persistence.*
import java.sql.Timestamp
import java.time.Instant
import java.util.*

@Entity
@Table(name = "plants")
data class PlantEntity(
    val name: String,
    val airTempMin: Float? = null,
    val airTempMax: Float? = null,
    val airHumidityMin: Float? = null,
    val airHumidityMax: Float? = null,
    val soilMoistureMin: Float? = null,
    val soilMoistureMax: Float? = null,
    val lightLuxMin: Int? = null,
    val lightLuxMax: Int? = null,
    @Temporal(value = TemporalType.TIMESTAMP)
    val createdDate: Timestamp = Timestamp.from(Instant.now()),
    @Id
    val uuid: UUID = UUID.randomUUID()
)
