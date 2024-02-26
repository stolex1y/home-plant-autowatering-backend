package ru.filimonov.hpa.domain.model

import java.sql.Timestamp
import java.time.Instant
import java.util.*

data class Device(
    val userId: UUID,
    val mac: String,
    val plantId: UUID? = null,
    val createdDate: Timestamp = Timestamp.from(Instant.now()),
    val uuid: UUID = UUID.randomUUID()
)
