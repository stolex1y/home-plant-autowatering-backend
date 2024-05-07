package ru.filimonov.hpa.mqtt

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.filimonov.hpa.domain.model.Device
import ru.filimonov.hpa.domain.model.readings.SoilMoistureReading
import ru.filimonov.hpa.domain.service.readings.SoilMoistureReadingsService
import java.util.*

@Service
class SoilMoistureReadingMessageHandler(
    private val readingsService: SoilMoistureReadingsService
) : SensorReadingMessageHandler() {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    override val topic: String = "soil-moisture"

    override fun saveReading(device: Device, reading: Double) {
        readingsService.add(
            userId = device.userId,
            SoilMoistureReading(
                reading = reading.toFloat(),
                deviceId = device.uuid,
                timestamp = Calendar.getInstance(),
            )
        )
    }
}
