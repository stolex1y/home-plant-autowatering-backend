package ru.filimonov.hpa.data.repository.readings

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.filimonov.hpa.data.model.readings.SoilMoistureReadingEntity
import java.util.*

@Repository
interface SoilMoistureReadingsRepository : BaseSensorReadingsRepository<SoilMoistureReadingEntity>,
    CrudRepository<SoilMoistureReadingEntity, UUID>
