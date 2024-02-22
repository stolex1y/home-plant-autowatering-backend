package ru.filimonov.hpa.data.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.filimonov.hpa.data.model.SoilMoistureReadingEntity
import java.sql.Timestamp
import java.util.*

@Repository
interface SoilMoistureReadingRepository : CrudRepository<SoilMoistureReadingEntity, UUID> {
    fun deleteAllByDeviceIdAndTimestampBetween(
        deviceId: UUID,
        timestampBegin: Timestamp,
        timestampEnd: Timestamp
    ): Long

    fun findTopByDeviceIdOrderByTimestampDesc(deviceId: UUID): SoilMoistureReadingEntity?

    fun findAllByDeviceIdAndTimestampBetweenOrderByTimestampDesc(
        deviceId: UUID,
        timestampBegin: Timestamp,
        timestampEnd: Timestamp
    ): List<SoilMoistureReadingEntity>

    fun findAllByDeviceIdOrderByTimestampDesc(deviceId: UUID): List<SoilMoistureReadingEntity>
}
