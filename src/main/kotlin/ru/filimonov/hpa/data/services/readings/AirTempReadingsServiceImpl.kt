package ru.filimonov.hpa.data.services.readings

import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.validation.annotation.Validated
import ru.filimonov.hpa.data.model.readings.AirTempReadingEntity
import ru.filimonov.hpa.data.model.readings.toEntity
import ru.filimonov.hpa.data.repositories.readings.AirTempReadingsRepository
import ru.filimonov.hpa.domain.model.readings.AirTempReading
import ru.filimonov.hpa.domain.services.DeviceService
import ru.filimonov.hpa.domain.services.readings.AirTempReadingsService
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

    override fun createDomainReading(deviceId: UUID, reading: Double, timestamp: Calendar): AirTempReading {
        return AirTempReading(deviceId = deviceId, reading = reading.toFloat(), timestamp = timestamp)
    }
}
