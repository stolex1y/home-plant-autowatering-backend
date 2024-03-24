package ru.filimonov.hpa.data.service

import org.springframework.stereotype.Service
import ru.filimonov.hpa.data.repository.MqttEventRepository
import ru.filimonov.hpa.domain.model.Configuration
import ru.filimonov.hpa.domain.model.Device
import ru.filimonov.hpa.domain.service.UpdateConfigurationEventService

@Service
class UpdateConfigurationEventServiceImpl(
    private val mqttEventRepository: MqttEventRepository
) : UpdateConfigurationEventService {
    companion object {
        private const val TOPIC = "update-config"
    }

    override fun sendUpdateConfigEvent(device: Device, configuration: Configuration) {
        //TODO(валидация)
        mqttEventRepository.sendEvent(TOPIC, device.mac, configuration.toString())
    }
}
