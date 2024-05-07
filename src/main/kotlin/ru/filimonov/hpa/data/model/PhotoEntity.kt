package ru.filimonov.hpa.data.model

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import ru.filimonov.hpa.core.toCalendar
import ru.filimonov.hpa.domain.model.Photo
import java.sql.Timestamp
import java.time.Instant
import java.util.*

@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(name = "photos")
data class PhotoEntity(
    val photo: ByteArray,

    @Id
    val uuid: UUID,
) {
    @CreatedDate
    @Column(updatable = false)
    var createdDate: Timestamp = Timestamp.from(Instant.now())

    @LastModifiedDate
    var updatedDate: Timestamp = Timestamp.from(Instant.now())

    fun toDomain() = Photo(
        photo = photo,
        uuid = uuid,
        createdDate = createdDate.toCalendar(),
        updatedDate = updatedDate.toCalendar(),
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PhotoEntity

        return uuid == other.uuid
    }

    override fun hashCode(): Int {
        return uuid.hashCode()
    }
}

fun Photo.toEntity() = PhotoEntity(
    photo = photo,
    uuid = uuid,
)
