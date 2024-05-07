package ru.filimonov.hpa.rest.readings

import org.apache.commons.logging.LogFactory
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.filimonov.hpa.domain.model.User
import ru.filimonov.hpa.domain.service.readings.*
import ru.filimonov.hpa.rest.readings.model.AllSensorReadingsResponse
import ru.filimonov.hpa.rest.readings.model.toResponse
import java.util.*

@RestController
@RequestMapping("/devices/{deviceId}/readings")
class AllSensorReadingsController(
    private val soilMoistureReadingsService: SoilMoistureReadingsService,
    private val batteryChargeReadingsService: BatteryChargeReadingsService,
    private val airHumidityReadingsService: AirHumidityReadingsService,
    private val airTempReadingsService: AirTempReadingsService,
    private val lightLevelReadingsService: LightLevelReadingsService,
) {
    private val log = LogFactory.getLog(javaClass)

    @GetMapping("/last")
    fun getLastReadings(
        @AuthenticationPrincipal user: User,
        @PathVariable deviceId: UUID,
    ): ResponseEntity<AllSensorReadingsResponse> {
        val soilMoisture = soilMoistureReadingsService.getLast(user.id, deviceId)?.toResponse()
        val batteryCharge = batteryChargeReadingsService.getLast(user.id, deviceId)?.toResponse()
        val lightLevel = lightLevelReadingsService.getLast(user.id, deviceId)?.toResponse()
        val airTemp = airTempReadingsService.getLast(user.id, deviceId)?.toResponse()
        val airHumidity = airHumidityReadingsService.getLast(user.id, deviceId)?.toResponse()
        return ResponseEntity.ok(
            AllSensorReadingsResponse(
                soilMoisture = soilMoisture,
                batteryCharge = batteryCharge,
                airTemp = airTemp,
                airHumidity = airHumidity,
                lightLevel = lightLevel,
            )
        )
    }
}
