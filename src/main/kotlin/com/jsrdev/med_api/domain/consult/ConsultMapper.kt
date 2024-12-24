package com.jsrdev.med_api.domain.consult

object ConsultMapper {
    fun Consult.toResponse(): ConsultResponse =
        ConsultResponse(
            id = this.id,
            physicianId = this.physician.id,
            patientId = this.patient.id,
            date = this.date
        )
}