package ru.filimonov.hpa.domain.service.auth

import kotlinx.coroutines.flow.Flow

interface AuthService {
    fun getRefreshToken(userId: String): Flow<String>
    fun refreshIdToken(refreshToken: String): Flow<String>
}
