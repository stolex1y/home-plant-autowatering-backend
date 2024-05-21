package ru.filimonov.hpa.domain.model

import jakarta.validation.constraints.NotBlank
import java.time.LocalTime

data class UserNotificationDetails(
    @NotBlank
    val userId: String,

    @NotBlank
    val token: String,

    val notificationTime: LocalTime,
)
