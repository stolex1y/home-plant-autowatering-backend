package ru.filimonov.hpa.domain.mqtt

import java.util.*

interface MqttMessageHandler {
    val topic: String

    fun handle(deviceId: UUID, payload: String)
}
