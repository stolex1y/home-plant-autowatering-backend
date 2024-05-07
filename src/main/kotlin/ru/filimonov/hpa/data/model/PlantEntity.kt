package ru.filimonov.hpa.data.model

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import ru.filimonov.hpa.core.toCalendar
import ru.filimonov.hpa.domain.model.Plant
import java.sql.Timestamp
import java.time.Instant
import java.util.*

@Entity
@Table(name = "plants")
@EntityListeners(AuditingEntityListener::class)
data class PlantEntity(
    val name: String,
    val airTempMin: Float?,
    val airTempMax: Float?,
    val airHumidityMin: Float?,
    val airHumidityMax: Float?,
    val soilMoistureMin: Float?,
    val soilMoistureMax: Float?,
    val lightLevelMin: Int?,
    val lightLevelMax: Int?,
    val photo: UUID?,

    @Id
    val uuid: UUID,
) {
    @CreatedDate
    @Column(updatable = false)
    var createdDate: Timestamp = Timestamp.from(Instant.now())

    @LastModifiedDate
    var updatedDate: Timestamp = Timestamp.from(Instant.now())

    fun toDomain() = Plant(
        name = name,
        airTempMin = airTempMin,
        airTempMax = airTempMax,
        airHumidityMin = airHumidityMin,
        airHumidityMax = airHumidityMax,
        soilMoistureMin = soilMoistureMin,
        soilMoistureMax = soilMoistureMax,
        lightLevelMin = lightLevelMin,
        lightLevelMax = lightLevelMax,
        photoId = photo,
        createdDate = createdDate.toCalendar(),
        updatedDate = updatedDate.toCalendar(),
        uuid = uuid,
    )
}

fun Plant.toEntity() = PlantEntity(
    name = name,
    airTempMin = airTempMin,
    airTempMax = airTempMax,
    airHumidityMin = airHumidityMin,
    airHumidityMax = airHumidityMax,
    soilMoistureMin = soilMoistureMin,
    soilMoistureMax = soilMoistureMax,
    lightLevelMin = lightLevelMin,
    lightLevelMax = lightLevelMax,
    photo = photoId,
    uuid = uuid,
)
