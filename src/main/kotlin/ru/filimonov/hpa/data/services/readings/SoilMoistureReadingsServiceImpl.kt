package ru.filimonov.hpa.data.services.readings

import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.validation.annotation.Validated
import ru.filimonov.hpa.data.model.readings.SoilMoistureReadingEntity
import ru.filimonov.hpa.data.model.readings.toEntity
import ru.filimonov.hpa.data.repositories.readings.SoilMoistureReadingsRepository
import ru.filimonov.hpa.domain.model.readings.SoilMoistureReading
import ru.filimonov.hpa.domain.services.DeviceService
import ru.filimonov.hpa.domain.services.readings.SoilMoistureReadingsService
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

    override fun createDomainReading(deviceId: UUID, reading: Double, timestamp: Calendar): SoilMoistureReading {
        return SoilMoistureReading(deviceId = deviceId, reading = reading.toFloat(), timestamp = timestamp)
    }
}
