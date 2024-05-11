package ru.filimonov.hpa.domain.services

import jakarta.validation.Valid
import ru.filimonov.hpa.domain.model.Configuration
import java.util.*

interface UpdateConfigurationEventService {
    fun sendUpdateConfigEvent(deviceId: UUID, @Valid configuration: Configuration)
}
