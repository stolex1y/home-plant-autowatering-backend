package ru.filimonov.hpa.domain.service

import java.util.*

interface MqttMessageHandler {
    val topic: String

    fun handle(deviceId: UUID, payload: String)
}
