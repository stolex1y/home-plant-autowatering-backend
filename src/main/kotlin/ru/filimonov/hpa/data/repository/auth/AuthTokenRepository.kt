package ru.filimonov.hpa.data.repository.auth

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Repository
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToFlow

@Repository
class AuthTokenRepository(
    private val webClient: WebClient,
    @Value("\${app.firebase.api-key}") private val apiKey: String
) {
    fun getRefreshToken(customToken: String): Flow<String> {
        return webClient.post()
            .uri("https://identitytoolkit.googleapis.com/v1/accounts:signInWithCustomToken?key=${apiKey}")
            .body(BodyInserters.fromValue(GetRefreshTokenRequest(token = customToken)))
            .retrieve()
            .bodyToFlow<GetRefreshTokenResponse>()
            .map { it.refreshToken }
    }

    fun refreshIdToken(refreshToken: String): Flow<String> {
        return webClient.post()
            .uri("https://securetoken.googleapis.com/v1/token?key=${apiKey}")
            .body(BodyInserters.fromValue(RefreshTokenRequest(refreshToken = refreshToken)))
            .retrieve()
            .bodyToFlow<RefreshTokenResponse>()
            .map { it.idToken }
    }
}
