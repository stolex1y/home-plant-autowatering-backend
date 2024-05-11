package ru.filimonov.hpa.domain.services.auth

import kotlinx.coroutines.flow.Flow

interface AuthService {
    fun getRefreshToken(userId: String): Flow<Result<String>>
    fun refreshIdToken(refreshToken: String): Flow<Result<String>>
}
