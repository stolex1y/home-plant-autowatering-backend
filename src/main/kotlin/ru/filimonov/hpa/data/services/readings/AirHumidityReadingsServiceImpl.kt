package ru.filimonov.hpa.data.services.readings

import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.validation.annotation.Validated
import ru.filimonov.hpa.data.model.readings.AirHumidityReadingEntity
import ru.filimonov.hpa.data.model.readings.toEntity
import ru.filimonov.hpa.data.repositories.readings.AirHumidityReadingsRepository
import ru.filimonov.hpa.domain.model.readings.AirHumidityReading
import ru.filimonov.hpa.domain.services.DeviceService
import ru.filimonov.hpa.domain.services.readings.AirHumidityReadingsService
import java.util.*

@Service
@Validated
class AirHumidityReadingsServiceImpl @Autowired constructor(
    deviceService: DeviceService,
    repository: AirHumidityReadingsRepository,
) : AirHumidityReadingsService,
    BaseSensorReadingsServiceImpl<Float, AirHumidityReadingEntity, AirHumidityReading, AirHumidityReadingsRepository>(
        deviceService = deviceService,
        repository = repository,
        toDomain = AirHumidityReadingEntity::toDomain,
        toEntity = AirHumidityReading::toEntity
    ) {

    private val log = LogFactory.getLog(javaClass)

    @Transactional
    override fun add(
        userId: String,
        readingModel: AirHumidityReading,
    ): AirHumidityReading {
        checkUserHasDevice(userId = userId, deviceId = readingModel.deviceId)
        return repository.save(readingModel.toEntity()).toDomain()
    }

    override fun createDomainReading(deviceId: UUID, reading: Double, timestamp: Calendar): AirHumidityReading =
        AirHumidityReading(deviceId = deviceId, reading = reading.toFloat(), timestamp = timestamp)
}
