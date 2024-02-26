package ru.filimonov.hpa.domain.service

import ru.filimonov.hpa.domain.model.Device

interface DeviceService {
    fun getDeviceByMac(mac: String): Device?
}
