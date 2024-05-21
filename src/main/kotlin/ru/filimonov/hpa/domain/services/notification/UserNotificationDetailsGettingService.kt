package ru.filimonov.hpa.domain.services.notification

import ru.filimonov.hpa.domain.model.UserNotificationDetails

interface UserNotificationDetailsGettingService {
    fun getAllUserNotificationDetails(userId: String): List<UserNotificationDetails>
    fun getNotificationDetails(userId: String, token: String): UserNotificationDetails?
    fun getAllNotificationDetails(): List<UserNotificationDetails>
}
