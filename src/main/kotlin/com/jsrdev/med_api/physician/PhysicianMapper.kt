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
}