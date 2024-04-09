package ru.filimonov.hpa.rest.device.model

import ru.filimonov.hpa.domain.model.Device
import java.util.*

data class AddDeviceRequest(
    val mac: String,
    val plantId: UUID? = null,
) {
    fun toDomain(userId: String) = Device(
        userId = userId,
        mac = mac,
        plantId = plantId,
    )
}
