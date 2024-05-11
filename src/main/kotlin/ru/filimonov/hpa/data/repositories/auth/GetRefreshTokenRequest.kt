package ru.filimonov.hpa.data.repositories.auth

data class GetRefreshTokenRequest(
    val token: String,
    val returnSecureToken: Boolean = true
)
