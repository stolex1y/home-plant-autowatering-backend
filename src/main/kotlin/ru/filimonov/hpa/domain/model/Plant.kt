package ru.filimonov.hpa.domain.model

import jakarta.validation.constraints.NotBlank
import ru.filimonov.hpa.core.constraints.InRange
import ru.filimonov.hpa.domain.validation.requireMaxValueGrOrEqMin
import java.util.*

data class Plant(
    @NotBlank
    val name: String,

    @InRange(min = -100.0, max = +100.0)
    val airTempMin: Float?,

    @InRange(min = -100.0, max = +100.0)
    val airTempMax: Float?,

    @InRange(min = 0.0, max = 100.0)
    val airHumidityMin: Float?,

    @InRange(min = 0.0, max = 100.0)
    val airHumidityMax: Float?,

    @InRange(min = 0.0, max = 100.0)
    val soilMoistureMin: Float?,

    @InRange(min = 0.0, max = 100.0)
    val soilMoistureMax: Float?,

    @InRange(min = 0.0, max = 130_000.0)
    val lightLevelMax: Int?,

    @InRange(min = 0.0, max = 130_000.0)
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
            max = soilMoistureMax,
            maxName = "soilMoistureMax",
            min = soilMoistureMin,
            minName = "soilMoistureMin"
        )
        requireMaxValueGrOrEqMin(
            max = lightLevelMax,
            maxName = "lightLuxMax",
            min = lightLevelMin,
            minName = "lightLuxMin"
        )
    }
}
