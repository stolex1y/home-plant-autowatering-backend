package ru.filimonov.hpa.controllers.rest.readings

import org.apache.commons.logging.LogFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import ru.filimonov.hpa.controllers.rest.readings.model.LightLevelReadingRequest
import ru.filimonov.hpa.domain.model.User
import ru.filimonov.hpa.domain.model.readings.LightLevelReading
import ru.filimonov.hpa.domain.services.readings.LightLevelReadingsService
import java.util.*

@RestController
@RequestMapping("/devices/{deviceId}/readings/light-level")
class LightLevelReadingsController(
    service: LightLevelReadingsService,
) : SensorReadingsController<Int, LightLevelReading, LightLevelReadingsService>(service) {
    private val log = LogFactory.getLog(javaClass)

    @PostMapping("/add")
    fun getLastReading(
        @AuthenticationPrincipal user: User,
        @PathVariable deviceId: UUID,
        @RequestBody reading: LightLevelReadingRequest,
    ): ResponseEntity<Unit> {
        service.add(user.id, reading.toDomain(deviceId = deviceId))
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }
}
