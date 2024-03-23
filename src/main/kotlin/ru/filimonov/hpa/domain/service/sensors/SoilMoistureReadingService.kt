package ru.filimonov.hpa.domain.mqtt

import org.springframework.data.domain.Range
import ru.filimonov.hpa.domain.model.PeriodUnit
import ru.filimonov.hpa.domain.model.SensorReading
import java.util.*

interface SoilMoistureReadingService {
    fun getLastReading(userId: String, deviceId: UUID): SensorReading<Float>?
    fun getReadingsForPeriodByTimeUnit(
        userId: String,
        deviceId: UUID,
        period: Range<Calendar>,
        periodUnit: PeriodUnit
    ): List<SensorReading<Float>>

    fun deleteReadingsForPeriod(userId: String, deviceId: UUID, period: Range<Calendar>): Long
    fun addReading(userId: String, deviceId: UUID, reading: Float): SensorReading<Float>?
}
