package ru.filimonov.hpa.data.service

import org.apache.commons.logging.LogFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.validation.annotation.Validated
import ru.filimonov.hpa.data.model.PhotoEntity.Companion.toEntity
import ru.filimonov.hpa.data.repository.PhotoRepository
import ru.filimonov.hpa.domain.model.Photo
import ru.filimonov.hpa.domain.service.PlantPhotoService
import ru.filimonov.hpa.domain.service.PlantService
import java.util.*

@Service
@Validated
class PlantPhotoServiceImpl(
    private val plantService: PlantService,
    private val photoRepository: PhotoRepository,
) : PlantPhotoService {
    private val log = LogFactory.getLog(javaClass)

    @Transactional
    override fun updatePlantPhoto(userId: String, plantId: UUID, photoBytes: ByteArray) {
        val plant = plantService.getPlantById(userId = userId, plantId = plantId)
        if (plant.photoId == null) {
            val addedPhoto = photoRepository.save(Photo(photo = photoBytes).toEntity())
            plantService.updatePlant(userId, plant.copy(photoId = addedPhoto.uuid))
        } else {
            val beforeUpdatePhoto =
                photoRepository.findById(plant.photoId)
                    .orElseThrow { IllegalArgumentException("Not found resource") }
            photoRepository.save(beforeUpdatePhoto.copy(photo = photoBytes))
        }
    }

    @Transactional(readOnly = true)
    override fun getPlantPhoto(userId: String, plantId: UUID): Photo? {
        val photoId = plantService.getPlantById(userId = userId, plantId = plantId).photoId
            ?: return null
        return photoRepository.findById(photoId).get().toDomain()
    }

    @Transactional(readOnly = true)
    override fun getPhotoUpdatedDate(userId: String, plantId: UUID): Calendar? {
        val photoId = plantService.getPlantById(userId = userId, plantId = plantId).photoId ?: return null
        return photoRepository.findById(photoId).get().toDomain().updatedDate
    }

    @Transactional
    override fun deletePlantPhoto(userId: String, plantId: UUID) {
        val photoId = plantService.getPlantById(userId = userId, plantId = plantId).photoId ?: return
        photoRepository.deleteById(photoId)
    }
}
