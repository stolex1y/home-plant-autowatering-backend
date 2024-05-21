package ru.filimonov.hpa.data.repositories

import org.springframework.data.repository.CrudRepository
import ru.filimonov.hpa.data.model.UserNotificationDetailsEntity

interface UserNotificationDetailsRepository : CrudRepository<UserNotificationDetailsEntity, String> {
    fun findAllByUserId(userId: String): List<UserNotificationDetailsEntity>
    fun findByUserIdAndToken(userId: String, token: String): UserNotificationDetailsEntity?
}
