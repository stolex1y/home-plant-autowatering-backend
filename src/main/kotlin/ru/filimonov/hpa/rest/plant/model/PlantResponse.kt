package ru.filimonov.hpa.rest.plant.model

import ru.filimonov.hpa.domain.model.Plant
import java.util.*

data class PlantResponse(
    val name: String,
    val airTempMin: Float?,
    val airTempMax: Float?,
    val airHumidityMin: Float?,
    val airHumidityMax: Float?,
    val soilMoistureMin: Float?,
    val soilMoistureMax: Float?,
    val lightLuxMax: Int?,
    val lightLuxMin: Int?,
    val photo: UUID?,
    val createdDate: Calendar,
    val uuid: UUID,
) {
    companion object {
        fun Plant.toResponse() = PlantResponse(
            name = name,
            airTempMin = airTempMin,
            airTempMax = airTempMax,
            airHumidityMin = airHumidityMin,
            airHumidityMax = airHumidityMax,
            soilMoistureMin = soilMoistureMin,
            soilMoistureMax = soilMoistureMax,
            lightLuxMin = lightLuxMin,
            lightLuxMax = lightLuxMax,
            photo = photo,
            uuid = uuid,
            createdDate = createdDate,
        )
    }
}
