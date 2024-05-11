package ru.filimonov.hpa.data.repositories.readings

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.filimonov.hpa.data.model.readings.AirHumidityReadingEntity
import java.util.*

@Repository
interface AirHumidityReadingsRepository : BaseSensorReadingsRepository<AirHumidityReadingEntity>,
    CrudRepository<AirHumidityReadingEntity, UUID>
