package com.jsrdev.med_api.infra.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfiguration {

    @Bean
    fun securityFilterChain(httpSecurity: HttpSecurity): SecurityFilterChain =
        httpSecurity.csrf { it.disable() }
            .authorizeHttpRequests {
                it.requestMatchers("/api/auth").permitAll()
                    .anyRequest()
                    .fullyAuthenticated()
            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .build()
}