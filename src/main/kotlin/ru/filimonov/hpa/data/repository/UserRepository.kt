package ru.filimonov.hpa.data.repository

import org.springframework.data.repository.CrudRepository
import ru.filimonov.hpa.data.model.UserEntity
import java.util.*

interface UserRepository : CrudRepository<UserEntity, UUID>
