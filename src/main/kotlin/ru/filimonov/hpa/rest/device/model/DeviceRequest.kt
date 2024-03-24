package ru.filimonov.hpa.rest.device.model

import jakarta.validation.constraints.Pattern
import ru.filimonov.hpa.domain.model.Device
import java.util.*

data class DeviceRequest(
    @Pattern(regexp = "^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$")
    val mac: String,
    val plantId: UUID? = null,
) {
    fun toDomain(userId: String) = Device(
        userId = userId,
        mac = mac,
        plantId = plantId,
    )
}
