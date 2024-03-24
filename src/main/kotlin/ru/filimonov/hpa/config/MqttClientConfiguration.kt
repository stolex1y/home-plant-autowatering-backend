package ru.filimonov.hpa.config

import jakarta.validation.constraints.NotBlank
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.bind.ConstructorBinding
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.channel.DirectChannel
import org.springframework.integration.dsl.IntegrationFlow
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory
import org.springframework.integration.mqtt.core.MqttPahoClientFactory
import org.springframework.integration.mqtt.event.MqttIntegrationEvent
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter
import org.springframework.integration.mqtt.support.MqttHeaders
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.stereotype.Component
import ru.filimonov.hpa.data.repository.DeviceRepository
import ru.filimonov.hpa.data.repository.MqttOutboundRepository
import ru.filimonov.hpa.domain.mqtt.MqttMessageHandler
import ru.filimonov.hpa.domain.service.DeviceService
import java.util.*


private const val MQTT_CONN_PROPS_PREFIX = "app.mqtt"
private const val DEVICE_SENSOR_BASE_TOPIC = "autowatering"
private const val ANY_DEVICE_ANY_SENSOR_TOPIC = "$DEVICE_SENSOR_BASE_TOPIC/+/+"

@Configuration
@ConfigurationPropertiesScan
class MqttClientConfiguration {

    @Component
    class MqttEventListener : ApplicationListener<MqttIntegrationEvent> {
        private val logger: Logger = LoggerFactory.getLogger(javaClass)

        override fun onApplicationEvent(event: MqttIntegrationEvent) {
            logger.debug(event.toString())
        }
    }

    @ConfigurationProperties(prefix = MQTT_CONN_PROPS_PREFIX)
    class ConnectionProperties @ConstructorBinding constructor(
        @NotBlank
        val url: String,
        @NotBlank
        val username: String,
        @NotBlank
        val password: String
    )

    class Topic(fullName: String) {
        companion object {
            private val TOPIC_REGEX = "$DEVICE_SENSOR_BASE_TOPIC/(.+)/(.+)".toRegex()
        }

        val deviceId: UUID
        val sensorType: String

        init {
            val matchResult = TOPIC_REGEX.find(fullName)
                ?: throw IllegalArgumentException("Topic ($fullName) isn't valid.")
            deviceId = matchResult.groupValues[1].run(UUID::fromString)
            sensorType = matchResult.groupValues[2]
        }
    }

    companion object {
        const val MQTT_OUTPUT_CHANNEL = "mqtt-output"

        private fun Message<*>.isRetained(): Boolean = headers[MqttHeaders.RECEIVED_RETAINED] == true

        private fun Message<*>.topic(): String = headers[MqttHeaders.RECEIVED_TOPIC]?.toString() ?: ""
    }

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @Bean
    fun mqttClientFactory(connectionProperties: ConnectionProperties): MqttPahoClientFactory {
        return DefaultMqttPahoClientFactory().apply {
            connectionOptions.apply {
                password = connectionProperties.password.toCharArray()
                userName = connectionProperties.username
                serverURIs = arrayOf(connectionProperties.url)
                isAutomaticReconnect = true
                isCleanSession = false
            }
        }
    }

    @Bean
    fun mqttMessageProducer(clientFactory: MqttPahoClientFactory): MqttPahoMessageDrivenChannelAdapter =
        MqttPahoMessageDrivenChannelAdapter(UUID.randomUUID().toString(), clientFactory).apply {
            setQos(2)
            addTopic(ANY_DEVICE_ANY_SENSOR_TOPIC)
            setConverter(DefaultPahoMessageConverter(2, true))
        }

    @Bean
    fun mqttMessageConsumer(clientFactory: MqttPahoClientFactory): MqttPahoMessageHandler {
        return MqttPahoMessageHandler(UUID.randomUUID().toString(), clientFactory).apply {
            setDefaultQos(2)
            setDefaultRetained(true)
        }
    }

    @Bean(name = [MQTT_OUTPUT_CHANNEL])
    fun mqttOutputChannel(): MessageChannel {
        return DirectChannel()
    }

    @Bean
    fun mqttInbound(
        mqttAdapter: MqttPahoMessageDrivenChannelAdapter,
        mqttHandlers: List<MqttMessageHandler>,
        deviceRepository: DeviceRepository,
        mqttOutboundRepository: MqttOutboundRepository
    ): IntegrationFlow {
        val topicHandlers = mutableMapOf<String, MutableList<MqttMessageHandler>>()
        mqttHandlers.forEach {
            topicHandlers.getOrPut(it.topic) { mutableListOf() }
                .add(it)
        }
        return IntegrationFlow.from(mqttAdapter)
            .filter<String> { payload -> payload.isNotEmpty() }.handle { message ->
                val topicFullName = message.topic()
                runCatching<Boolean> {
                    val topic = Topic(topicFullName)
                    if (!deviceRepository.existsById(topic.deviceId)) {
                        throw IllegalArgumentException("Couldn't find device by id: ${topic.deviceId}")
                    }
                    val handlers: List<MqttMessageHandler>? = topicHandlers[topic.sensorType]
                    if (handlers.isNullOrEmpty()) {
                        return@runCatching false
                    }
                    handlers.forEach {
                        it.handle(
                            topic.deviceId, message.payload.toString()
                        )
                    }
                    return@runCatching true
                }.onSuccess { handled ->
                    if (handled) {
                        mqttOutboundRepository.sendMessage(topicFullName, "", 2, true)
                    }
                }.onFailure {
                    logger.error("Invalid mqtt message by topic ($topicFullName): ${it.message}.", it)
                    mqttOutboundRepository.sendMessage(topicFullName, "", 2, true)
                }
            }.get()
    }

    @Bean
    fun mqttOutbound(
        @Qualifier(MQTT_OUTPUT_CHANNEL) mqttOutputChannel: MessageChannel,
        mqttPahoMessageHandler: MqttPahoMessageHandler
    ): IntegrationFlow {
        return IntegrationFlow.from(mqttOutputChannel).handle(mqttPahoMessageHandler).get()
    }

}
