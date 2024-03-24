package ru.filimonov.hpa.data.service

import org.apache.commons.logging.LogFactory
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated
import ru.filimonov.hpa.data.model.DeviceEntity.Companion.toEntity
import ru.filimonov.hpa.data.repository.DeviceRepository
import ru.filimonov.hpa.domain.model.Device
import ru.filimonov.hpa.domain.service.DeviceService
import ru.filimonov.hpa.domain.service.PlantService
import ru.filimonov.hpa.domain.validation.domainRequireNotNull
import ru.filimonov.hpa.domain.validation.domainRequireNull
import java.util.*

@Service
@Validated
class DeviceServiceImpl(
    private val repository: DeviceRepository,
    private val plantService: PlantService,
) : DeviceService {
    private val log = LogFactory.getLog(javaClass)

    override fun updateDevice(device: Device): Device {
        checkUserHasDevice(userId = device.userId, deviceId = device.uuid)
        checkUniqueMacAddress(device)
        checkPlantIsExists(userId = device.userId, plantId = device.plantId)
        return repository.save(device.toEntity()).toDomain()
    }

    override fun getAllDevicesByUserId(userId: String): List<Device> {
        return repository.findAllByUserId(userId).map {
            it.toDomain()
        }
    }

    override fun getDeviceById(userId: String, deviceId: UUID): Device {
        checkUserHasDevice(userId = userId, deviceId = deviceId)
        return repository.findById(deviceId).map {
            it.toDomain()
        }.get()
    }

    override fun addDevice(device: Device): Device {
        checkUniqueMacAddress(device)
        checkPlantIsExists(userId = device.userId, plantId = device.plantId)
        return repository.save(device.toEntity()).toDomain()
    }

    override fun deleteDevice(userId: String, deviceId: UUID) {
        checkUserHasDevice(userId = userId, deviceId = deviceId)
        repository.deleteById(deviceId)
    }

    override fun isUserDevice(userId: String, deviceId: UUID): Boolean {
        return repository.existsByUserIdAndUuid(userId, deviceId)
    }

    private fun checkUserHasDevice(userId: String, deviceId: UUID) {
        if (!isUserDevice(userId = userId, deviceId = deviceId)) {
            log.info("user with id=$userId tried to read device data with id=$deviceId")
            throw IllegalAccessException()
        }
    }

    private fun checkUniqueMacAddress(device: Device) {
        domainRequireNull(repository.findByMac(device.mac), "mac") {
            "This user has already added a device with this MAC address"
        }
    }

    private fun checkPlantIsExists(userId: String, plantId: UUID?) {
        if (plantId == null) {
            return
        }
        domainRequireNotNull(plantService.isUserPlant(userId = userId, plantId = plantId), "plantId") {
            "The plant was not found"
        }
    }
}
