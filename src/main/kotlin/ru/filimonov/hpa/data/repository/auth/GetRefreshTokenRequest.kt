package ru.filimonov.hpa.data.repository.auth

data class GetRefreshTokenRequest(
    val token: String,
    val returnSecureToken: Boolean = true
)
