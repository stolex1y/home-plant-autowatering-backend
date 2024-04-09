package ru.filimonov.hpa.data.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import ru.filimonov.hpa.core.toCalendar
import ru.filimonov.hpa.domain.model.PlantPhoto
import java.sql.Timestamp
import java.time.Instant
import java.util.*

@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(name = "plant_photos")
data class PlantPhotoEntity(
    val photo: ByteArray,
    @Id
    val uuid: UUID,
) {
    companion object {
        fun PlantPhoto.toEntity() = PlantPhotoEntity(
            photo = photo,
            uuid = uuid,
        )
    }

    @CreatedDate
    @Column(updatable = false)
    var createdDate: Timestamp = Timestamp.from(Instant.now())

    @LastModifiedDate
    var updatedDate: Timestamp = Timestamp.from(Instant.now())

    fun toDomain() = PlantPhoto(
        photo = photo,
        uuid = uuid,
        createdDate = createdDate.toCalendar(),
        updatedDate = updatedDate.toCalendar(),
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PlantPhotoEntity

        return uuid == other.uuid
    }

    override fun hashCode(): Int {
        return uuid.hashCode()
    }
}
