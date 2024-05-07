package ru.filimonov.hpa.rest.readings

import org.apache.commons.logging.LogFactory
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.filimonov.hpa.domain.model.readings.LightLevelReading
import ru.filimonov.hpa.domain.service.readings.LightLevelReadingsService

@RestController
@RequestMapping("/devices/{deviceId}/readings/light-level")
class LightLevelReadingsController(
    service: LightLevelReadingsService,
) : SensorReadingsController<Int, LightLevelReading, LightLevelReadingsService>(service) {
    private val log = LogFactory.getLog(javaClass)
}
