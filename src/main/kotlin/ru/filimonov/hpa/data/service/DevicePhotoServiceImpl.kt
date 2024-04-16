package ru.filimonov.hpa.data.service

import org.apache.commons.logging.LogFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.validation.annotation.Validated
import ru.filimonov.hpa.data.model.PhotoEntity.Companion.toEntity
import ru.filimonov.hpa.data.repository.PhotoRepository
import ru.filimonov.hpa.domain.model.Photo
import ru.filimonov.hpa.domain.service.DevicePhotoService
import ru.filimonov.hpa.domain.service.DeviceService
import java.util.*

@Service
@Validated
class DevicePhotoServiceImpl(
    private val photoRepository: PhotoRepository,
    private val deviceService: DeviceService,
) : DevicePhotoService {
    private val log = LogFactory.getLog(javaClass)

    @Transactional
    override fun updateDevicePhoto(userId: String, deviceId: UUID, photoBytes: ByteArray) {
        val device = deviceService.getDeviceById(userId = userId, deviceId = deviceId)
        if (device.photoId == null) {
            val addedPhoto = photoRepository.save(Photo(photo = photoBytes).toEntity())
            deviceService.updateDevice(device.copy(photoId = addedPhoto.uuid))
        } else {
            val beforeUpdatePhoto =
                photoRepository.findById(device.photoId)
                    .orElseThrow { IllegalArgumentException("Not found resource") }
            photoRepository.save(beforeUpdatePhoto.copy(photo = photoBytes))
        }
    }

    @Transactional(readOnly = true)
    override fun getDevicePhoto(userId: String, deviceId: UUID): Photo? {
        val photoId = deviceService.getDeviceById(userId = userId, deviceId = deviceId).photoId
            ?: return null
        return photoRepository.findById(photoId).get().toDomain()
    }

    @Transactional(readOnly = true)
    override fun getPhotoUpdatedDate(userId: String, deviceId: UUID): Calendar? {
        val photoId = deviceService.getDeviceById(userId = userId, deviceId = deviceId).photoId ?: return null
        return photoRepository.findById(photoId).get().toDomain().updatedDate
    }

    @Transactional
    override fun deleteDevicePhoto(userId: String, deviceId: UUID) {
        val photoId = deviceService.getDeviceById(userId = userId, deviceId = deviceId).photoId ?: return
        photoRepository.deleteById(photoId)
    }
}
