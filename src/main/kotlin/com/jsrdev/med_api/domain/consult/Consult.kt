package com.jsrdev.med_api.domain.consult

import com.jsrdev.med_api.domain.patient.Patient
import com.jsrdev.med_api.domain.physician.Physician
import jakarta.persistence.*
import java.time.LocalDateTime

@Table(name = "consultations")
@Entity(name = "Consult")
data class Consult(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "physician_id")
    val physicianId: Physician,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    val patientId: Patient,

    val date: LocalDateTime
)