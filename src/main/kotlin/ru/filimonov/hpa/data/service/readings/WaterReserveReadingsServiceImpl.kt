package ru.filimonov.hpa.data.service.readings

import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.validation.annotation.Validated
import ru.filimonov.hpa.data.model.readings.WaterReserveReadingEntity
import ru.filimonov.hpa.data.model.readings.toEntity
import ru.filimonov.hpa.data.repository.readings.WaterReserveReadingsRepository
import ru.filimonov.hpa.domain.model.readings.SensorReading
import ru.filimonov.hpa.domain.model.readings.WaterReserveReading
import ru.filimonov.hpa.domain.service.DeviceService
import ru.filimonov.hpa.domain.service.readings.WaterReserveReadingsService
import java.util.*

@Service
@Validated
class WaterReserveReadingsServiceImpl @Autowired constructor(
    deviceService: DeviceService,
    repository: WaterReserveReadingsRepository,
) : WaterReserveReadingsService,
    BaseSensorReadingsServiceImpl<Float, WaterReserveReadingEntity, WaterReserveReading, WaterReserveReadingsRepository>(
        deviceService = deviceService,
        repository = repository,
        toDomain = WaterReserveReadingEntity::toDomain,
        toEntity = WaterReserveReading::toEntity
    ) {

    private val log = LogFactory.getLog(javaClass)

    @Transactional
    override fun add(
        userId: String,
        readingModel: WaterReserveReading,
    ): WaterReserveReading {
        checkUserHasDevice(userId = userId, deviceId = readingModel.deviceId)
        return repository.save(readingModel.toEntity()).toDomain()
    }

    override fun createSensorReading(reading: Double, timestamp: Calendar): SensorReading<Float> =
        SensorReading(reading = reading.toFloat(), timestamp = timestamp)
}
