package com.jsrdev.med_api.physician

import com.jsrdev.med_api.address.AddressData

data class PhysicianResponse(
    val id: Long,
    val name: String,
    val specialty: Specialty,
    val document: String,
    val email: String,
    val address: AddressData
)
