package com.jsrdev.med_api.patient

object PatientMapper {
    fun Patient.toResponse() =
        PatientResponse(
            id = this.id!!,
            name = this.name,
            documentIdentity = this.documentIdentity,
            email = this.email
        )
}