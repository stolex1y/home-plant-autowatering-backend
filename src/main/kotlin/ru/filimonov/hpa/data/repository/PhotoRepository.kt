package ru.filimonov.hpa.data.repository

import org.springframework.data.repository.CrudRepository
import ru.filimonov.hpa.data.model.PhotoEntity
import java.util.*

interface PhotoRepository : CrudRepository<PhotoEntity, UUID>
