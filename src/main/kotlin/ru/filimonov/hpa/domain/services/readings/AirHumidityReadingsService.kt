package ru.filimonov.hpa.domain.services.readings

import ru.filimonov.hpa.domain.model.readings.AirHumidityReading

interface AirHumidityReadingsService : BaseSensorReadingsService<Float, AirHumidityReading>
