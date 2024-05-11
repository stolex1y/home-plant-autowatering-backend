package ru.filimonov.hpa.controllers.mqtt

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.filimonov.hpa.domain.model.Device
import ru.filimonov.hpa.domain.model.readings.AirTempReading
import ru.filimonov.hpa.domain.services.readings.AirTempReadingsService
import java.util.*

@Service
class AirTempReadingMessageHandler(
    private val readingsService: AirTempReadingsService
) : SensorReadingMessageHandler() {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    override val topic: String = "air-temp"

    override fun saveReading(device: Device, reading: Double) {
        readingsService.add(
            userId = device.userId,
            AirTempReading(
                reading = reading.toFloat(),
                deviceId = device.uuid,
                timestamp = Calendar.getInstance(),
            )
        )
    }
}
