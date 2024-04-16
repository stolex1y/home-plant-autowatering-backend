package ru.filimonov.hpa.domain.model

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.PastOrPresent
import ru.filimonov.hpa.core.constraints.MacAddress
import java.util.*

data class Device(
    val userId: String,
    @MacAddress
    val mac: String,
    @NotBlank
    val name: String,
    val photoId: UUID? = null,
    val plantId: UUID? = null,
    @PastOrPresent
    val createdDate: Calendar = Calendar.getInstance(),
    val uuid: UUID = UUID.randomUUID(),
)
