package com.jsrdev.med_api.patient

import com.jsrdev.med_api.address.AddressData

data class PatientResponse(
    val id: Long,
    val name: String,
    val documentIdentity: String,
    val email: String,
    val address: AddressData
)
