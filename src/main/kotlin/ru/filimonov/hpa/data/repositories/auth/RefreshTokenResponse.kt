package ru.filimonov.hpa.data.repositories.auth

import com.fasterxml.jackson.annotation.JsonProperty

data class RefreshTokenResponse(
    @JsonProperty("id_token")
    val idToken: String
)
