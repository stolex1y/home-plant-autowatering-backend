package ru.filimonov.hpa.domain.service

import jakarta.validation.Valid
import ru.filimonov.hpa.domain.model.Plant
import java.util.*

interface PlantService {
    fun getAllPlants(userId: String): List<Plant>
    fun isUserPlant(userId: String, plantId: UUID): Boolean
    fun getPlantById(userId: String, plantId: UUID): Plant
    fun getPlantsByIds(userId: String, plantIds: List<UUID>): List<Plant>
    fun addPlant(userId: String, @Valid plant: Plant): Plant
    fun updatePlant(userId: String, @Valid plant: Plant): Plant
    fun deletePlant(userId: String, plantId: UUID)
}
