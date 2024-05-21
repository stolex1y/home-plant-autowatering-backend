package ru.filimonov.hpa.domain.model

import jakarta.validation.constraints.NotBlank
import ru.filimonov.hpa.common.constraints.InRange
import ru.filimonov.hpa.domain.model.readings.AirHumidityReading
import ru.filimonov.hpa.domain.model.readings.AirTempReading
import ru.filimonov.hpa.domain.model.readings.LightLevelReading
import ru.filimonov.hpa.domain.model.readings.SoilMoistureReading
import ru.filimonov.hpa.domain.validation.requireMaxValueGrOrEqMin
import java.util.*

data class Plant(
    @NotBlank
    val name: String,

    @InRange(min = AirTempReading.MIN, max = AirTempReading.MAX)
    val airTempMin: Float?,

    @InRange(min = AirTempReading.MIN, max = AirTempReading.MAX)
    val airTempMax: Float?,

    @InRange(min = AirHumidityReading.MIN, max = AirHumidityReading.MAX)
    val airHumidityMin: Float?,

    @InRange(min = AirHumidityReading.MIN, max = AirHumidityReading.MAX)
    val airHumidityMax: Float?,

    @InRange(min = SoilMoistureReading.MIN, max = SoilMoistureReading.MAX)
    val soilMoistureMin: Float?,

    @InRange(min = LightLevelReading.MIN, max = LightLevelReading.MAX)
    val lightLevelMax: Int?,

    @InRange(min = LightLevelReading.MIN, max = LightLevelReading.MAX)
    val lightLevelMin: Int?,

    val photoId: UUID?,
    val createdDate: Calendar,
    val updatedDate: Calendar,
    val uuid: UUID,
) {
    init {
        requireMaxValueGrOrEqMin(max = airTempMax, maxName = "airTempMax", min = airTempMin, minName = "airTempMin")
        requireMaxValueGrOrEqMin(
            max = airHumidityMax,
            maxName = "airHumidityMax",
            min = airHumidityMin,
            minName = "airHumidityMin"
        )
        requireMaxValueGrOrEqMin(
            max = lightLevelMax,
            maxName = "lightLuxMax",
            min = lightLevelMin,
            minName = "lightLuxMin"
        )
    }

    val airHumidityRange: ClosedFloatingPointRange<Float>
        get() = (airHumidityMin ?: Float.MIN_VALUE)..(airHumidityMax ?: Float.MAX_VALUE)

    val airTempRange: ClosedFloatingPointRange<Float>
        get() = (airTempMin ?: Float.MIN_VALUE)..(airTempMax ?: Float.MAX_VALUE)

    val lightLevelRange: IntRange
        get() = (lightLevelMin ?: Int.MIN_VALUE)..(lightLevelMax ?: Int.MAX_VALUE)
}
