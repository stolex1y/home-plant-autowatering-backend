package ru.filimonov.hpa.data.model.readings

import java.io.Serializable
import java.sql.Timestamp
import java.time.Instant
import java.util.*

data class ReadingEntityId(
    val timestamp: Timestamp = Timestamp.from(Instant.now()),
    val deviceId: UUID = UUID.randomUUID(),
) : Serializable
