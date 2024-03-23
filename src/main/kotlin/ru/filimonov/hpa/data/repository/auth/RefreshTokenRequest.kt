package ru.filimonov.hpa.data.repository.auth

import com.fasterxml.jackson.annotation.JsonProperty

data class RefreshTokenRequest(
    @JsonProperty("refresh_token")
    val refreshToken: String,
    @JsonProperty("grant_type")
    val grantType: String = "refresh_token"
)
