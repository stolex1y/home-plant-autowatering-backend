package ru.filimonov.hpa.data.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.annotation.CreatedDate
import ru.filimonov.hpa.core.toCalendar
import ru.filimonov.hpa.domain.model.Plant
import java.sql.Timestamp
import java.time.Instant
import java.util.*

@Entity
@Table(name = "plants")
data class PlantEntity(
    val name: String,
    val airTempMin: Float?,
    val airTempMax: Float?,
    val airHumidityMin: Float?,
    val airHumidityMax: Float?,
    val soilMoistureMin: Float?,
    val soilMoistureMax: Float?,
    val lightLuxMin: Int?,
    val lightLuxMax: Int?,
    val photo: UUID?,
    @Id
    val uuid: UUID,
) {
    @CreatedDate
    @Column(updatable = false)
    var createdDate: Timestamp = Timestamp.from(Instant.now())

    companion object {
        fun Plant.toEntity() = PlantEntity(
            name = name,
            airTempMin = airTempMin,
            airTempMax = airTempMax,
            airHumidityMin = airHumidityMin,
            airHumidityMax = airHumidityMax,
            soilMoistureMin = soilMoistureMin,
            soilMoistureMax = soilMoistureMax,
            lightLuxMin = lightLuxMin,
            lightLuxMax = lightLuxMax,
            photo = photoId,
            uuid = uuid,
        )
    }

    fun toDomain() = Plant(
        name = name,
        airTempMin = airTempMin,
        airTempMax = airTempMax,
        airHumidityMin = airHumidityMin,
        airHumidityMax = airHumidityMax,
        soilMoistureMin = soilMoistureMin,
        soilMoistureMax = soilMoistureMax,
        lightLuxMin = lightLuxMin,
        lightLuxMax = lightLuxMax,
        photoId = photo,
        createdDate = createdDate.toCalendar(),
        uuid = uuid,
    )
}
