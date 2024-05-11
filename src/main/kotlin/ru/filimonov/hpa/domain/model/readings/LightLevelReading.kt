package ru.filimonov.hpa.domain.model.readings

import jakarta.validation.constraints.PastOrPresent
import ru.filimonov.hpa.common.constraints.InRange
import java.util.*

data class LightLevelReading(
    @field:InRange(min = MIN, max = MAX)
    override val reading: Int,

    val deviceId: UUID,

    @field:PastOrPresent
    override val timestamp: Calendar,
) : SensorReading<Int> {
    companion object {
        const val MIN = 0.0
        const val MAX = 130_000.0
    }
}
