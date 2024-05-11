package ru.filimonov.hpa.controllers.rest.readings

import org.apache.commons.logging.LogFactory
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.filimonov.hpa.domain.model.readings.BatteryChargeReading
import ru.filimonov.hpa.domain.services.readings.BatteryChargeReadingsService

@RestController
@RequestMapping("/devices/{deviceId}/readings/battery-charge")
class BatteryChargeReadingsController(
    service: BatteryChargeReadingsService,
) : SensorReadingsController<Float, BatteryChargeReading, BatteryChargeReadingsService>(service) {
    private val log = LogFactory.getLog(javaClass)
}
