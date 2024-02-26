package ru.filimonov.hpa.domain.model

import kotlin.time.Duration

data class Configuration(
        val minMoistureReading: Float,
        val maxMoistureReading: Float,
        val readingPeriod: Duration
) {
    override fun toString(): String {
        return "minMoisture=$minMoistureReading;" +
                "maxMoisture=$maxMoistureReading;" +
                "readingPeriodMs=${readingPeriod.inWholeMilliseconds}"
    }
}
