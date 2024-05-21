package ru.filimonov.hpa.domain.services.notification

interface UserNotificationScheduler {
    fun scheduleNotificationSending(userId: String)
}
