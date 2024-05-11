package ru.filimonov.hpa.controllers.rest.readings.model

import ru.filimonov.hpa.domain.model.readings.LightLevelReading
import java.util.*

data class LightLevelReadingRequest(
    val reading: Int,
) {
    fun toDomain(deviceId: UUID, timestamp: Calendar = Calendar.getInstance()) = LightLevelReading(
        reading = reading, deviceId = deviceId, timestamp = timestamp.apply { timeInMillis += 1000 },
    )
}
