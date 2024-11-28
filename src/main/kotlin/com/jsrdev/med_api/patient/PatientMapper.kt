package com.jsrdev.med_api.patient

import com.jsrdev.med_api.address.AddressMapper.updateFrom

object PatientMapper {
    fun Patient.toResponse() =
        PatientResponse(
            id = this.id!!,
            name = this.name,
            documentIdentity = this.documentIdentity,
            email = this.email
        )

    fun Patient.updateFrom(update: UpdatePatient) {
        name = update.name ?: this.name
        avatar = update.avatar ?: this.avatar
        phoneNumber = update.phoneNumber ?: this.phoneNumber
        update.addressData?.let { address.updateFrom(update.addressData) }
    }
}