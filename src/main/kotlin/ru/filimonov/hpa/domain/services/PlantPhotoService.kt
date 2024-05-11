package ru.filimonov.hpa.domain.services

import ru.filimonov.hpa.domain.model.Photo
import java.util.*

interface PlantPhotoService {
    fun updatePlantPhoto(userId: String, plantId: UUID, photoBytes: ByteArray)
    fun getPlantPhoto(userId: String, plantId: UUID): Photo?
    fun getPhotoUpdatedDate(userId: String, plantId: UUID): Calendar?
    fun deletePlantPhoto(userId: String, plantId: UUID)
}
