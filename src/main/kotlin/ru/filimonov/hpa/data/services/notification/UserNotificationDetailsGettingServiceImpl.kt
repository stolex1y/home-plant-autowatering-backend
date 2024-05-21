package ru.filimonov.hpa.data.services.notification

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.filimonov.hpa.data.repositories.UserNotificationDetailsRepository
import ru.filimonov.hpa.domain.model.UserNotificationDetails
import ru.filimonov.hpa.domain.services.notification.UserNotificationDetailsGettingService

@Service
class UserNotificationDetailsGettingServiceImpl(
    private val repository: UserNotificationDetailsRepository,
) : UserNotificationDetailsGettingService {
    @Transactional(readOnly = true)
    override fun getAllNotificationDetails(): List<UserNotificationDetails> {
        return repository.findAll().map { it.toDomain() }
    }

    @Transactional(readOnly = true)
    override fun getAllUserNotificationDetails(userId: String): List<UserNotificationDetails> {
        return repository.findAllByUserId(userId).map { it.toDomain() }
    }

    @Transactional(readOnly = true)
    override fun getNotificationDetails(userId: String, token: String): UserNotificationDetails? {
        return repository.findByUserIdAndToken(userId = userId, token = token)?.toDomain()
    }
}
