package ru.filimonov.hpa.data.service

import org.apache.commons.logging.LogFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.validation.annotation.Validated
import ru.filimonov.hpa.core.toCalendar
import ru.filimonov.hpa.data.model.PlantPhotoEntity.Companion.toEntity
import ru.filimonov.hpa.data.repository.PlantPhotoRepository
import ru.filimonov.hpa.domain.model.PlantPhoto
import ru.filimonov.hpa.domain.service.PlantPhotoService
import ru.filimonov.hpa.domain.service.PlantService
import java.util.*

@Service
@Validated
class PlantPhotoServiceImpl(
    private val plantService: PlantService,
    private val plantPhotoRepository: PlantPhotoRepository,
) : PlantPhotoService {
    private val log = LogFactory.getLog(javaClass)

    @Transactional
    override fun updatePlantPhoto(userId: String, plantId: UUID, photoBytes: ByteArray) {
        val plant = plantService.getPlantById(userId = userId, plantId = plantId)
        if (plant.photo == null) {
            val addedPhoto = plantPhotoRepository.save(PlantPhoto(photo = photoBytes).toEntity())
            plantService.updatePlant(userId, plant.copy(photo = addedPhoto.uuid))
        } else {
            val beforeUpdatePhoto =
                plantPhotoRepository.findById(plant.photo)
                    .orElseThrow { IllegalArgumentException("Not found resource") }
            plantPhotoRepository.save(beforeUpdatePhoto.copy(photo = photoBytes))
        }
    }

    @Transactional(readOnly = true)
    override fun getPlantPhoto(userId: String, plantId: UUID): PlantPhoto {
        val photoId = plantService.getPlantById(userId = userId, plantId = plantId).photo
            ?: throw IllegalArgumentException("Not found resource")
        return plantPhotoRepository.findById(photoId).get().toDomain()
    }

    @Transactional(readOnly = true)
    override fun getPhotoUpdatedDate(userId: String, plantId: UUID): Calendar? {
        val photoId = plantService.getPlantById(userId = userId, plantId = plantId).photo ?: return null
        return plantPhotoRepository.findById(photoId).get().toDomain().updatedDate
    }

    @Transactional
    override fun deletePlantPhoto(userId: String, plantId: UUID) {
        val photoId = plantService.getPlantById(userId = userId, plantId = plantId).photo ?: return
        plantPhotoRepository.deleteById(photoId)
    }

    private fun checkUserHasPlant(userId: String, plantId: UUID) {
        if (!plantService.isUserPlant(userId = userId, plantId = plantId)) {
            log.info("user with id=$userId tried to read plant with id=$plantId")
            throw IllegalAccessException()
        }
    }
}
