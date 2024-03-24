package ru.filimonov.hpa.domain.model

import java.util.*

data class Device(
    val userId: String,
    val mac: String,
    val plantId: UUID? = null,
    val createdDate: Calendar = Calendar.getInstance(),
    val uuid: UUID = UUID.randomUUID(),
)
