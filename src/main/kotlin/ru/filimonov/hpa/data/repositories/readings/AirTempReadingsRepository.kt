package ru.filimonov.hpa.data.repositories.readings

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.filimonov.hpa.data.model.readings.AirTempReadingEntity
import java.util.*

@Repository
interface AirTempReadingsRepository : BaseSensorReadingsRepository<AirTempReadingEntity>,
    CrudRepository<AirTempReadingEntity, UUID>
