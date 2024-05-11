package ru.filimonov.hpa.domain.model

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.PastOrPresent
import ru.filimonov.hpa.common.constraints.MacAddress
import java.util.*

data class Device(
    val userId: String,

    @MacAddress
    val mac: String,

    @NotBlank
    val name: String,

    val photoId: UUID?,
    val plantId: UUID?,

    @PastOrPresent
    val createdDate: Calendar,

    @PastOrPresent
    val updatedDate: Calendar,

    val uuid: UUID,
)
