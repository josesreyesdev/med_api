package com.jsrdev.med_api.controller

import com.jsrdev.med_api.domain.auth.AuthenticationRequest
import com.jsrdev.med_api.domain.auth.AuthenticationResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController (
    private val authManager: AuthenticationManager
) {

    @PostMapping
    fun authenticate(@RequestBody authenticationRequest: AuthenticationRequest): ResponseEntity<AuthenticationResponse> {
        val token = UsernamePasswordAuthenticationToken(
            authenticationRequest.email,
            authenticationRequest.password
        )
        authManager.authenticate(token)

        return ResponseEntity.ok().build()
    }
}