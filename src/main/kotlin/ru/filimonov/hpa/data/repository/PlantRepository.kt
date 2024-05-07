package ru.filimonov.hpa.data.repository

import org.springframework.data.repository.CrudRepository
import ru.filimonov.hpa.data.model.PlantEntity
import java.util.*

interface PlantRepository : CrudRepository<PlantEntity, UUID> {
    fun findByUuid(plantId: UUID): PlantEntity?
    fun findAllByUuidInOrderByUuid(plantIds: List<UUID>): List<PlantEntity>
}
