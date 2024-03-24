package ru.filimonov.hpa.rest.device.model

import ru.filimonov.hpa.domain.model.Device
import java.util.*

data class UpdateDeviceRequest(
    val plantId: UUID? = null,
) {
    fun applyUpdates(device: Device): Device =
        device.copy(
            plantId = plantId
        )
}
