package com.jsrdev.med_api.domain.consult

import java.time.LocalDateTime

data class ConsultResponse(
    val id: Long?,
    val physicianId: Long?,
    val patientId: Long?,
    val date: LocalDateTime?
)
