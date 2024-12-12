package com.jsrdev.med_api.infra.security

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("jwt")
data class JwtProperties(
    val secretKey: String
)
