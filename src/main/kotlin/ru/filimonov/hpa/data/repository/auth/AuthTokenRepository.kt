package ru.filimonov.hpa.data.repository.auth

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Repository
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToFlow
import org.springframework.web.reactive.function.client.exchangeToFlow
import ru.filimonov.hpa.core.mapToResult

@Repository
class AuthTokenRepository(
    private val webClient: WebClient,
    @Value("\${app.firebase.api-key}") private val apiKey: String
) {
    fun getRefreshToken(customToken: String): Flow<Result<String>> {
        return webClient.post()
            .uri("https://identitytoolkit.googleapis.com/v1/accounts:signInWithCustomToken?key=${apiKey}")
            .body(BodyInserters.fromValue(GetRefreshTokenRequest(token = customToken)))
            .exchangeToFlow { clientResponse ->
                if (clientResponse.statusCode().is2xxSuccessful) {
                    clientResponse.bodyToFlow<GetRefreshTokenResponse>().map { it.refreshToken }.mapToResult()
                } else if (clientResponse.statusCode().is4xxClientError) {
                    flowOf(Result.failure(IllegalArgumentException()))
                } else {
                    flowOf(Result.failure(IllegalStateException()))
                }
            }
    }

    fun refreshIdToken(refreshToken: String): Flow<Result<String>> {
        return webClient.post()
            .uri("https://securetoken.googleapis.com/v1/token?key=${apiKey}")
            .body(BodyInserters.fromValue(RefreshTokenRequest(refreshToken = refreshToken)))
            .exchangeToFlow { clientResponse ->
                if (clientResponse.statusCode().is2xxSuccessful) {
                    clientResponse.bodyToFlow<RefreshTokenResponse>().map { it.idToken }.mapToResult()
                } else if (clientResponse.statusCode().is4xxClientError) {
                    flowOf(Result.failure(IllegalArgumentException()))
                } else {
                    flowOf(Result.failure(IllegalStateException()))
                }
            }
    }
}
