package ru.filimonov.hpa.data.repository

import org.springframework.data.repository.CrudRepository
import ru.filimonov.hpa.data.model.UsersPlantsMapEntity
import java.util.*

interface UsersPlantsMapRepository : CrudRepository<UsersPlantsMapEntity, UsersPlantsMapEntity> {
    fun existsByUserAndPlant(userId: String, plantId: UUID): Boolean
    fun findAllByUserAndPlantIn(userId: String, plantId: List<UUID>): List<UsersPlantsMapEntity>
    fun findAllByUser(userId: String): List<UsersPlantsMapEntity>
}
