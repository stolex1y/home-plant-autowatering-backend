package ru.filimonov.hpa.data.service

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service
import ru.filimonov.hpa.data.repository.auth.AuthTokenRepository
import ru.filimonov.hpa.domain.service.auth.AuthService

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
