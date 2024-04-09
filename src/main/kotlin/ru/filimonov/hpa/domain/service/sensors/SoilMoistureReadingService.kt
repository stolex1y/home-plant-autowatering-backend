package ru.filimonov.hpa.domain.service.sensors

import org.springframework.data.domain.Range
import ru.filimonov.hpa.core.constraints.ValidCalendarRange
import ru.filimonov.hpa.domain.model.PeriodUnit
import ru.filimonov.hpa.domain.model.SensorReading
import ru.filimonov.hpa.domain.model.SoilMoistureReading
import java.util.*

interface SoilMoistureReadingService {
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
        soilMoistureReading: SoilMoistureReading,
    ): SoilMoistureReading
}
