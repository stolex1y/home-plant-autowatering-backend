package ru.filimonov.hpa.rest.device.model

import ru.filimonov.hpa.domain.model.Device
import java.util.*

data class AddDeviceRequest(
    val mac: String,
    val plantId: UUID? = null,
    val name: String,
) {
    fun toDomain(userId: String) = Device(
        userId = userId,
        mac = mac,
        plantId = plantId,
        name = name,
        photoId = null,
        createdDate = Calendar.getInstance(),
        updatedDate = Calendar.getInstance(),
        uuid = UUID.randomUUID(),
    )
}
