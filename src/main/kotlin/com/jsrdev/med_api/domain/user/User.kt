package com.jsrdev.med_api.domain.user

import jakarta.persistence.*

@Table(name = "users")
@Entity(name = "User")
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    val login: String,
    val pass: String,
    @Enumerated(EnumType.STRING)
    val role: Role
)
