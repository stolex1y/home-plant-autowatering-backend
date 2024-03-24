package ru.filimonov.hpa.rest.device.model

import ru.filimonov.hpa.domain.model.Device
import java.util.*

data class DeviceResponse(
    val uuid: UUID,
    val plantId: UUID?,
    val createdDate: Long,
)

fun Device.toResponse() = DeviceResponse(
    uuid = uuid,
    plantId = plantId,
    createdDate = createdDate.timeInMillis,
)
