package ru.filimonov.hpa.data.service.readings

import org.apache.commons.logging.LogFactory
import org.springframework.data.domain.Range
import org.springframework.transaction.annotation.Transactional
import ru.filimonov.hpa.core.bounds
import ru.filimonov.hpa.core.floorToDay
import ru.filimonov.hpa.core.floorToHour
import ru.filimonov.hpa.core.toTimestamp
import ru.filimonov.hpa.data.repository.readings.BaseSensorReadingsRepository
import ru.filimonov.hpa.domain.model.PeriodUnit
import ru.filimonov.hpa.domain.model.readings.SensorReading
import ru.filimonov.hpa.domain.service.DeviceService
import ru.filimonov.hpa.domain.service.readings.BaseSensorReadingsService
import java.util.*

abstract class BaseSensorReadingsServiceImpl<
        Reading : Number,
        ReadingEntity,
        DomainReading : SensorReading<Reading>,
        ReadingRepository : BaseSensorReadingsRepository<ReadingEntity>>(
    protected val deviceService: DeviceService,
    protected val repository: ReadingRepository,
    private val toDomain: ReadingEntity.() -> DomainReading,
    private val toEntity: DomainReading.() -> ReadingEntity,
) : BaseSensorReadingsService<Reading, DomainReading> {

    private val log = LogFactory.getLog(javaClass)

    protected abstract fun createSensorReading(
        reading: Double,
        timestamp: Calendar,
    ): SensorReading<Reading>

    @Transactional(readOnly = true)
    override fun getLast(userId: String, deviceId: UUID): SensorReading<Reading>? {
        checkUserHasDevice(userId = userId, deviceId = deviceId)
        val entity = repository.findTopByDeviceIdOrderByTimestampDesc(deviceId)
        return entity?.toDomain()
    }

    @Transactional(readOnly = true)
    override fun getForPeriodByTimeUnit(
        userId: String, deviceId: UUID, period: Range<Calendar>, periodUnit: PeriodUnit
    ): List<SensorReading<Reading>> {
        checkUserHasDevice(userId = userId, deviceId = deviceId)
        return when (periodUnit) {
            PeriodUnit.DAY -> getReadingsForPeriodRounded(deviceId, period) { floorToDay() }
            PeriodUnit.HOUR -> getReadingsForPeriodRounded(deviceId, period) { floorToHour() }
        }
    }

    @Transactional
    override fun deleteForPeriod(userId: String, deviceId: UUID, period: Range<Calendar>): Long {
        checkUserHasDevice(userId = userId, deviceId = deviceId)
        val periodBounds = period.bounds()
        return repository.deleteAllByDeviceIdAndTimestampBetween(
            deviceId,
            periodBounds.first.toTimestamp().apply { time-- },
            periodBounds.second.toTimestamp().apply { time++ }
        )
    }

    protected fun checkUserHasDevice(userId: String, deviceId: UUID) {
        if (!deviceService.isUserDevice(userId = userId, deviceId = deviceId)) {
            log.info("user with id=$userId tried to read device data with id=$deviceId")
            throw IllegalAccessException()
        }
    }

    private fun getReadingsForPeriodRounded(
        deviceId: UUID, period: Range<Calendar>, round: Calendar.() -> Unit
    ): List<SensorReading<Reading>> {
        val periodBounds = period.bounds()
        return repository.findAllByDeviceIdAndTimestampBetweenOrderByTimestampAsc(
            deviceId, periodBounds.first.toTimestamp(), periodBounds.second.toTimestamp()
        ).map(toDomain).onEach { it.timestamp.round() }.groupBy { it.timestamp }
            .mapValues { groupEntry ->
                groupEntry.value
                    .map { it.reading.toDouble() }
                    .average()
            }.map { createSensorReading(reading = it.value, timestamp = it.key) }
    }
}
