package ru.filimonov.hpa.data.repository

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.integration.mqtt.support.MqttHeaders
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.MessageHeaders
import org.springframework.messaging.support.GenericMessage
import org.springframework.stereotype.Repository
import ru.filimonov.hpa.config.MqttClientConfiguration

@Repository
class MqttOutboundRepository(
    @Qualifier(MqttClientConfiguration.MQTT_OUTPUT_CHANNEL) private val mqttMessageChannel: MessageChannel
) {
    fun sendMessage(topic: String, payload: String, qos: Int, retained: Boolean) {
        val headers = MessageHeaders(
            mapOf(
                MqttHeaders.TOPIC to topic,
                MqttHeaders.QOS to qos,
                MqttHeaders.RETAINED to retained
            )
        )
        mqttMessageChannel.send(GenericMessage<String>(payload, headers))
    }
}
