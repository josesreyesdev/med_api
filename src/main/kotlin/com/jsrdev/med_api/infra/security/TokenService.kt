package com.jsrdev.med_api.infra.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@Service
class TokenService(jwtProperties: JwtProperties) {

    private val secretKey = jwtProperties.secretKey

    fun generate(userDetails: UserDetails): String {
        val algorithm: Algorithm = Algorithm.HMAC256(secretKey)

        return JWT.create()
                .withIssuer("med_api")
                .withSubject(userDetails.username)
                .withExpiresAt(generateExpirationDate())
                .sign(algorithm)
    }

    private fun generateExpirationDate(): Instant =
        LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-06:00"))
}