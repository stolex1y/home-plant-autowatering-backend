package ru.filimonov.hpa.domain.service

import ru.filimonov.hpa.domain.model.Configuration
import ru.filimonov.hpa.domain.model.Device

interface UpdateConfigurationEventService {
    fun sendUpdateConfigEvent(device: Device, configuration: Configuration)
}
