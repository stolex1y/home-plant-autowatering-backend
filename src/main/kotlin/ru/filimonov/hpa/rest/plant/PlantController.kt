package ru.filimonov.hpa.rest.plant

import org.apache.commons.logging.LogFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import ru.filimonov.hpa.domain.model.User
import ru.filimonov.hpa.domain.service.PlantService
import ru.filimonov.hpa.rest.plant.model.AddPlantRequest
import ru.filimonov.hpa.rest.plant.model.PlantResponse
import ru.filimonov.hpa.rest.plant.model.PlantResponse.Companion.toResponse
import ru.filimonov.hpa.rest.plant.model.UpdatePlantRequest
import java.util.*

@RestController
@RequestMapping("/plants")
class PlantController(
    private val plantService: PlantService,
) {
    private val log = LogFactory.getLog(javaClass)

    @GetMapping
    fun getAllPlants(
        @AuthenticationPrincipal user: User
    ): ResponseEntity<List<PlantResponse>> {
        val plants = plantService.getAllPlants(user.id)
            .map { it.toResponse() }
        return ResponseEntity.ok(plants)
    }

    @GetMapping("/{ids}")
    fun getPlantsByIds(
        @AuthenticationPrincipal user: User,
        @PathVariable ids: List<UUID>,
    ): ResponseEntity<List<PlantResponse>> {
        val plants = plantService.getPlantsByIds(user.id, ids)
            .map { it.toResponse() }
        return ResponseEntity.ok(plants)
    }

    @GetMapping("/{plantId}")
    fun getPlant(
        @AuthenticationPrincipal user: User,
        @PathVariable plantId: UUID,
    ): ResponseEntity<PlantResponse> {
        val plant = plantService.getPlantById(user.id, plantId).toResponse()
        return ResponseEntity.ok(plant)
    }

    @PostMapping
    fun addPlant(
        @AuthenticationPrincipal user: User,
        @RequestBody plant: AddPlantRequest,
    ): ResponseEntity<PlantResponse> {
        val createdPlant = plantService.addPlant(user.id, plant.toDomain()).toResponse()
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(createdPlant)
    }

    @PutMapping("/{plantId}")
    @Transactional
    fun updatePlant(
        @AuthenticationPrincipal user: User,
        @PathVariable plantId: UUID,
        @RequestBody plantUpdate: UpdatePlantRequest,
    ): ResponseEntity<PlantResponse> {
        val beforeUpdate = plantService.getPlantById(userId = user.id, plantId = plantId)
        val afterUpdate = plantUpdate.applyUpdates(beforeUpdate)
        plantService.updatePlant(userId = user.id, plant = afterUpdate)
        return ResponseEntity.ok(afterUpdate.toResponse())
    }

    @DeleteMapping("/{plantId}")
    fun removePlant(
        @AuthenticationPrincipal user: User,
        @PathVariable plantId: UUID,
    ): ResponseEntity<Unit> {
        plantService.deletePlant(user.id, plantId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
}
