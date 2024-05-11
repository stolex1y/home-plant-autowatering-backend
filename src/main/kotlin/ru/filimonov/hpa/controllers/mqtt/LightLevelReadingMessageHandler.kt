package ru.filimonov.hpa.controllers.mqtt

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.filimonov.hpa.domain.model.Device
import ru.filimonov.hpa.domain.model.readings.LightLevelReading
import ru.filimonov.hpa.domain.services.readings.LightLevelReadingsService
import java.util.*
import kotlin.math.roundToInt

@Service
class LightLevelReadingMessageHandler(
    private val readingsService: LightLevelReadingsService
) : SensorReadingMessageHandler() {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    override val topic: String = "light-level"

    override fun saveReading(device: Device, reading: Double) {
        readingsService.add(
            userId = device.userId,
            LightLevelReading(
                reading = reading.roundToInt(),
                deviceId = device.uuid,
                timestamp = Calendar.getInstance(),
            )
        )
    }
}
