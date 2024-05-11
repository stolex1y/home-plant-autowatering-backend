package ru.filimonov.hpa.data.services

import org.apache.commons.logging.LogFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.validation.annotation.Validated
import ru.filimonov.hpa.data.model.UsersPlantsMapEntity
import ru.filimonov.hpa.data.model.toEntity
import ru.filimonov.hpa.data.repositories.DeviceRepository
import ru.filimonov.hpa.data.repositories.PlantRepository
import ru.filimonov.hpa.data.repositories.UsersPlantsMapRepository
import ru.filimonov.hpa.domain.model.Configuration
import ru.filimonov.hpa.domain.model.Plant
import ru.filimonov.hpa.domain.model.toConfiguration
import ru.filimonov.hpa.domain.services.PlantService
import ru.filimonov.hpa.domain.services.UpdateConfigurationEventService
import java.util.*

@Service
@Validated
class PlantServiceImpl(
    private val usersPlantsMapRepository: UsersPlantsMapRepository,
    private val plantRepository: PlantRepository,
    private val deviceRepository: DeviceRepository,
    private val updateConfigurationEventService: UpdateConfigurationEventService,
) : PlantService {
    private val log = LogFactory.getLog(javaClass)

    @Transactional(readOnly = true)
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
        deviceRepository.findByPlantId(plantId = addedPlant.uuid)?.uuid?.let { deviceId ->
            updateConfigurationEventService.sendUpdateConfigEvent(
                deviceId = deviceId,
                configuration = addedPlant.toConfiguration()
            )
        }
        return addedPlant
    }

    @Transactional
    override fun updatePlant(userId: String, plant: Plant): Plant {
        checkUserHasPlant(userId = userId, plantId = plant.uuid)
        val updatedPlant = plantRepository.save(plant.toEntity()).toDomain()
        deviceRepository.findByPlantId(plantId = updatedPlant.uuid)?.uuid?.let { deviceId ->
            updateConfigurationEventService.sendUpdateConfigEvent(
                deviceId = deviceId,
                configuration = updatedPlant.toConfiguration()
            )
        }
        return updatedPlant
    }

    @Transactional
    override fun deletePlant(userId: String, plantId: UUID) {
        checkUserHasPlant(userId = userId, plantId = plantId)
        val deviceId = deviceRepository.findByPlantId(plantId = plantId)?.uuid
        plantRepository.deleteById(plantId)
        if (deviceId != null) {
            updateConfigurationEventService.sendUpdateConfigEvent(
                deviceId = deviceId,
                configuration = Configuration.defaultConfiguration()
            )
        }
    }

    @Transactional(readOnly = true)
    override fun getAllPlants(userId: String): List<Plant> {
        val plantIds = usersPlantsMapRepository.findAllByUser(userId).map {
            it.plant
        }.toList()
        return plantRepository.findAllByUuidInOrderByUuid(plantIds = plantIds).map { it.toDomain() }
    }

    @Transactional(readOnly = true)
    private fun checkUserHasPlant(userId: String, plantId: UUID) {
        if (!isUserPlant(userId = userId, plantId = plantId)) {
            log.info("user with id=$userId tried to read plant with id=$plantId")
            throw IllegalAccessException()
        }
    }
}
