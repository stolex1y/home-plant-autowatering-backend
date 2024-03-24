package ru.filimonov.hpa.domain.service

import java.util.UUID

interface PlantService {
    fun isUserPlant(userId: String, plantId: UUID): Boolean
}
