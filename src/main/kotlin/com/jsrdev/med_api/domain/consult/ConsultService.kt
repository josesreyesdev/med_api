package com.jsrdev.med_api.domain.consult

import com.jsrdev.med_api.domain.patient.PatientRepository
import com.jsrdev.med_api.domain.physician.Physician
import com.jsrdev.med_api.domain.physician.PhysicianRepository
import com.jsrdev.med_api.infra.exceptions.ValidateException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ConsultService(
    private val physicianRepository: PhysicianRepository,
    private val patientRepository: PatientRepository,
    private val consultRepository: ConsultRepository
) {

    fun addConsult(data: ConsultRequest) {

        var physician: Physician? = null
        if (
            data.idPhysician != null &&
            !physicianRepository.existsById(data.idPhysician) &&
            physicianRepository.findPhysicianByActiveTrue(data.idPhysician)
        ) {

            physician = physicianRepository.findByIdOrNull(data.idPhysician)
                ?: throw ValidateException("Physician id: ${data.idPhysician} not found in DB or is not active")
        }


        val patient = patientRepository.findByIdOrNull(data.idPatient)
            ?: throw IllegalArgumentException("Patient id: ${data.idPatient} not found in DB")

        val consult = Consult(
            id = null,
            physicianId = physician ?: chooseAPhysician(data),
            patientId = patient,
            date = data.date
        )

        consultRepository.save(consult)
    }

    private fun chooseAPhysician(data: ConsultRequest): Physician {
        data.specialty?.let {
            return physicianRepository
                .chooseARandomPhysicianAvailableOnTheDate(data.specialty, data.date)
                ?: throw ValidateException("No physician available for this specialty")
        } ?: throw ValidateException(
            "It´s necessary to choose a specialty when you don't send a physician"
        )
    }
}