package ru.filimonov.hpa.data.model

import jakarta.persistence.*
import ru.filimonov.hpa.domain.model.Device
import java.sql.Timestamp
import java.time.Instant
import java.util.*

@Entity
@Table(name = "devices")
data class DeviceEntity(
    @Column(name = "user_id")
    val userId: String,
    val mac: String,
    @Column(name = "plant")
    val plantId: UUID? = null,
    val createdDate: Timestamp = Timestamp.from(Instant.now()),
    @GeneratedValue
    @Id
    val uuid: UUID = UUID.randomUUID()
) {
    companion object {
        fun Device.toEntity() = DeviceEntity(
            userId = userId,
            mac = mac,
            plantId = plantId,
            createdDate = createdDate,
            uuid = uuid
        )
    }

    fun toDomain() = Device(
        userId = userId,
        mac = mac,
        plantId = plantId,
        createdDate = createdDate,
        uuid = uuid
    )
}
