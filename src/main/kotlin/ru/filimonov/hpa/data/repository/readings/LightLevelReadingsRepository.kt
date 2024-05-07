package ru.filimonov.hpa.data.repository.readings

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.filimonov.hpa.data.model.readings.LightLevelReadingEntity
import java.util.*

@Repository
interface LightLevelReadingsRepository : BaseSensorReadingsRepository<LightLevelReadingEntity>,
    CrudRepository<LightLevelReadingEntity, UUID>
