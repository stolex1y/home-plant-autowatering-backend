package ru.filimonov.hpa.domain.model.readings

import jakarta.validation.constraints.PastOrPresent
import ru.filimonov.hpa.common.constraints.InRange
import java.util.*

data class AirTempReading(
    @field:InRange(min = MIN, max = MAX)
    override val reading: Float,

    val deviceId: UUID,

    @field:PastOrPresent
    override val timestamp: Calendar,
) : SensorReading<Float> {
    companion object {
        const val MIN = -10.0
        const val MAX = 40.0
    }
}
