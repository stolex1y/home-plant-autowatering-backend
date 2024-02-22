package ru.filimonov.hpa.domain.service

import org.springframework.data.domain.Range

import ru.filimonov.hpa.domain.model.SensorReading

import java.util.*

interface SoilMoistureReadingService {
    fun getLastValue(deviceId: UUID): SensorReading<Float>?
    fun getReadingsForPeriodByHour(deviceId: UUID, period: Range<Calendar>): List<SensorReading<Float>>
    fun getReadingsForPeriodByDay(deviceId: UUID, period: Range<Calendar>): List<SensorReading<Float>>
    fun deleteReadingsForPeriod(deviceId: UUID, period: Range<Calendar>): Long
    fun addReading(deviceId: UUID, reading: Float): SensorReading<Float>
}
