package com.jsrdev.med_api.domain.consultation

object ConsultationMapper {
    fun ConsultationDetail.toResponse(): ConsultationResponse =
        ConsultationResponse(
            id = this.id,
            physicianId = this.physicianId,
            patientId = this.patientId,
            date = this.date
        )
}