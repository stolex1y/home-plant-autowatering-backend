package ru.filimonov.hpa.data.services

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service
import ru.filimonov.hpa.data.repositories.auth.AuthTokenRepository
import ru.filimonov.hpa.domain.services.auth.AuthService

@Service
class AuthServiceImpl(
    private val firebaseAuth: FirebaseAuth,
    private val authTokenRepository: AuthTokenRepository
) : AuthService {
    override fun getRefreshToken(userId: String): Flow<Result<String>> {
        return authTokenRepository.getRefreshToken(firebaseAuth.createCustomToken(userId))
    }

    override fun refreshIdToken(refreshToken: String): Flow<Result<String>> {
        return authTokenRepository.refreshIdToken(refreshToken)
    }
}
