package ru.filimonov.hpa.data.model

import jakarta.persistence.*
import java.io.Serializable
import java.util.*

@Entity
@Table(name = "users_plants")
@IdClass(UsersPlantsMapEntity::class)
data class UsersPlantsMapEntity(
    @Id
    @Column(name = "user_id")
    val user: String,

    @Id
    val plant: UUID
) : Serializable
