package ru.filimonov.hpa.rest.device

import org.apache.commons.logging.LogFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import ru.filimonov.hpa.core.CALENDAR_MIN
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
    fun getDevicePhoto(
        @AuthenticationPrincipal user: User,
        @PathVariable deviceId: UUID,
        @RequestHeader("If-Modified-Since") modifiedSince: Calendar = CALENDAR_MIN,
    ): ResponseEntity<ByteArray?> {
        val lastModified = devicePhotoService.getPhotoUpdatedDate(userId = user.id, deviceId = deviceId)
            ?: return ResponseEntity.notFound().build()

        if (lastModified < modifiedSince) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build()
        }

        val photo = devicePhotoService.getDevicePhoto(
            userId = user.id, deviceId = deviceId
        ) ?: return ResponseEntity.notFound().build()

        return ResponseEntity.ok().lastModified(photo.updatedDate.timeInMillis).body(photo.photo)
    }

    @PutMapping
    @Transactional
    fun updateDevicePhoto(
        @AuthenticationPrincipal user: User,
        @PathVariable deviceId: UUID,
        @RequestParam("photo") photo: MultipartFile,
    ): ResponseEntity<Unit> {
        devicePhotoService.updateDevicePhoto(userId = user.id, deviceId = deviceId, photoBytes = photo.bytes)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

    @DeleteMapping
    fun removeDevicePhoto(
        @AuthenticationPrincipal user: User,
        @PathVariable deviceId: UUID,
    ): ResponseEntity<Unit> {
        devicePhotoService.deleteDevicePhoto(userId = user.id, deviceId = deviceId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
}
