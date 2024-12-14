package com.jsrdev.med_api.domain.patient

import com.jsrdev.med_api.domain.address.AddressData

data class PatientResponse(
    val id: Long,
    val name: String,
    val avatar: String?,
    val documentIdentity: String,
    val email: String,
    val address: AddressData
)
