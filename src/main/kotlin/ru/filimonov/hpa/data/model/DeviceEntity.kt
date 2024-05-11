package ru.filimonov.hpa.data.model

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import ru.filimonov.hpa.common.toCalendar
import ru.filimonov.hpa.domain.model.Device
import java.sql.Timestamp
import java.time.Instant
import java.util.*

@Entity
@Table(name = "devices")
@EntityListeners(AuditingEntityListener::class)
data class DeviceEntity(
    @Column(name = "user_id")
    val userId: String,

    val mac: String,

    @Column(name = "plant")
    val plantId: UUID?,

    val name: String,

    @Column(name = "photo")
    val photoId: UUID?,

    @GeneratedValue
    @Id
    val uuid: UUID,
) {
    @CreatedDate
    @Column(updatable = false)
    var createdDate: Timestamp = Timestamp.from(Instant.now())

    @LastModifiedDate
    var updatedDate: Timestamp = Timestamp.from(Instant.now())

    fun toDomain() = Device(
        userId = userId,
        mac = mac,
        plantId = plantId,
        createdDate = createdDate.toCalendar(),
        updatedDate = updatedDate.toCalendar(),
        uuid = uuid,
        name = name,
        photoId = photoId,
    )
}

fun Device.toEntity() = DeviceEntity(
    userId = userId,
    mac = mac,
    plantId = plantId,
    uuid = uuid,
    photoId = photoId,
    name = name,
)
