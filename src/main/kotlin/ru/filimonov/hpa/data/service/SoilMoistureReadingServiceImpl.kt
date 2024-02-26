package ru.filimonov.hpa.data.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Range
import org.springframework.stereotype.Service
import ru.filimonov.hpa.core.bounds
import ru.filimonov.hpa.core.floorToDay
import ru.filimonov.hpa.core.floorToHour
import ru.filimonov.hpa.core.toTimestamp
import ru.filimonov.hpa.data.model.SoilMoistureReadingEntity
import ru.filimonov.hpa.data.repository.SoilMoistureReadingRepository
import ru.filimonov.hpa.domain.model.SensorReading
import ru.filimonov.hpa.domain.service.SoilMoistureReadingService
import java.util.*

@Service
class SoilMoistureReadingServiceImpl @Autowired constructor(
    private val repository: SoilMoistureReadingRepository,
) : SoilMoistureReadingService {
    override fun getLastValue(deviceId: UUID): SensorReading<Float>? {
        val entity = repository.findTopByDeviceIdOrderByTimestampDesc(deviceId) ?: return null
        return entity.toDomain()
    }

    override fun getReadingsForPeriodByHour(deviceId: UUID, period: Range<Calendar>): List<SensorReading<Float>> {
        return getReadingsForPeriodRounded(deviceId, period) { floorToHour() }
    }

    override fun getReadingsForPeriodByDay(deviceId: UUID, period: Range<Calendar>): List<SensorReading<Float>> {
        return getReadingsForPeriodRounded(deviceId, period) { floorToDay() }
    }

    override fun deleteReadingsForPeriod(deviceId: UUID, period: Range<Calendar>): Long {
        val periodBounds = period.bounds()
        return repository.deleteAllByDeviceIdAndTimestampBetween(
            deviceId,
            periodBounds.first.toTimestamp().apply { time-- },
            periodBounds.second.toTimestamp().apply { time++ }
        )
    }

    override fun addReading(deviceId: UUID, reading: Float): SensorReading<Float> {
        return repository.save(
            SoilMoistureReadingEntity(
                reading = reading,
                deviceId = deviceId
            )
        ).toDomain()
    }

    private fun getReadingsForPeriodRounded(
        deviceId: UUID,
        period: Range<Calendar>,
        round: Calendar.() -> Unit
    ): List<SensorReading<Float>> {
        val periodBounds = period.bounds()
        return repository.findAllByDeviceIdAndTimestampBetweenOrderByTimestampDesc(
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
