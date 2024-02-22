package ru.filimonov.hpa.data.repository

import org.springframework.data.repository.CrudRepository
import ru.filimonov.hpa.data.model.DeviceEntity
import java.util.*

interface DeviceRepository : CrudRepository<DeviceEntity, UUID>
