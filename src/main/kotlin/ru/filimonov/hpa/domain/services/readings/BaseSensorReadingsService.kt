package ru.filimonov.hpa.domain.services.readings

import jakarta.validation.Valid
import org.springframework.data.domain.Range
import ru.filimonov.hpa.common.constraints.ValidCalendarRange
import ru.filimonov.hpa.domain.model.PeriodUnit
import java.util.*

interface BaseSensorReadingsService<Reading, ReadingModel> {
    fun getLast(userId: String, deviceId: UUID): ReadingModel?

    fun getForPeriodByTimeUnit(
        userId: String,
        deviceId: UUID,
        period: Range<Calendar>,
        periodUnit: PeriodUnit,
    ): List<ReadingModel>

    fun deleteForPeriod(userId: String, deviceId: UUID, @ValidCalendarRange period: Range<Calendar>): Long

    fun add(
        userId: String,
        @Valid readingModel: ReadingModel,
    ): ReadingModel
}
