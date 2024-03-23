package ru.filimonov.hpa.rest.auth

import kotlinx.coroutines.flow.first
import org.springframework.security.access.annotation.Secured
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.filimonov.hpa.domain.model.User
import ru.filimonov.hpa.domain.service.auth.AuthService

@RestController
class AuthController(
    private val authService: AuthService
) {
    @GetMapping("/auth")
    suspend fun authenticate(@AuthenticationPrincipal user: User): AuthResponse {
        return AuthResponse(
            refreshToken = authService.getRefreshToken(user.id).first()
        )
    }

    @PostMapping("/reauth")
    suspend fun refreshIdToken(@RequestBody refreshTokenRequest: RefreshTokenRequest): RefreshTokenResponse {
        return RefreshTokenResponse(
            idToken = authService.refreshIdToken(refreshTokenRequest.refreshToken).first()
        )
    }
}
