package ru.filimonov.hpa.data.repository.readings

import org.springframework.data.repository.CrudRepository
import java.sql.Timestamp
import java.util.*

interface BaseSensorReadingsRepository<ReadingEntity> {
    fun deleteAllByDeviceIdAndTimestampBetween(
        deviceId: UUID,
        timestampBegin: Timestamp,
        timestampEnd: Timestamp
    ): Long

    fun findTopByDeviceIdOrderByTimestampDesc(deviceId: UUID): ReadingEntity?

    fun findAllByDeviceIdAndTimestampBetweenOrderByTimestampAsc(
        deviceId: UUID,
        timestampBegin: Timestamp,
        timestampEnd: Timestamp
    ): List<ReadingEntity>

    fun findAllByDeviceIdOrderByTimestampDesc(deviceId: UUID): List<ReadingEntity>
}
