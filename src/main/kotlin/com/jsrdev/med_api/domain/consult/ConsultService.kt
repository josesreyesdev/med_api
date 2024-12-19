package com.jsrdev.med_api.domain.consult

import com.jsrdev.med_api.domain.patient.PatientRepository
import com.jsrdev.med_api.domain.physician.PhysicianRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ConsultService(
    private val physicianRepository: PhysicianRepository,
    private val patientRepository: PatientRepository,
    private val consultRepository: ConsultRepository
) {

    fun addConsult(data: ConsultRequest) {

        val physician = physicianRepository.findByIdOrNull(data.idPhysician)
            ?: throw IllegalArgumentException("Physician with id: ${data.idPhysician} not found")
        val patient = patientRepository.findByIdOrNull(data.idPatient)
            ?: throw IllegalArgumentException("Patient with id: ${data.idPatient} not found")

        val consult = Consult(
            id = null,
            physicianId = physician,
            patientId = patient,
            date = data.date
        )

        consultRepository.save(consult)
    }
}