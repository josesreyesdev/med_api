package com.jsrdev.med_api.domain.consult.validations

import com.jsrdev.med_api.domain.consult.ConsultRepository
import com.jsrdev.med_api.domain.consult.ConsultRequest
import com.jsrdev.med_api.infra.exceptions.IntegrityValidation
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class PatientConsultationDuringTheSameDay(
    private val consultRepository: ConsultRepository
) : ConsultationValidator {
    /*
    * Do not allow more than one consultation to be booked
    * on the same day for the same patient.
    * */

    override fun validate(data: ConsultRequest) {
        val firstSchedule: LocalDateTime = data.date.withHour(7)
        val lastSchedule: LocalDateTime = data.date.withHour(18)

        val patientWithConsultation = consultRepository
            .existsByPatientIdAndDateBetween(data.idPatient, firstSchedule, lastSchedule)

        if (patientWithConsultation) {
            throw IntegrityValidation("The patient already has an active consultation for this day")
        }
    }
}