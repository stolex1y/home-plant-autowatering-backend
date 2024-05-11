package ru.filimonov.hpa.data.services.readings

import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.validation.annotation.Validated
import ru.filimonov.hpa.data.model.readings.WaterReserveReadingEntity
import ru.filimonov.hpa.data.model.readings.toEntity
import ru.filimonov.hpa.data.repositories.readings.WaterReserveReadingsRepository
import ru.filimonov.hpa.domain.model.readings.WaterReserveReading
import ru.filimonov.hpa.domain.services.DeviceService
import ru.filimonov.hpa.domain.services.readings.WaterReserveReadingsService
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

    override fun createDomainReading(deviceId: UUID, reading: Double, timestamp: Calendar): WaterReserveReading {
        return WaterReserveReading(deviceId = deviceId, reading = reading.toFloat(), timestamp = timestamp)
    }
}
