package ru.filimonov.hpa.data.service

import org.apache.commons.logging.LogFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.validation.annotation.Validated
import ru.filimonov.hpa.data.model.UsersPlantsMapEntity
import ru.filimonov.hpa.data.model.toEntity
import ru.filimonov.hpa.data.repository.PlantRepository
import ru.filimonov.hpa.data.repository.UsersPlantsMapRepository
import ru.filimonov.hpa.domain.model.Plant
import ru.filimonov.hpa.domain.service.PlantService
import java.util.*

@Service
@Validated
class PlantServiceImpl(
    private val usersPlantsMapRepository: UsersPlantsMapRepository,
    private val plantRepository: PlantRepository,
) : PlantService {
    private val log = LogFactory.getLog(javaClass)

    override fun isUserPlant(userId: String, plantId: UUID): Boolean {
        return usersPlantsMapRepository.existsByUserAndPlant(userId = userId, plantId = plantId)
    }

    @Transactional(readOnly = true)
    override fun getPlantById(userId: String, plantId: UUID): Plant {
        checkUserHasPlant(userId = userId, plantId = plantId)
        return plantRepository.findByUuid(plantId = plantId)!!.toDomain()
    }

    @Transactional(readOnly = true)
    override fun getPlantsByIds(userId: String, plantIds: List<UUID>): List<Plant> {
        val userPlants = usersPlantsMapRepository.findAllByUserAndPlantIn(userId, plantIds)
            .map { it.plant }
        return plantRepository.findAllByUuidInOrderByUuid(plantIds = userPlants).map { it.toDomain() }
    }

    @Transactional
    override fun addPlant(userId: String, plant: Plant): Plant {
        val addedPlant = plantRepository.save(plant.toEntity()).toDomain()
        usersPlantsMapRepository.save(
            UsersPlantsMapEntity(
                user = userId,
                plant = addedPlant.uuid,
            )
        )
        return addedPlant
    }

    @Transactional
    override fun updatePlant(userId: String, plant: Plant): Plant {
        checkUserHasPlant(userId = userId, plantId = plant.uuid)
        return plantRepository.save(plant.toEntity()).toDomain()
    }

    @Transactional
    override fun deletePlant(userId: String, plantId: UUID) {
        checkUserHasPlant(userId = userId, plantId = plantId)
        plantRepository.deleteById(plantId)
    }

    @Transactional(readOnly = true)
    override fun getAllPlants(userId: String): List<Plant> {
        val plantIds = usersPlantsMapRepository.findAllByUser(userId).map {
            it.plant
        }.toList()
        return plantRepository.findAllByUuidInOrderByUuid(plantIds = plantIds).map { it.toDomain() }
    }

    private fun checkUserHasPlant(userId: String, plantId: UUID) {
        if (!isUserPlant(userId = userId, plantId = plantId)) {
            log.info("user with id=$userId tried to read plant with id=$plantId")
            throw IllegalAccessException()
        }
    }
}
