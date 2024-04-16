package ru.filimonov.hpa.data.model

import jakarta.persistence.*
import ru.filimonov.hpa.core.toCalendar
import ru.filimonov.hpa.core.toTimestamp
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
    val name: String,
    @Column(name = "photo")
    val photoId: UUID? = null,
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
            createdDate = createdDate.toTimestamp(),
            uuid = uuid,
            photoId = photoId,
            name = name,
        )
    }

    fun toDomain() = Device(
        userId = userId,
        mac = mac,
        plantId = plantId,
        createdDate = createdDate.toCalendar(),
        uuid = uuid,
        name = name,
        photoId = photoId,
    )
}
