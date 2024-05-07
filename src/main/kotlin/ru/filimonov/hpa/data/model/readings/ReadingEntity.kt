package ru.filimonov.hpa.data.model.readings

import jakarta.persistence.*
import java.sql.Timestamp
import java.util.*

@MappedSuperclass
@IdClass(ReadingEntityId::class)
data class ReadingEntity<T>(
    val reading: T,

    @Column(name = "device")
    @Id
    val deviceId: UUID,

    @Temporal(value = TemporalType.TIMESTAMP)
    @Id
    val timestamp: Timestamp,
)
