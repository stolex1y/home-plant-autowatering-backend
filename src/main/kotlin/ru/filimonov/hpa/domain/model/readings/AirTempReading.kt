package ru.filimonov.hpa.domain.model.readings

import jakarta.validation.constraints.PastOrPresent
import ru.filimonov.hpa.core.constraints.InRange
import java.util.*

data class AirTempReading(
    @InRange(min = MIN, max = MAX)
    override val reading: Float,

    val deviceId: UUID,

    @PastOrPresent
    override val timestamp: Calendar,
) : SensorReading<Float>(reading = reading, timestamp = timestamp) {
    companion object {
        const val MIN = -10.0
        const val MAX = 40.0
    }
}
