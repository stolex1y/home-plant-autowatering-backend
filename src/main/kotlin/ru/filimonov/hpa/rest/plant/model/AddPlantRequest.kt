package ru.filimonov.hpa.rest.plant.model

import ru.filimonov.hpa.domain.model.Plant

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
        lightLuxMin = lightLuxMin,
        lightLuxMax = lightLuxMax,
        photoId = null,
    )
}
