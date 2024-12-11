package com.jsrdev.med_api.infra.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTCreationException
import org.springframework.stereotype.Service


@Service
class TokenService {

    fun generate(): String {
        return try {
            val algorithm: Algorithm = Algorithm.HMAC256("123456")
            val token = JWT.create()
                .withIssuer("med_api")
                .withSubject("jsrdev")
                .sign(algorithm)
            token
        } catch (exception: JWTCreationException) {
            throw RuntimeException()
        }
    }
}