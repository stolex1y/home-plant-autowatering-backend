package ru.filimonov.hpa.mqtt

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import ru.filimonov.hpa.domain.model.Device

abstract class SensorReadingMessageHandler : MqttMessageHandler {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    override fun handle(device: Device, payload: String) {
        logger.debug("Handle reading ($payload) from topic $topic")
        val sensorReading = payload.toDoubleOrNull()
        if (sensorReading == null) {
            logger.error("Invalid reading value. Expected float, but was: $payload")
            return
        }
        saveReading(device = device, reading = sensorReading)
    }

    protected abstract fun saveReading(device: Device, reading: Double)
}
