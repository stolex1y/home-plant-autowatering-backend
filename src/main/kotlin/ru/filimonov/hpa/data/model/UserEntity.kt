package ru.filimonov.hpa.data.model

import jakarta.persistence.*
import java.sql.Timestamp
import java.time.Instant
import java.util.*

@Entity
@Table(name = "users")
data class UserEntity(
    @Temporal(value = TemporalType.TIMESTAMP)
    val createdDate: Timestamp = Timestamp.from(Instant.now()),
    @Id
    val uuid: UUID = UUID.randomUUID()
)
