package ru.filimonov.hpa.controllers.rest.device.model

import ru.filimonov.hpa.domain.model.Device
import java.util.*

data class UpdateDeviceRequest(
    val plantId: UUID? = null,
    val name: String,
) {
    fun applyUpdates(device: Device): Device =
        device.copy(
            plantId = plantId,
            name = name,
        )
}
