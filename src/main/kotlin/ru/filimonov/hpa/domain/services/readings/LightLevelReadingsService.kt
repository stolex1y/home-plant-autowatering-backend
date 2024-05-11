package ru.filimonov.hpa.domain.services.readings

import ru.filimonov.hpa.domain.model.readings.LightLevelReading

interface LightLevelReadingsService : BaseSensorReadingsService<Int, LightLevelReading>
