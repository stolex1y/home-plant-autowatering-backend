package ru.filimonov.hpa.rest.device

import org.apache.commons.logging.LogFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import ru.filimonov.hpa.domain.model.User
import ru.filimonov.hpa.domain.service.DevicePhotoService
import java.util.*

@RestController
@RequestMapping("/devices/{deviceId}/photo")
class DevicePhotoController(
    private val devicePhotoService: DevicePhotoService
) {
    private val log = LogFactory.getLog(javaClass)

    @GetMapping
    fun getPlantPhoto(
        @AuthenticationPrincipal user: User,
        @PathVariable deviceId: UUID,
    ): ResponseEntity<ByteArray> {
        return ResponseEntity.ok(devicePhotoService.getDevicePhoto(userId = user.id, deviceId = deviceId)?.photo)
    }

    @PutMapping
    @Transactional
    fun updatePlantPhoto(
        @AuthenticationPrincipal user: User,
        @PathVariable deviceId: UUID,
        @RequestParam("photo") photo: MultipartFile,
    ): ResponseEntity<Unit> {
        devicePhotoService.updateDevicePhoto(userId = user.id, deviceId = deviceId, photoBytes = photo.bytes)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

    @DeleteMapping
    fun removePlantPhoto(
        @AuthenticationPrincipal user: User,
        @PathVariable deviceId: UUID,
    ): ResponseEntity<Unit> {
        devicePhotoService.deleteDevicePhoto(userId = user.id, deviceId = deviceId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
}
