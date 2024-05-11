package ru.filimonov.hpa.controllers.rest.plant

import org.apache.commons.logging.LogFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import ru.filimonov.hpa.controllers.rest.plant.model.AddPlantRequest
import ru.filimonov.hpa.controllers.rest.plant.model.PlantResponse
import ru.filimonov.hpa.controllers.rest.plant.model.PlantResponse.Companion.toResponse
import ru.filimonov.hpa.controllers.rest.plant.model.UpdatePlantRequest
import ru.filimonov.hpa.domain.model.User
import ru.filimonov.hpa.domain.services.PlantService
import java.util.*

@RestController
@RequestMapping("/plants")
class PlantController(
    private val plantService: PlantService,
) {
    private val log = LogFactory.getLog(javaClass)

    @GetMapping
    fun getPlantsByIds(
        @AuthenticationPrincipal user: User,
        @RequestParam(required = false, name = "ids") ids: List<UUID> = emptyList(),
    ): ResponseEntity<List<PlantResponse>> {
        if (ids.isEmpty()) {
            val plants = plantService.getAllPlants(user.id)
                .map { it.toResponse(user) }
            return ResponseEntity.ok(plants)
        } else {
            val plants = plantService.getPlantsByIds(user.id, ids)
                .map { it.toResponse(user) }
            return ResponseEntity.ok(plants)
        }
    }

    @GetMapping("/{plantId}")
    fun getPlant(
        @AuthenticationPrincipal user: User,
        @PathVariable plantId: UUID,
    ): ResponseEntity<PlantResponse> {
        val plant = plantService.getPlantById(user.id, plantId).toResponse(user)
        return ResponseEntity.ok(plant)
    }

    @PostMapping
    fun addPlant(
        @AuthenticationPrincipal user: User,
        @RequestBody plant: AddPlantRequest,
    ): ResponseEntity<PlantResponse> {
        val createdPlant = plantService.addPlant(user.id, plant.toDomain()).toResponse(user)
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
        return ResponseEntity.ok(afterUpdate.toResponse(user))
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
