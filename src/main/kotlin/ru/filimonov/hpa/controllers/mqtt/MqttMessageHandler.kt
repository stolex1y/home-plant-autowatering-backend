package ru.filimonov.hpa.controllers.mqtt

import ru.filimonov.hpa.domain.model.Device

interface MqttMessageHandler {
    val topic: String

    fun handle(device: Device, payload: String)
}
