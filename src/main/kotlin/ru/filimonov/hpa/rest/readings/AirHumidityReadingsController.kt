package ru.filimonov.hpa.rest.readings

import org.apache.commons.logging.LogFactory
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.filimonov.hpa.domain.model.readings.AirHumidityReading
import ru.filimonov.hpa.domain.service.readings.AirHumidityReadingsService

@RestController
@RequestMapping("/devices/{deviceId}/readings/air-humidity")
class AirHumidityReadingsController(
    service: AirHumidityReadingsService,
) : SensorReadingsController<Float, AirHumidityReading, AirHumidityReadingsService>(service) {
    private val log = LogFactory.getLog(javaClass)
}
