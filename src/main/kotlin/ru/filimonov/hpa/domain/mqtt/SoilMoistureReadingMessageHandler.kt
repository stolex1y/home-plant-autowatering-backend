package ru.filimonov.hpa.domain.mqtt

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.filimonov.hpa.data.model.SoilMoistureReadingEntity
import ru.filimonov.hpa.data.repository.SoilMoistureReadingRepository
import java.util.*

@Service
class SoilMoistureReadingMessageHandler(
    private val soilMoistureReadingService: SoilMoistureReadingRepository
) : MqttMessageHandler {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    override val topic: String = "soil-moisture"

    override fun handle(deviceId: UUID, payload: String) {
        logger.debug("Handle soil moisture reading: $payload")
        val sensorReading = payload.toFloatOrNull()
        if (sensorReading == null) {
            logger.error("Invalid soil moisture value. Expected float, but was: $payload")
            return
        }
        soilMoistureReadingService.save(
            SoilMoistureReadingEntity(
                reading = sensorReading,
                deviceId = deviceId,
            )
        )
    }
}
