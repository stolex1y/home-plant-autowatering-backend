package ru.filimonov.hpa.data.service

import org.springframework.stereotype.Service
import ru.filimonov.hpa.data.repository.DeviceRepository
import ru.filimonov.hpa.domain.model.Device
import ru.filimonov.hpa.domain.service.DeviceService
import java.util.*
import kotlin.jvm.optionals.getOrNull

@Service
class DeviceServiceImpl(
    private val repository: DeviceRepository
) : DeviceService {
    override fun getDeviceByMac(mac: String): Device? {
        return repository.findByMac(mac)?.toDomain()
    }

    override fun getAllDevicesByUserId(userId: String): List<Device> {
        return repository.findAllByUserId(userId).map {
            it.toDomain()
        }
    }

    override fun getDeviceById(id: UUID): Device? {
        return repository.findById(id).map {
            it.toDomain()
        }.getOrNull()
    }

    override fun isDeviceExists(id: UUID): Boolean {
        return repository.existsById(id)
    }

    override fun isUserDevice(userId: String, deviceId: UUID): Boolean {
        return repository.existsByUserIdAndUuid(userId, deviceId)
    }
}
