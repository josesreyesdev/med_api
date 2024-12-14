package com.jsrdev.med_api.infra.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.DecodedJWT
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

@Service
class TokenService(jwtProperties: JwtProperties) {

    private val secretKey = Algorithm.HMAC256(jwtProperties.secretKey)

    fun generate(
        userDetails: UserDetails,
        additionalClaims: Map<String, Any> = emptyMap()
    ): String {

        val jwtBuilder = JWT.create()
            .withIssuer("med_api")
            .withSubject(userDetails.username)
            .withIssuedAt(Date.from(Instant.now()))
            .withExpiresAt(generateExpirationDate())

        // added additional claims if any
        additionalClaims.forEach { (key, value) ->
            jwtBuilder.withClaim(key, value.toString())
        }

        return jwtBuilder.sign(secretKey)
    }

    private fun generateExpirationDate(): Instant =
        LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-06:00"))

    fun isValid(token: String, userDetails: UserDetails): Boolean {
        val decodedJWT = decodeToken(token)
        val email = extractSubject(token)

        return userDetails.username == email && !isExpired(decodedJWT)
    }

    fun extractSubject(token: String): String? =
        decodeToken(token).subject

    private fun isExpired(decodedJWT: DecodedJWT): Boolean =
        decodedJWT.expiresAt.before(Date(System.currentTimeMillis()))

    private fun decodeToken(token: String): DecodedJWT {
        try {
            val verifier = JWT.require(secretKey)
                .withIssuer("med_api")
                .build()

            return verifier.verify(token)
        } catch (ex: JWTVerificationException) {
            throw IllegalArgumentException("invalid or expired token")
        }
    }
}