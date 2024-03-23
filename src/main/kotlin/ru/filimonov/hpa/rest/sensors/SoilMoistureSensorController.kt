package ru.filimonov.hpa.rest.sensors

import org.apache.commons.logging.LogFactory
import org.springframework.data.domain.Range
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import ru.filimonov.hpa.core.toDateTimeString
import ru.filimonov.hpa.domain.model.PeriodUnit
import ru.filimonov.hpa.domain.model.User
import ru.filimonov.hpa.domain.service.sensors.SoilMoistureReadingService
import ru.filimonov.hpa.rest.common.model.SensorReadingResponse
import ru.filimonov.hpa.rest.common.model.toResponse
import java.util.*

@RestController
@RequestMapping("/devices/{deviceId}/readings/soil-moisture")
class SoilMoistureSensorController(
    private val soilMoistureReadingService: SoilMoistureReadingService,
) {
    private val log = LogFactory.getLog(javaClass)

    @GetMapping("/last")
    fun getLastReading(
        @AuthenticationPrincipal user: User,
        @PathVariable deviceId: UUID,
    ): ResponseEntity<SensorReadingResponse<Float>> {
        val result = soilMoistureReadingService.getLastReading(user.id, deviceId)?.toResponse()
        return if (result == null) {
            ResponseEntity.notFound().build()
        } else ResponseEntity.ok(
            result
        )
    }

    @GetMapping("/period")
    fun getReadingsForPeriod(
        @RequestParam(name = "unit") periodUnit: PeriodUnit,
        @RequestParam(name = "from") fromTimestamp: Calendar,
        @RequestParam(name = "to") toTimestamp: Calendar,
        @PathVariable deviceId: UUID,
        @AuthenticationPrincipal user: User,
    ): ResponseEntity<List<SensorReadingResponse<Float>>> {
        log.debug("get readings for period from ${fromTimestamp.toDateTimeString()} to ${toTimestamp.toDateTimeString()}")
        return ResponseEntity.ok(
            soilMoistureReadingService.getReadingsForPeriodByTimeUnit(
                user.id,
                deviceId,
                Range.closed(fromTimestamp, toTimestamp),
                periodUnit,
            ).map { it.toResponse() }
        )
    }
}
