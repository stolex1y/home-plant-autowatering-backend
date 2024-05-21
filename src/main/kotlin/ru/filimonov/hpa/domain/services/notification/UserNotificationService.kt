package ru.filimonov.hpa.domain.services.notification

interface UserNotificationService {
    fun updateNotification(userId: String, notificationToken: String)
}
