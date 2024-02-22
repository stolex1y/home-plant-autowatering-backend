package ru.filimonov.hpa.data.model

import jakarta.persistence.*
import java.sql.Timestamp
import java.time.Instant
import java.util.*

@Entity
@Table(name = "devices")
data class DeviceEntity(
    @Column(name = "user_id")
    val userId: UUID,
    val mac: String,
    @Column(name = "plant")
    val plantId: UUID? = null,
    val createdDate: Timestamp = Timestamp.from(Instant.now()),
    @GeneratedValue
    @Id
    val uuid: UUID = UUID.randomUUID()
)
