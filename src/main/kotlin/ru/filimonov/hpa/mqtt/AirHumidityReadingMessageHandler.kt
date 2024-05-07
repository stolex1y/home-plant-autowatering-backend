package ru.filimonov.hpa.mqtt

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.filimonov.hpa.domain.model.Device
import ru.filimonov.hpa.domain.model.readings.AirHumidityReading
import ru.filimonov.hpa.domain.service.readings.AirHumidityReadingsService
import java.util.*

@Service
class AirHumidityReadingMessageHandler(
    private val readingsService: AirHumidityReadingsService
) : SensorReadingMessageHandler() {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    override val topic: String = "air-humidity"

    override fun saveReading(device: Device, reading: Double) {
        readingsService.add(
            userId = device.userId,
            AirHumidityReading(
                reading = reading.toFloat(),
                deviceId = device.uuid,
                timestamp = Calendar.getInstance(),
            )
        )
    }
}
