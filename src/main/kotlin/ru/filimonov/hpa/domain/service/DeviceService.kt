package ru.filimonov.hpa.domain.service

import jakarta.validation.Valid
import ru.filimonov.hpa.domain.model.Device
import java.util.*

interface DeviceService {
    fun getDeviceById(userId: String, deviceId: UUID): Device
    fun getAllDevicesByUserId(userId: String): List<Device>
    fun isUserDevice(userId: String, deviceId: UUID): Boolean
    fun addDevice(@Valid device: Device): Device
    fun deleteDevice(userId: String, deviceId: UUID)
    fun updateDevice(@Valid device: Device): Device
}
