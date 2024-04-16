package ru.filimonov.hpa.domain.model

import java.util.*

data class Photo(
    val photo: ByteArray,
    val uuid: UUID = UUID.randomUUID(),
    val createdDate: Calendar = Calendar.getInstance(),
    val updatedDate: Calendar = Calendar.getInstance(),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Photo

        return uuid == other.uuid
    }

    override fun hashCode(): Int {
        return uuid.hashCode()
    }
}
