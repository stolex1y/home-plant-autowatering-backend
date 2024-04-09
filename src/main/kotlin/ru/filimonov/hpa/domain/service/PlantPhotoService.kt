package ru.filimonov.hpa.domain.service

import ru.filimonov.hpa.domain.model.PlantPhoto
import java.util.*

interface PlantPhotoService {
    fun updatePlantPhoto(userId: String, plantId: UUID, photoBytes: ByteArray)
    fun getPlantPhoto(userId: String, plantId: UUID): PlantPhoto
    fun getPhotoUpdatedDate(userId: String, plantId: UUID): Calendar?
    fun deletePlantPhoto(userId: String, plantId: UUID)
}
