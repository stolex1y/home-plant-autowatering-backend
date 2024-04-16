package ru.filimonov.hpa.rest.device.model

import ru.filimonov.hpa.domain.model.Device
import java.util.*

data class DeviceResponse(
    val uuid: UUID,
    val plantId: UUID?,
    val createdDate: Long,
    val name: String,
    val mac: String,
    val photoId: UUID?,
)

fun Device.toResponse() = DeviceResponse(
    uuid = uuid,
    plantId = plantId,
    createdDate = createdDate.timeInMillis,
    name = name,
    mac = mac,
    photoId = photoId,
)
