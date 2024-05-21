package ru.filimonov.hpa.data.services.notification

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.validation.annotation.Validated
import ru.filimonov.hpa.data.model.toEntity
import ru.filimonov.hpa.data.repositories.UserNotificationDetailsRepository
import ru.filimonov.hpa.domain.model.UserNotificationDetails
import ru.filimonov.hpa.domain.services.notification.UserNotificationDetailsService
import ru.filimonov.hpa.domain.services.notification.UserNotificationScheduler
import kotlin.jvm.optionals.getOrNull

@Service
@Validated
class UserNotificationDetailsServiceImpl(
    private val repository: UserNotificationDetailsRepository,
    private val userNotificationScheduler: UserNotificationScheduler,
) : UserNotificationDetailsService {
    @Transactional
    override fun updateNotificationDetails(notificationDetails: UserNotificationDetails) {
        val beforeUpdating = repository.findById(notificationDetails.userId).getOrNull()?.toDomain()
        repository.save(notificationDetails.toEntity())
        if (beforeUpdating?.notificationTime != notificationDetails.notificationTime) {
            userNotificationScheduler.scheduleNotificationSending(userId = notificationDetails.userId)
        }
    }

    @Transactional
    override fun updateNotificationToken(userId: String, token: String) {
        val notificationDetails =
            repository.findById(userId).orElseThrow { IllegalArgumentException("Couldn't find notification details") }
        repository.save(notificationDetails.copy(token = token))
    }

    @Transactional
    override fun deleteNotificationDetails(userId: String) {
        repository.deleteById(userId)
    }
}
