package ru.filimonov.hpa.data.services

import org.apache.commons.logging.LogFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.validation.annotation.Validated
import ru.filimonov.hpa.data.model.toEntity
import ru.filimonov.hpa.data.repositories.DeviceRepository
import ru.filimonov.hpa.domain.model.Configuration
import ru.filimonov.hpa.domain.model.Device
import ru.filimonov.hpa.domain.model.toConfiguration
import ru.filimonov.hpa.domain.services.DeviceService
import ru.filimonov.hpa.domain.services.PlantService
import ru.filimonov.hpa.domain.services.UpdateConfigurationEventService
import ru.filimonov.hpa.domain.validation.domainRequire
import java.util.*

@Service
@Validated
class DeviceServiceImpl(
    private val repository: DeviceRepository,
    private val plantService: PlantService,
    private val updateConfigurationEventService: UpdateConfigurationEventService,
) : DeviceService {
    private val log = LogFactory.getLog(javaClass)

    @Transactional
    override fun updateDevice(device: Device): Device {
        checkUserHasDevice(userId = device.userId, deviceId = device.uuid)
        return saveDevice(device = device)
    }

    @Transactional(readOnly = true)
    override fun getAllDevicesByUserId(userId: String): List<Device> {
        return repository.findAllByUserIdOrderByUuid(userId).map {
            it.toDomain()
        }
    }

    @Transactional(readOnly = true)
    override fun getDeviceById(userId: String, deviceId: UUID): Device {
        checkUserHasDevice(userId = userId, deviceId = deviceId)
        return repository.findById(deviceId).map {
            it.toDomain()
        }.get()
    }

    @Transactional
    override fun addDevice(device: Device): Device {
        return saveDevice(device = device)
    }

    @Transactional
    override fun deleteDevice(userId: String, deviceId: UUID) {
        repository.deleteById(deviceId)
        updateConfigurationEventService.sendUpdateConfigEvent(
            deviceId = deviceId,
            configuration = Configuration.defaultConfiguration()
        )
    }

    @Transactional(readOnly = true)
    override fun isUserDevice(userId: String, deviceId: UUID): Boolean {
        return repository.existsByUserIdAndUuid(userId = userId, deviceId = deviceId)
    }

    private fun checkUserHasDevice(userId: String, deviceId: UUID) {
        if (!repository.existsByUserIdAndUuid(userId = userId, deviceId = deviceId)) {
            log.info("user with id=$userId tried to read device data with id=$deviceId")
            throw IllegalAccessException()
        }
    }

    private fun checkUniqueMacAddress(device: Device) {
        val other = repository.findByMac(device.mac) ?: return
        domainRequire(other.uuid == device.uuid, "mac") {
            "This user has already added a device with this MAC address"
        }
    }

    private fun saveDevice(device: Device): Device {
        checkUniqueMacAddress(device)
        if (device.plantId != null) {
            val plant = plantService.getPlantById(userId = device.userId, plantId = device.plantId)
            updateConfigurationEventService.sendUpdateConfigEvent(
                deviceId = device.uuid,
                configuration = plant.toConfiguration()
            )
        }
        val saved = repository.save(device.toEntity()).toDomain()
        if (saved.uuid != device.uuid) {
            repository.updateUuidByUuid(saved.uuid, device.uuid)
        }
        return repository.findById(device.uuid).get().toDomain()
    }
}
