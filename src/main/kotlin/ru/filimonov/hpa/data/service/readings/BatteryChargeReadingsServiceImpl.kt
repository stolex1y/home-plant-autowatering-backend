package ru.filimonov.hpa.data.service.readings

import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.validation.annotation.Validated
import ru.filimonov.hpa.data.model.readings.BatteryChargeReadingEntity
import ru.filimonov.hpa.data.model.readings.toEntity
import ru.filimonov.hpa.data.repository.readings.BatteryChargeReadingsRepository
import ru.filimonov.hpa.domain.model.readings.BatteryChargeReading
import ru.filimonov.hpa.domain.model.readings.SensorReading
import ru.filimonov.hpa.domain.service.DeviceService
import ru.filimonov.hpa.domain.service.readings.BatteryChargeReadingsService
import java.util.*

@Service
@Validated
class BatteryChargeReadingsServiceImpl @Autowired constructor(
    deviceService: DeviceService,
    repository: BatteryChargeReadingsRepository,
) : BatteryChargeReadingsService,
    BaseSensorReadingsServiceImpl<Float, BatteryChargeReadingEntity, BatteryChargeReading, BatteryChargeReadingsRepository>(
        deviceService = deviceService,
        repository = repository,
        toDomain = BatteryChargeReadingEntity::toDomain,
        toEntity = BatteryChargeReading::toEntity
    ) {

    private val log = LogFactory.getLog(javaClass)

    @Transactional
    override fun add(
        userId: String,
        readingModel: BatteryChargeReading,
    ): BatteryChargeReading {
        checkUserHasDevice(userId = userId, deviceId = readingModel.deviceId)
        return repository.save(readingModel.toEntity()).toDomain()
    }

    override fun createSensorReading(reading: Double, timestamp: Calendar): SensorReading<Float> =
        SensorReading(reading = reading.toFloat(), timestamp = timestamp)
}
