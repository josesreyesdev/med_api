package com.jsrdev.med_api.physician

import com.jsrdev.med_api.address.AddressMapper.updateFrom

object PhysicianMapper {
    fun Physician.toResponse() =
        PhysicianResponse(
            id = this.id!!,
            name = this.name,
            specialty = this.specialty,
            document = this.document,
            email = this.email
        )

    fun Physician.updateFrom(p: UpdatePhysician) {
        this.name = p.name ?: this.name
        this.avatar = p.avatar ?: this.avatar
        this.document = p.document ?: this.document
        p.addressData?.let { this.address.updateFrom(p.addressData) }
    }
}