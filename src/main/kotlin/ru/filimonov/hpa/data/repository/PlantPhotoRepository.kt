package ru.filimonov.hpa.data.repository

import org.springframework.data.repository.CrudRepository
import ru.filimonov.hpa.data.model.PlantPhotoEntity
import java.util.*

interface PlantPhotoRepository : CrudRepository<PlantPhotoEntity, UUID> {
}
