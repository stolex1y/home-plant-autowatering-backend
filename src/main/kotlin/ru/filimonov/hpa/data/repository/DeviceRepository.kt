package ru.filimonov.hpa.data.repository

import org.springframework.data.repository.CrudRepository
import ru.filimonov.hpa.data.model.DeviceEntity
import java.util.*

interface DeviceRepository : CrudRepository<DeviceEntity, UUID> {
    fun findByMac(mac: String): DeviceEntity?
    fun findAllByUserId(userId: String): List<DeviceEntity>
    fun existsByUserIdAndUuid(userId: String, deviceId: UUID): Boolean
}
