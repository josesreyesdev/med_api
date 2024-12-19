package com.jsrdev.med_api.domain.consult

object ConsultMapper {
    fun Consult.toResponse(): ConsultResponse =
        ConsultResponse(
            id = this.id,
            physicianId = this.physicianId.id,
            patientId = this.patientId.id,
            date = this.date
        )
}