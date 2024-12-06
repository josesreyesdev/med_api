package com.jsrdev.med_api.infra.security

import com.jsrdev.med_api.domain.user.UserRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

typealias ApplicationUser = com.jsrdev.med_api.domain.user.User

@Service
class AuthService(private val userRepository: UserRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails =
        userRepository.findByLogin(username)
            ?.mapToUserDetails()
            ?: throw UsernameNotFoundException("Username not found")

    private fun ApplicationUser.mapToUserDetails(): UserDetails =
        User.builder()
            .username(this.login)
            .password(this.pass)
            .roles(this.role.name)
            .build()
}