package ru.filimonov.hpa.domain.service

import ru.filimonov.hpa.domain.model.Device
import java.util.*

interface DeviceService {
    fun getDeviceByMac(mac: String): Device?
    fun getAllDevicesByUserId(userId: String): List<Device>
    fun isUserDevice(userId: String, deviceId: UUID): Boolean
}
