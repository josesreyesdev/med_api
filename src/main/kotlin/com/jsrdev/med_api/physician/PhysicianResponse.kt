package com.jsrdev.med_api.physician

data class PhysicianResponse(
    val id: Long,
    val name: String,
    val specialty: Specialty,
    val document: String,
    val email: String
)
