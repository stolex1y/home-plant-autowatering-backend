package ru.filimonov.hpa.domain.service.readings

import ru.filimonov.hpa.domain.model.readings.BatteryChargeReading

interface BatteryChargeReadingsService : BaseSensorReadingsService<Float, BatteryChargeReading>
