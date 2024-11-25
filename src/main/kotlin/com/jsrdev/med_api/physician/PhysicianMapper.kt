package com.jsrdev.med_api.physician

object PhysicianMapper {
    fun Physician.toResponse() =
        PhysicianResponse(
            id = this.id!!,
            name = this.name,
            specialty = this.specialty,
            document = this.document,
            email = this.email
        )

    fun UpdatePhysician.toPhysician(p: Physician) {
        p.name = this.name ?: p.name
        p.document = this.document ?: p.document
        this.addressData?.let { ad ->
            p.address.street = ad.street
            p.address.stateOrProvince = ad.stateOrProvince
            p.address.municipalityOrDelegation = ad.municipalityOrDelegation
            p.address.city = ad.city
            p.address.zipCode = ad.zipCode
            p.address.country = ad.country
            p.address.externalNumber = ad.externalNumber
            p.address.internalNumber = ad.internalNumber ?: p.address.internalNumber
            p.address.complement = ad.complement ?: p.address.complement
        }
    }
}