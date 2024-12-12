package com.jsrdev.med_api.controller

import com.jsrdev.med_api.domain.auth.AuthenticationRequest
import com.jsrdev.med_api.domain.auth.AuthenticationResponse
import com.jsrdev.med_api.infra.security.AuthService
import com.jsrdev.med_api.infra.security.TokenService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authManager: AuthenticationManager,
    private val tokenService: TokenService,
    private val authService: AuthService
) {

    @PostMapping
    fun authenticate(
        @RequestBody @Valid authenticationRequest: AuthenticationRequest
    ): ResponseEntity<AuthenticationResponse> {
        val authToken = UsernamePasswordAuthenticationToken(
            authenticationRequest.email,
            authenticationRequest.password
        )
        authManager.authenticate(authToken)

        val userDetails = authService.loadUserByUsername(authenticationRequest.email)

        val jwtToken: String = tokenService.generate(userDetails)

        return ResponseEntity.ok(
            AuthenticationResponse(accessToken = jwtToken)
        )
    }
}