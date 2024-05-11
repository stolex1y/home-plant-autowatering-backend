package ru.filimonov.hpa.data.repositories

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.messaging.MessageChannel
import org.springframework.stereotype.Repository
import ru.filimonov.hpa.configs.MqttClientConfiguration
import java.util.*

@Repository
class MqttEventRepository(
    @Qualifier(MqttClientConfiguration.MQTT_OUTPUT_CHANNEL) mqttMessageChannel: MessageChannel
) {
    private val mqttOutboundRepository = MqttOutboundRepository(mqttMessageChannel)

    companion object {
        private const val EVENT_BASE_TOPIC = "autowatering"
    }

    fun sendEvent(eventName: String, deviceId: UUID, payload: String) {
        mqttOutboundRepository.sendMessage(
            topic = "$EVENT_BASE_TOPIC/$deviceId/$eventName",
            payload = payload,
            qos = 2,
            retained = true
        )
    }
}
