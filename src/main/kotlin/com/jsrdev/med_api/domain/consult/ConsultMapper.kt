package com.jsrdev.med_api.domain.consult

object ConsultMapper {
    fun Consult.toResponse(): ConsultResponse =
        ConsultResponse(
            id = this.id,
            physicianId = this.physician.id,
            patientId = this.patient.id,
            date = this.date
        )

    fun Consult.toDetailResponse(): DetailConsultationResponse =
        DetailConsultationResponse(
            id = this.id ?: 0,
            idPatient = this.patient.id ?: 0,
            idPhysician = this.physician.id ?: 0,
            date = this.date
        )
}