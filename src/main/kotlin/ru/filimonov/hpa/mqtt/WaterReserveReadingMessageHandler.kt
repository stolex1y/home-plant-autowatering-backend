package ru.filimonov.hpa.mqtt

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.filimonov.hpa.domain.model.Device
import ru.filimonov.hpa.domain.model.readings.WaterReserveReading
import ru.filimonov.hpa.domain.service.readings.WaterReserveReadingsService
import java.util.*

@Service
class WaterReserveReadingMessageHandler(
    private val readingsService: WaterReserveReadingsService
) : SensorReadingMessageHandler() {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    override val topic: String = "water-reserve"

    override fun saveReading(device: Device, reading: Double) {
        readingsService.add(
            userId = device.userId,
            WaterReserveReading(
                reading = reading.toFloat(),
                deviceId = device.uuid,
                timestamp = Calendar.getInstance(),
            )
        )
    }
}
