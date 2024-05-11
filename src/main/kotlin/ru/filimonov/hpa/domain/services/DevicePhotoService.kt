package ru.filimonov.hpa.domain.services

import ru.filimonov.hpa.domain.model.Photo
import java.util.*

interface DevicePhotoService {
    fun updateDevicePhoto(userId: String, deviceId: UUID, photoBytes: ByteArray)
    fun getDevicePhoto(userId: String, deviceId: UUID): Photo?
    fun getPhotoUpdatedDate(userId: String, deviceId: UUID): Calendar?
    fun deleteDevicePhoto(userId: String, deviceId: UUID)
}