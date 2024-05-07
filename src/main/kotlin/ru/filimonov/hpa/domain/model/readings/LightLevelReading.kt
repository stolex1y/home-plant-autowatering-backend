package ru.filimonov.hpa.domain.model.readings

import jakarta.validation.constraints.PastOrPresent
import ru.filimonov.hpa.core.constraints.InRange
import java.util.*

data class LightLevelReading(
    @InRange(min = MIN, max = MAX)
    override val reading: Int,

    val deviceId: UUID,

    @PastOrPresent
    override val timestamp: Calendar,
) : SensorReading<Int>(reading = reading, timestamp = timestamp) {
    companion object {
        const val MIN = 0.0
        const val MAX = 130_000.0
    }
}
