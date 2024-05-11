package ru.filimonov.hpa.data.services

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated
import ru.filimonov.hpa.data.repositories.MqttEventRepository
import ru.filimonov.hpa.domain.model.Configuration
import ru.filimonov.hpa.domain.services.UpdateConfigurationEventService
import java.util.*

@Service
@Validated
class UpdateConfigurationEventServiceImpl(
    private val mqttEventRepository: MqttEventRepository,
    private val jsonObjectMapper: ObjectMapper,
) : UpdateConfigurationEventService {
    companion object {
        private const val TOPIC = "config"
    }

    override fun sendUpdateConfigEvent(deviceId: UUID, configuration: Configuration) {
        mqttEventRepository.sendEvent(
            eventName = TOPIC, deviceId = deviceId, payload = jsonObjectMapper.writeValueAsString(configuration)
        )
    }
}
