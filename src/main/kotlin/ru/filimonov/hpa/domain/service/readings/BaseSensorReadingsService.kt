package ru.filimonov.hpa.domain.service.readings

import jakarta.validation.Valid
import org.springframework.data.domain.Range
import ru.filimonov.hpa.core.constraints.ValidCalendarRange
import ru.filimonov.hpa.domain.model.PeriodUnit
import ru.filimonov.hpa.domain.model.readings.SensorReading
import java.util.*

interface BaseSensorReadingsService<Reading, ReadingModel> {
    fun getLast(userId: String, deviceId: UUID): SensorReading<Reading>?

    fun getForPeriodByTimeUnit(
        userId: String,
        deviceId: UUID,
        period: Range<Calendar>,
        periodUnit: PeriodUnit,
    ): List<SensorReading<Reading>>

    fun deleteForPeriod(userId: String, deviceId: UUID, @ValidCalendarRange period: Range<Calendar>): Long

    fun add(
        userId: String,
        @Valid readingModel: ReadingModel,
    ): ReadingModel
}
