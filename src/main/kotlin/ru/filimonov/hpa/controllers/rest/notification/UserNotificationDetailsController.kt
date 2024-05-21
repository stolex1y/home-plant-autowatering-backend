package ru.filimonov.hpa.controllers.rest.notification

import org.apache.commons.logging.LogFactory
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.filimonov.hpa.controllers.rest.notification.model.UpdateNotificationDetailsRequest
import ru.filimonov.hpa.domain.model.User
import ru.filimonov.hpa.domain.model.UserNotificationDetails
import ru.filimonov.hpa.domain.services.notification.UserNotificationDetailsGettingService
import ru.filimonov.hpa.domain.services.notification.UserNotificationDetailsService
import java.time.LocalTime

@RestController
@RequestMapping("/notification-details")
class UserNotificationDetailsController(
    private val userNotificationDetailsService: UserNotificationDetailsService,
    private val userNotificationDetailsGettingService: UserNotificationDetailsGettingService,
) {
    private val log = LogFactory.getLog(javaClass)

    @PutMapping
    @Transactional
    fun updateNotificationDetails(
        @AuthenticationPrincipal user: User,
        @RequestBody updateNotificationDetailsRequest: UpdateNotificationDetailsRequest,
    ): ResponseEntity<Unit> {
        val beforeUpdate = userNotificationDetailsGettingService.getNotificationDetails(
            userId = user.id,
            token = updateNotificationDetailsRequest.token
        )
            ?: UserNotificationDetails(
                userId = user.id,
                token = "",
                notificationTime = LocalTime.now()
            )
        val afterUpdate = updateNotificationDetailsRequest.applyUpdates(beforeUpdate)
        userNotificationDetailsService.updateNotificationDetails(afterUpdate)
        return ResponseEntity.noContent().build()
    }
}
