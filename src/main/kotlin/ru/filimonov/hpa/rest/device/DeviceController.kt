package ru.filimonov.hpa.rest.device

import org.apache.commons.logging.LogFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import ru.filimonov.hpa.domain.model.User
import ru.filimonov.hpa.domain.service.DeviceService
import ru.filimonov.hpa.rest.device.model.AddDeviceRequest
import ru.filimonov.hpa.rest.device.model.DeviceResponse
import ru.filimonov.hpa.rest.device.model.UpdateDeviceRequest
import ru.filimonov.hpa.rest.device.model.toResponse
import java.util.*

@RestController
@RequestMapping("/devices")
class DeviceController(
    private val deviceService: DeviceService,
) {
    private val log = LogFactory.getLog(javaClass)

    @GetMapping
    fun getAllDevices(
        @AuthenticationPrincipal user: User
    ): ResponseEntity<List<DeviceResponse>> {
        val devices = deviceService.getAllDevicesByUserId(user.id)
            .map { it.toResponse() }
        return ResponseEntity.ok(devices)
    }

    @PostMapping
    fun addDevice(
        @AuthenticationPrincipal user: User,
        @RequestBody device: AddDeviceRequest,
    ): ResponseEntity<DeviceResponse> {
        val createdDevice = deviceService.addDevice(device.toDomain(user.id)).toResponse()
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(createdDevice)
    }

    @GetMapping("/{deviceId}")
    fun getDevice(
        @AuthenticationPrincipal user: User,
        @PathVariable deviceId: UUID,
    ): ResponseEntity<DeviceResponse> {
        val device = deviceService.getDeviceById(user.id, deviceId).toResponse()
        return ResponseEntity.ok(device)
    }

    @PutMapping("/{deviceId}")
    @Transactional
    fun updateDevice(
        @AuthenticationPrincipal user: User,
        @PathVariable deviceId: UUID,
        @RequestBody deviceUpdate: UpdateDeviceRequest,
    ): ResponseEntity<DeviceResponse> {
        val beforeUpdate = deviceService.getDeviceById(userId = user.id, deviceId = deviceId)
        val afterUpdate = deviceUpdate.applyUpdates(beforeUpdate)
        deviceService.updateDevice(afterUpdate)
        return ResponseEntity.ok(afterUpdate.toResponse())
    }

    @DeleteMapping("/{deviceId}")
    fun removeDevice(
        @AuthenticationPrincipal user: User,
        @PathVariable deviceId: UUID,
    ): ResponseEntity<Unit> {
        deviceService.deleteDevice(user.id, deviceId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
}
