package com.jsrdev.med_api.domain.auth

data class AuthenticationRequest(
    val email: String,
    val password: String,
)
