package ru.filimonov.hpa.data.service

import org.springframework.stereotype.Service
import ru.filimonov.hpa.data.repository.DeviceRepository
import ru.filimonov.hpa.domain.model.Device
import ru.filimonov.hpa.domain.service.DeviceService

@Service
class DeviceServiceImpl(
    private val repository: DeviceRepository
) : DeviceService {
    override fun getDeviceByMac(mac: String): Device? {
        return repository.findByMac(mac)?.toDomain()
    }
}
