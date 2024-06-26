package ru.filimonov.hpa.controllers.rest.device.model

import org.springframework.hateoas.server.mvc.linkTo
import ru.filimonov.hpa.controllers.rest.device.DevicePhotoController
import ru.filimonov.hpa.domain.model.Device
import ru.filimonov.hpa.domain.model.User
import java.net.URI
import java.util.*

data class DeviceResponse(
    val uuid: UUID,
    val plantId: UUID?,
    val createdDate: Long,
    val name: String,
    val mac: String,
    val photo: URI?,
)

fun Device.toResponse(user: User) = DeviceResponse(
    uuid = uuid,
    plantId = plantId,
    createdDate = createdDate.timeInMillis,
    name = name,
    mac = mac,
    photo = photoId?.let {
        linkTo<DevicePhotoController> {
            getDevicePhoto(
                user = user,
                deviceId = uuid
            )
        }.toUri()
    },
)
