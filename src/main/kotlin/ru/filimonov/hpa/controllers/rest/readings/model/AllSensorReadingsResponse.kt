package ru.filimonov.hpa.controllers.rest.readings.model

data class AllSensorReadingsResponse(
    val soilMoisture: SensorReadingResponse<Float>?,
    val batteryCharge: SensorReadingResponse<Float>?,
    val airTemp: SensorReadingResponse<Float>?,
    val airHumidity: SensorReadingResponse<Float>?,
    val lightLevel: SensorReadingResponse<Int>?,
)
