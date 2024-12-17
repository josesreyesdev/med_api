package com.jsrdev.med_api.domain.consultation

import com.jsrdev.med_api.domain.patient.Patient
import com.jsrdev.med_api.domain.physician.Physician
import java.time.LocalDateTime


data class ConsultationDetail(
    val id: Long?,
    val physicianId: Physician?,
    val patientId: Patient?,
    val date: LocalDateTime?
)