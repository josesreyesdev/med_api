package com.jsrdev.med_api.domain.consult.validations

import com.jsrdev.med_api.domain.consult.ConsultRepository
import com.jsrdev.med_api.domain.consult.ConsultRequest
import com.jsrdev.med_api.infra.exceptions.ValidateException
import java.time.LocalDateTime

class PatientConsultationDuringTheSameDay(private val consultRepository: ConsultRepository) {
    /*
    * Do not allow more than one consultation to be booked
    * on the same day for the same patient.
    * */

    fun validate(data: ConsultRequest) {
        val firstSchedule: LocalDateTime = data.date.withHour(7)
        val lastSchedule: LocalDateTime = data.date.withHour(18)

        val patientWithConsultation = consultRepository
            .existsByPatientIdAndDateBetween(data.idPatient, firstSchedule, lastSchedule)

        if (patientWithConsultation) {
            throw ValidateException("The patient already has an active consultation for this day")
        }
    }
}