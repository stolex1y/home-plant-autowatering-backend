package ru.filimonov.hpa.data.service.readings

import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.validation.annotation.Validated
import ru.filimonov.hpa.data.model.readings.AirTempReadingEntity
import ru.filimonov.hpa.data.model.readings.toEntity
import ru.filimonov.hpa.data.repository.readings.AirTempReadingsRepository
import ru.filimonov.hpa.domain.model.readings.AirTempReading
import ru.filimonov.hpa.domain.model.readings.SensorReading
import ru.filimonov.hpa.domain.service.DeviceService
import ru.filimonov.hpa.domain.service.readings.AirTempReadingsService
import java.util.*

@Service
@Validated
class AirTempReadingsServiceImpl @Autowired constructor(
    deviceService: DeviceService,
    repository: AirTempReadingsRepository,
) : AirTempReadingsService,
    BaseSensorReadingsServiceImpl<Float, AirTempReadingEntity, AirTempReading, AirTempReadingsRepository>(
        deviceService = deviceService,
        repository = repository,
        toDomain = AirTempReadingEntity::toDomain,
        toEntity = AirTempReading::toEntity
    ) {

    private val log = LogFactory.getLog(javaClass)

    @Transactional
    override fun add(
        userId: String,
        readingModel: AirTempReading,
    ): AirTempReading {
        checkUserHasDevice(userId = userId, deviceId = readingModel.deviceId)
        return repository.save(readingModel.toEntity()).toDomain()
    }

    override fun createSensorReading(reading: Double, timestamp: Calendar): SensorReading<Float> =
        SensorReading(reading = reading.toFloat(), timestamp = timestamp)
}
