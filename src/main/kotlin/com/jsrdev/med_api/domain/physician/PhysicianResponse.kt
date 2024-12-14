package com.jsrdev.med_api.domain.physician

import com.jsrdev.med_api.domain.address.AddressData

data class PhysicianResponse(
    val id: Long,
    val name: String,
    val avatar: String?,
    val specialty: Specialty,
    val document: String,
    val email: String,
    val address: AddressData
)
