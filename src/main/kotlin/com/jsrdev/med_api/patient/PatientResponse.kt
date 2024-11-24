package com.jsrdev.med_api.patient

data class PatientResponse(
    val id: Long,
    val name: String,
    val documentIdentity: String,
    val email: String
)
