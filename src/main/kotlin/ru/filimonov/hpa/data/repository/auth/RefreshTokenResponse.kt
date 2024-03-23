package ru.filimonov.hpa.data.repository.auth

import com.fasterxml.jackson.annotation.JsonKey
import com.fasterxml.jackson.annotation.JsonProperty

data class RefreshTokenResponse(
    @JsonProperty("id_token")
    val idToken: String
)
