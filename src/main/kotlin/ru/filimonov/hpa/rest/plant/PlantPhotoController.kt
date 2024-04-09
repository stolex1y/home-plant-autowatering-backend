package ru.filimonov.hpa.rest.plant

import org.apache.commons.logging.LogFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import ru.filimonov.hpa.domain.model.User
import ru.filimonov.hpa.domain.service.PlantPhotoService
import java.util.*

@RestController
@RequestMapping("/plants/{plantId}/photo")
class PlantPhotoController(
    private val plantPhotoService: PlantPhotoService
) {
    private val log = LogFactory.getLog(javaClass)

    @GetMapping
    fun getPlantPhoto(
        @AuthenticationPrincipal user: User,
        @PathVariable plantId: UUID,
    ): ResponseEntity<ByteArray> {
        return ResponseEntity.ok(plantPhotoService.getPlantPhoto(userId = user.id, plantId = plantId).photo)
    }

    @PutMapping
    @Transactional
    fun updatePlantPhoto(
        @AuthenticationPrincipal user: User,
        @PathVariable plantId: UUID,
        @RequestParam("photo") photo: MultipartFile,
    ): ResponseEntity<Unit> {
        plantPhotoService.updatePlantPhoto(userId = user.id, plantId = plantId, photoBytes = photo.bytes)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

    @DeleteMapping
    fun removePlantPhoto(
        @AuthenticationPrincipal user: User,
        @PathVariable plantId: UUID,
    ): ResponseEntity<Unit> {
        plantPhotoService.deletePlantPhoto(userId = user.id, plantId = plantId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
}
