package ru.filimonov.hpa.data.service

import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated
import ru.filimonov.hpa.data.repository.UsersPlantsMapRepository
import ru.filimonov.hpa.domain.service.PlantService
import java.util.*

@Service
@Validated
class PlantServiceImpl(
    private val usersPlantsMapRepository: UsersPlantsMapRepository
) : PlantService {
    override fun isUserPlant(userId: String, plantId: UUID): Boolean {
        return usersPlantsMapRepository.existsByUserAndPlant(userId, plantId)
    }
}
