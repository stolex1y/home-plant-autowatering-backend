package ru.filimonov.hpa.domain.service.sensors

import org.springframework.data.domain.Range
import ru.filimonov.hpa.core.constraints.InRange
import ru.filimonov.hpa.core.constraints.ValidCalendarRange
import ru.filimonov.hpa.domain.model.PeriodUnit
import ru.filimonov.hpa.domain.model.SensorReading
import java.util.*

interface SoilMoistureReadingService {
    companion object {
        const val MIN_VALUE = 0.0
        const val MAX_VALUE = 100.0
    }

    fun getLastReading(userId: String, deviceId: UUID): SensorReading<Float>?

    fun getReadingsForPeriodByTimeUnit(
        userId: String,
        deviceId: UUID,
        period: Range<Calendar>,
        periodUnit: PeriodUnit,
    ): List<SensorReading<Float>>

    fun deleteReadingsForPeriod(userId: String, deviceId: UUID, @ValidCalendarRange period: Range<Calendar>): Long

    fun addReading(
        userId: String,
        deviceId: UUID,
        @InRange(min = MIN_VALUE, max = MAX_VALUE) reading: Float
    ): SensorReading<Float>?
}
