package ru.filimonov.hpa.controllers.rest.auth

import kotlinx.coroutines.flow.first
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import ru.filimonov.hpa.domain.model.User
import ru.filimonov.hpa.domain.services.auth.AuthService

@RestController
class AuthController(
    private val authService: AuthService
) {
    @GetMapping("/auth")
    suspend fun authenticate(@AuthenticationPrincipal user: User): AuthResponse {
        val refreshToken = authService.getRefreshToken(user.id).first().getOrThrow()
        return AuthResponse(
            refreshToken = refreshToken
        )
    }

    @PostMapping("/reauth")
    suspend fun refreshIdToken(@RequestBody refreshTokenRequest: RefreshTokenRequest): RefreshTokenResponse {
        val idToken = authService.refreshIdToken(refreshTokenRequest.refreshToken).first().getOrThrow()
        return RefreshTokenResponse(
            idToken = idToken
        )
    }
}
