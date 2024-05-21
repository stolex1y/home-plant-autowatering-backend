package ru.filimonov.hpa.data.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import ru.filimonov.hpa.common.toTime
import ru.filimonov.hpa.common.toTimestamp
import ru.filimonov.hpa.common.toZonedDateTime
import ru.filimonov.hpa.domain.model.UserNotificationDetails
import java.sql.Time
import java.sql.Timestamp

@Entity
@Table(name = "user_notification_details")
data class UserNotificationDetailsEntity(
    @Id
    val token: String,
    val userId: String,
    val notificationTime: Time,
) {
    fun toDomain() = UserNotificationDetails(
        userId = userId,
        token = token,
        notificationTime = notificationTime.toLocalTime(),
    )
}

fun UserNotificationDetails.toEntity() = UserNotificationDetailsEntity(
    userId = userId,
    token = token,
    notificationTime = notificationTime.toTime(),
)
