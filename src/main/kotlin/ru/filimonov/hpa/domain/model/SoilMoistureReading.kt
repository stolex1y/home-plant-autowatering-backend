package ru.filimonov.hpa.domain.model

import jakarta.persistence.Id
import jakarta.validation.constraints.PastOrPresent
import ru.filimonov.hpa.core.constraints.InRange
import java.util.*

data class SoilMoistureReading(
    @InRange(min = MIN, max = MAX)
    override val reading: Float,
    val deviceId: UUID,
    @PastOrPresent
    override val timestamp: Calendar = Calendar.getInstance(),
    @Id
    val uuid: UUID = UUID.randomUUID()
) : SensorReading<Float>(reading = reading, timestamp = timestamp) {
    companion object {
        const val MIN = 0.0
        const val MAX = 100.0
    }
}
