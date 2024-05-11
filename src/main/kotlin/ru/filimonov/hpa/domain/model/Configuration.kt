package ru.filimonov.hpa.domain.model

import ru.filimonov.hpa.common.constraints.InRange
import ru.filimonov.hpa.domain.model.readings.SoilMoistureReading.Companion.MAX
import ru.filimonov.hpa.domain.model.readings.SoilMoistureReading.Companion.MIN
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

data class Configuration(
    @field:InRange(min = MIN, max = MAX)
    val soilMoistureMin: Float,
) {
    val syncPeriod: Long = 30.minutes.inWholeMilliseconds
    val pumpDelay: Long = 1.days.inWholeMilliseconds
    val pumpTime: Long = 10.seconds.inWholeMilliseconds

    companion object {
        fun defaultConfiguration() = Configuration(
            soilMoistureMin = MIN.toFloat(),
        )
    }
}

fun Plant.toConfiguration() = Configuration(
    soilMoistureMin = soilMoistureMin ?: MIN.toFloat(),
)
