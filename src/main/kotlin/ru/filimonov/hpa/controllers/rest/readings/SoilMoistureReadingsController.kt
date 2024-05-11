package ru.filimonov.hpa.controllers.rest.readings

import org.apache.commons.logging.LogFactory
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.filimonov.hpa.domain.model.readings.SoilMoistureReading
import ru.filimonov.hpa.domain.services.readings.SoilMoistureReadingsService

@RestController
@RequestMapping("/devices/{deviceId}/readings/soil-moisture")
class SoilMoistureReadingsController(
    service: SoilMoistureReadingsService,
) : SensorReadingsController<Float, SoilMoistureReading, SoilMoistureReadingsService>(service) {
    private val log = LogFactory.getLog(javaClass)
}
