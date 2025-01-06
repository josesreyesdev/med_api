package com.jsrdev.med_api.domain.consult

import java.time.LocalDateTime

data class DetailConsultationResponse(
    val id: Long,
    val idPatient: Long,
    val idPhysician: Long,
    val date: LocalDateTime
)
