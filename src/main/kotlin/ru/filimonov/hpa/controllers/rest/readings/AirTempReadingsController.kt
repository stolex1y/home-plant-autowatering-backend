package ru.filimonov.hpa.controllers.rest.readings

import org.apache.commons.logging.LogFactory
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.filimonov.hpa.domain.model.readings.AirTempReading
import ru.filimonov.hpa.domain.services.readings.AirTempReadingsService

@RestController
@RequestMapping("/devices/{deviceId}/readings/air-temp")
class AirTempReadingsController(
    service: AirTempReadingsService,
) : SensorReadingsController<Float, AirTempReading, AirTempReadingsService>(service) {
    private val log = LogFactory.getLog(javaClass)
}
