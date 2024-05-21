package ru.filimonov.hpa.domain.services.notification

import jakarta.validation.Valid
import ru.filimonov.hpa.domain.model.UserNotificationDetails

interface UserNotificationDetailsService {
    fun updateNotificationDetails(@Valid notificationDetails: UserNotificationDetails)
    fun updateNotificationToken(userId: String, token: String)
    fun deleteNotificationDetails(userId: String)
}
