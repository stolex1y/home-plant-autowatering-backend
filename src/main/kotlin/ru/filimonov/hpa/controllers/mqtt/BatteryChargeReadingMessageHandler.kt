package ru.filimonov.hpa.controllers.mqtt

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.filimonov.hpa.domain.model.Device
import ru.filimonov.hpa.domain.model.readings.BatteryChargeReading
import ru.filimonov.hpa.domain.services.readings.BatteryChargeReadingsService
import java.util.*

@Service
class BatteryChargeReadingMessageHandler(
    private val readingsService: BatteryChargeReadingsService
) : SensorReadingMessageHandler() {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    override val topic: String = "battery-charge"

    override fun saveReading(device: Device, reading: Double) {
        readingsService.add(
            userId = device.userId,
            BatteryChargeReading(
                reading = reading.toFloat(),
                deviceId = device.uuid,
                timestamp = Calendar.getInstance(),
            )
        )
    }
}
