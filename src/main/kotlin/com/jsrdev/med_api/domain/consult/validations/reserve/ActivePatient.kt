package com.jsrdev.med_api.domain.consult.validations.reserve

import com.jsrdev.med_api.domain.consult.ConsultRequest
import com.jsrdev.med_api.domain.patient.PatientRepository
import com.jsrdev.med_api.infra.exceptions.IntegrityValidation
import org.springframework.stereotype.Component

@Component
class ActivePatient(
    private val patientRepository: PatientRepository
) : ConsultationValidator {

    /*
    * active patient
    * */
    override fun validate(data: ConsultRequest) {
        val isActivePatient: Boolean = patientRepository.findActiveById(data.idPatient)

        if (!isActivePatient) {
            throw IntegrityValidation("Consultation cannot be booked with inactive patient")
        }
    }
}