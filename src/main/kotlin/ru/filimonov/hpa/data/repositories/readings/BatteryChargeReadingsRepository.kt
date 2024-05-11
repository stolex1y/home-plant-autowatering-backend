package ru.filimonov.hpa.data.repositories.readings

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.filimonov.hpa.data.model.readings.BatteryChargeReadingEntity
import java.util.*

@Repository
interface BatteryChargeReadingsRepository : BaseSensorReadingsRepository<BatteryChargeReadingEntity>,
    CrudRepository<BatteryChargeReadingEntity, UUID>
