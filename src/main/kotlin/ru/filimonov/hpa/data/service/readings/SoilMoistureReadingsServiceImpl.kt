package ru.filimonov.hpa.data.service.readings

import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.validation.annotation.Validated
import ru.filimonov.hpa.data.model.readings.SoilMoistureReadingEntity
import ru.filimonov.hpa.data.model.readings.toEntity
import ru.filimonov.hpa.data.repository.readings.SoilMoistureReadingsRepository
import ru.filimonov.hpa.domain.model.readings.SensorReading
import ru.filimonov.hpa.domain.model.readings.SoilMoistureReading
import ru.filimonov.hpa.domain.service.DeviceService
import ru.filimonov.hpa.domain.service.readings.SoilMoistureReadingsService
import java.util.*

@Service
@Validated
class SoilMoistureReadingsServiceImpl @Autowired constructor(
    deviceService: DeviceService,
    repository: SoilMoistureReadingsRepository,
) : SoilMoistureReadingsService,
    BaseSensorReadingsServiceImpl<Float, SoilMoistureReadingEntity, SoilMoistureReading, SoilMoistureReadingsRepository>(
        deviceService = deviceService,
        repository = repository,
        toDomain = SoilMoistureReadingEntity::toDomain,
        toEntity = SoilMoistureReading::toEntity
    ) {

    private val log = LogFactory.getLog(javaClass)

    @Transactional
    override fun add(
        userId: String,
        readingModel: SoilMoistureReading,
    ): SoilMoistureReading {
        checkUserHasDevice(userId = userId, deviceId = readingModel.deviceId)
        return repository.save(readingModel.toEntity()).toDomain()
    }

    override fun createSensorReading(reading: Double, timestamp: Calendar): SensorReading<Float> =
        SensorReading(reading = reading.toFloat(), timestamp = timestamp)
}
