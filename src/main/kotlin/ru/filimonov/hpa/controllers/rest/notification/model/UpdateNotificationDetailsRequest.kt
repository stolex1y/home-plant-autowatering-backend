package ru.filimonov.hpa.controllers.rest.notification.model

import ru.filimonov.hpa.common.toLocalTime
import ru.filimonov.hpa.domain.model.UserNotificationDetails

data class UpdateNotificationDetailsRequest(
    val token: String,
    val notificationTime: Long,
) {
    fun applyUpdates(notificationDetails: UserNotificationDetails) = notificationDetails.copy(
        token = token,
        notificationTime = notificationTime.toLocalTime(),
    )
}
