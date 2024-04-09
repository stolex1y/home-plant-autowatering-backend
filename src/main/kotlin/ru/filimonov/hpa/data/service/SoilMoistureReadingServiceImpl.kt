package ru.filimonov.hpa.data.service

import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Range
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.validation.annotation.Validated
import ru.filimonov.hpa.core.bounds
import ru.filimonov.hpa.core.floorToDay
import ru.filimonov.hpa.core.floorToHour
import ru.filimonov.hpa.core.toTimestamp
import ru.filimonov.hpa.data.model.SoilMoistureReadingEntity
import ru.filimonov.hpa.data.model.SoilMoistureReadingEntity.Companion.toEntity
import ru.filimonov.hpa.data.repository.SoilMoistureReadingRepository
import ru.filimonov.hpa.domain.model.PeriodUnit
import ru.filimonov.hpa.domain.model.SensorReading
import ru.filimonov.hpa.domain.model.SoilMoistureReading
import ru.filimonov.hpa.domain.service.DeviceService
import ru.filimonov.hpa.domain.service.sensors.SoilMoistureReadingService
import java.util.*

@Service
@Validated
class SoilMoistureReadingServiceImpl @Autowired constructor(
    private val deviceService: DeviceService,
    private val repository: SoilMoistureReadingRepository,
) : SoilMoistureReadingService {

    private val log = LogFactory.getLog(javaClass)

    @Transactional(readOnly = true)
    override fun getLastReading(userId: String, deviceId: UUID): SensorReading<Float>? {
        checkUserHasDevice(userId = userId, deviceId = deviceId)
        val entity = repository.findTopByDeviceIdOrderByTimestampDesc(deviceId) ?: return null
        return entity.toDomain()
    }

    @Transactional(readOnly = true)
    override fun getReadingsForPeriodByTimeUnit(
        userId: String,
        deviceId: UUID,
        period: Range<Calendar>,
        periodUnit: PeriodUnit,
    ): List<SensorReading<Float>> {
        checkUserHasDevice(userId = userId, deviceId = deviceId)
        return when (periodUnit) {
            PeriodUnit.DAY -> getReadingsForPeriodRounded(deviceId, period) { floorToDay() }
            PeriodUnit.HOUR -> getReadingsForPeriodRounded(deviceId, period) { floorToHour() }
        }
    }

    @Transactional
    override fun deleteReadingsForPeriod(userId: String, deviceId: UUID, period: Range<Calendar>): Long {
        checkUserHasDevice(userId = userId, deviceId = deviceId)
        val periodBounds = period.bounds()
        return repository.deleteAllByDeviceIdAndTimestampBetween(
            deviceId,
            periodBounds.first.toTimestamp().apply { time-- },
            periodBounds.second.toTimestamp().apply { time++ }
        )
    }

    @Transactional
    override fun addReading(
        userId: String,
        soilMoistureReading: SoilMoistureReading,
    ): SoilMoistureReading {
        checkUserHasDevice(userId = userId, deviceId = soilMoistureReading.deviceId)
        return repository.save(soilMoistureReading.toEntity()).toDomain()
    }

    private fun checkUserHasDevice(userId: String, deviceId: UUID) {
        if (!deviceService.isUserDevice(userId = userId, deviceId = deviceId)) {
            log.info("user with id=$userId tried to read device data with id=$deviceId")
            throw IllegalAccessException()
        }
    }

    private fun getReadingsForPeriodRounded(
        deviceId: UUID,
        period: Range<Calendar>,
        round: Calendar.() -> Unit
    ): List<SensorReading<Float>> {
        val periodBounds = period.bounds()
        return repository.findAllByDeviceIdAndTimestampBetweenOrderByTimestampAsc(
            deviceId,
            periodBounds.first.toTimestamp(),
            periodBounds.second.toTimestamp()
        ).map(SoilMoistureReadingEntity::toDomain)
            .onEach { it.timestamp.round() }
            .groupBy { it.timestamp }
            .mapValues { groupEntry ->
                groupEntry.value.map { it.reading }.average().toFloat()
            }
            .map { SensorReading(reading = it.value, timestamp = it.key) }
    }
}
