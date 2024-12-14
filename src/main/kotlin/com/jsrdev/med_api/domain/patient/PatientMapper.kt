package com.jsrdev.med_api.domain.patient

import com.jsrdev.med_api.domain.address.AddressMapper.toResponse
import com.jsrdev.med_api.domain.address.AddressMapper.updateFrom

object PatientMapper {
    fun Patient.toResponse() =
        PatientResponse(
            id = this.id!!,
            name = this.name,
            avatar = this.avatar,
            documentIdentity = this.documentIdentity,
            email = this.email,
            address = this.address.toResponse()
        )

    fun Patient.updateFrom(update: UpdatePatient) {
        name = update.name ?: this.name
        avatar = update.avatar ?: this.avatar
        phoneNumber = update.phoneNumber ?: this.phoneNumber
        update.addressData?.let { address.updateFrom(update.addressData) }
    }
}