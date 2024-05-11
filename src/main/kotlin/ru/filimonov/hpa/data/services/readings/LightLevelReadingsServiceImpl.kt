package ru.filimonov.hpa.data.services.readings

import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.validation.annotation.Validated
import ru.filimonov.hpa.data.model.readings.LightLevelReadingEntity
import ru.filimonov.hpa.data.model.readings.toEntity
import ru.filimonov.hpa.data.repositories.readings.LightLevelReadingsRepository
import ru.filimonov.hpa.domain.model.readings.LightLevelReading
import ru.filimonov.hpa.domain.services.DeviceService
import ru.filimonov.hpa.domain.services.readings.LightLevelReadingsService
import java.util.*
import kotlin.math.roundToInt

@Service
@Validated
class LightLevelReadingsServiceImpl @Autowired constructor(
    deviceService: DeviceService,
    repository: LightLevelReadingsRepository,
) : LightLevelReadingsService,
    BaseSensorReadingsServiceImpl<Int, LightLevelReadingEntity, LightLevelReading, LightLevelReadingsRepository>(
        deviceService = deviceService,
        repository = repository,
        toDomain = LightLevelReadingEntity::toDomain,
        toEntity = LightLevelReading::toEntity
    ) {

    private val log = LogFactory.getLog(javaClass)

    @Transactional
    override fun add(
        userId: String,
        readingModel: LightLevelReading,
    ): LightLevelReading {
        checkUserHasDevice(userId = userId, deviceId = readingModel.deviceId)
        return repository.save(readingModel.toEntity()).toDomain()
    }

    override fun createDomainReading(deviceId: UUID, reading: Double, timestamp: Calendar): LightLevelReading {
        return LightLevelReading(deviceId = deviceId, reading = reading.roundToInt(), timestamp = timestamp)
    }
}
