package ru.filimonov.hpa.data.repository.readings

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.filimonov.hpa.data.model.readings.WaterReserveReadingEntity
import java.util.*

@Repository
interface WaterReserveReadingsRepository : BaseSensorReadingsRepository<WaterReserveReadingEntity>,
    CrudRepository<WaterReserveReadingEntity, UUID>
