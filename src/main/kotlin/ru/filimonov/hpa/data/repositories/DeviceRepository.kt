package ru.filimonov.hpa.data.repositories

import org.springframework.data.repository.CrudRepository
import ru.filimonov.hpa.data.model.DeviceEntity
import java.util.*

interface DeviceRepository : CrudRepository<DeviceEntity, UUID> {
    fun findByMac(mac: String): DeviceEntity?
    fun findAllByUserIdOrderByUuid(userId: String): List<DeviceEntity>
    fun existsByUserIdAndUuid(userId: String, deviceId: UUID): Boolean
    fun findByPlantId(plantId: UUID): DeviceEntity?
    fun findByUuidAndUserId(deviceId: UUID, userId: String): DeviceEntity?
}
