package com.jsrdev.med_api.domain.consult.validations

import com.jsrdev.med_api.domain.consult.ConsultRequest
import com.jsrdev.med_api.domain.patient.PatientRepository
import com.jsrdev.med_api.infra.exceptions.ValidateException

class ActivePatient(private val patientRepository: PatientRepository) {

    /*
    * active patient
    * */
    fun validate(data: ConsultRequest) {
        val isActivePatient: Boolean = patientRepository.findActiveById(data.idPatient)

        if (!isActivePatient) {
            throw ValidateException("Consultation cannot be booked with inactive patient")
        }
    }
}