package com.jsrdev.med_api.infra.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfiguration {

    @Bean
    fun securityFilterChain(
        httpSecurity: HttpSecurity,
        securityFilter: SecurityFilter
    ): SecurityFilterChain {
        httpSecurity.csrf { it.disable() }
            .authorizeHttpRequests {
                it
                    .requestMatchers(
                        "/v3/api-docs/**",    // Allow access to OpenAPI docs
                        "/swagger-ui/**",     // Allow access to Swagger UI
                        "/swagger-ui.html"
                    ).permitAll()
                    .requestMatchers("/api/auth")
                    .permitAll()
                    .requestMatchers(
                        HttpMethod.POST,
                        "/api/users"
                    ).permitAll()
                    .requestMatchers(
                        HttpMethod.DELETE,
                        "/api/physicians**",
                        "/api/patients**"
                    ).hasRole("ADMIN")
                    .requestMatchers(
                        "/api/users**"
                    ).hasRole("ADMIN")
                    .anyRequest()
                    .fullyAuthenticated()
            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter::class.java)

        return httpSecurity.build()
    }
}