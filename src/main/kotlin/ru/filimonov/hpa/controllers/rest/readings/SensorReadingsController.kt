package ru.filimonov.hpa.controllers.rest.readings

import org.apache.commons.logging.LogFactory
import org.springframework.data.domain.Range
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import ru.filimonov.hpa.common.toDateTimeString
import ru.filimonov.hpa.controllers.rest.readings.model.SensorReadingResponse
import ru.filimonov.hpa.controllers.rest.readings.model.toResponse
import ru.filimonov.hpa.domain.model.PeriodUnit
import ru.filimonov.hpa.domain.model.User
import ru.filimonov.hpa.domain.model.readings.SensorReading
import ru.filimonov.hpa.domain.services.readings.BaseSensorReadingsService
import java.util.*

abstract class SensorReadingsController<Reading : Number, ReadingModel : SensorReading<Reading>, SensorReadingsService : BaseSensorReadingsService<Reading, ReadingModel>>(
    protected val service: SensorReadingsService
) {
    private val log = LogFactory.getLog(javaClass)

    @GetMapping("/last")
    fun getLastReading(
        @AuthenticationPrincipal user: User,
        @PathVariable deviceId: UUID,
    ): ResponseEntity<SensorReadingResponse<Reading>> {
        val result = service.getLast(user.id, deviceId)?.toResponse()
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
    ): ResponseEntity<List<SensorReadingResponse<Reading>>> {
        log.debug("get readings for period from ${fromTimestamp.toDateTimeString()} to ${toTimestamp.toDateTimeString()}")
        return ResponseEntity.ok(
            service.getForPeriodByTimeUnit(
                user.id,
                deviceId,
                Range.closed(fromTimestamp, toTimestamp),
                periodUnit,
            ).map { it.toResponse() }
        )
    }
}
