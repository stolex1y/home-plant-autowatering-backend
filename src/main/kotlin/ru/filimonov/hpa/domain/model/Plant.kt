package ru.filimonov.hpa.domain.model

import jakarta.validation.constraints.NotBlank
import ru.filimonov.hpa.core.constraints.InRange
import ru.filimonov.hpa.domain.validation.domainRequire
import java.util.*

data class Plant(
    @NotBlank
    val name: String,
    @InRange(min = -30.0, max = +30.0)
    val airTempMin: Float?,
    @InRange(min = -30.0, max = +30.0)
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
    val lightLuxMax: Int?,
    @InRange(min = 0.0, max = 130_000.0)
    val lightLuxMin: Int?,
    val photo: UUID?,
    val createdDate: Calendar = Calendar.getInstance(),
    val uuid: UUID = UUID.randomUUID(),
) {
    init {
        checkMaxValueGrOrEqMin(max = airTempMax, maxName = "airTempMax", min = airTempMin, minName = "airTempMin")
        checkMaxValueGrOrEqMin(
            max = airHumidityMax,
            maxName = "airHumidityMax",
            min = airHumidityMin,
            minName = "airHumidityMin"
        )
        checkMaxValueGrOrEqMin(
            max = soilMoistureMax,
            maxName = "soilMoistureMax",
            min = soilMoistureMin,
            minName = "soilMoistureMin"
        )
        checkMaxValueGrOrEqMin(max = lightLuxMax, maxName = "lightLuxMax", min = lightLuxMin, minName = "lightLuxMin")
    }

    private fun checkMaxValueGrOrEqMin(max: Float?, maxName: String, min: Float?, minName: String) {
        if (max != null && min != null) {
            domainRequire(max >= min, maxName) { "$maxName must be >= $minName" }
        }
    }

    private fun checkMaxValueGrOrEqMin(max: Int?, maxName: String, min: Int?, minName: String) {
        checkMaxValueGrOrEqMin(max = max?.toFloat(), maxName = maxName, min = min?.toFloat(), minName = minName)
    }
}
