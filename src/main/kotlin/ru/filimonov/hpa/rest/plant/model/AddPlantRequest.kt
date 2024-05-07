package ru.filimonov.hpa.rest.plant.model

import ru.filimonov.hpa.domain.model.Plant
import java.util.*

data class AddPlantRequest(
    val name: String,
    val airTempMin: Float?,
    val airTempMax: Float?,
    val airHumidityMin: Float?,
    val airHumidityMax: Float?,
    val soilMoistureMin: Float?,
    val soilMoistureMax: Float?,
    val lightLuxMax: Int?,
    val lightLuxMin: Int?,
) {
    fun toDomain() = Plant(
        name = name,
        airTempMin = airTempMin,
        airTempMax = airTempMax,
        airHumidityMin = airHumidityMin,
        airHumidityMax = airHumidityMax,
        soilMoistureMin = soilMoistureMin,
        soilMoistureMax = soilMoistureMax,
        lightLevelMin = lightLuxMin,
        lightLevelMax = lightLuxMax,
        photoId = null,
        createdDate = Calendar.getInstance(),
        updatedDate = Calendar.getInstance(),
        uuid = UUID.randomUUID(),
    )
}
