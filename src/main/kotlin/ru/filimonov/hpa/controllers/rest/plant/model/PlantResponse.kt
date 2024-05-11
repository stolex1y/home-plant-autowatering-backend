package ru.filimonov.hpa.controllers.rest.plant.model

import org.springframework.hateoas.server.mvc.linkTo
import ru.filimonov.hpa.controllers.rest.plant.PlantPhotoController
import ru.filimonov.hpa.domain.model.Plant
import ru.filimonov.hpa.domain.model.User
import java.net.URI
import java.util.*

data class PlantResponse(
    val name: String,
    val airTempMin: Float?,
    val airTempMax: Float?,
    val airHumidityMin: Float?,
    val airHumidityMax: Float?,
    val soilMoistureMin: Float?,
    val lightLuxMax: Int?,
    val lightLuxMin: Int?,
    val photoUri: URI?,
    val createdDate: Long,
    val uuid: UUID,
) {
    companion object {
        fun Plant.toResponse(user: User) = PlantResponse(
            name = name,
            airTempMin = airTempMin,
            airTempMax = airTempMax,
            airHumidityMin = airHumidityMin,
            airHumidityMax = airHumidityMax,
            soilMoistureMin = soilMoistureMin,
            lightLuxMin = lightLevelMin,
            lightLuxMax = lightLevelMax,
            photoUri = photoId?.let {
                linkTo<PlantPhotoController> {
                    getPlantPhoto(
                        user = user,
                        plantId = uuid
                    )
                }.toUri()
            },
            uuid = uuid,
            createdDate = createdDate.timeInMillis,
        )
    }
}
