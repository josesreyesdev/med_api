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
        data.idPhysician?.let {
            physician = physicianRepository.findByIdOrNull(data.idPhysician)
                ?: throw ValidateException("Physician id: ${data.idPhysician} not found")
        }

        val patient = patientRepository.findByIdOrNull(data.idPatient)
            ?: throw IllegalArgumentException("Patient id: ${data.idPatient} not found")

        val consult = Consult(
            id = null,
            physicianId = physician ?: chooseAPhysician(data),
            patientId = patient,
            date = data.date
        )

        consultRepository.save(consult)
    }

    private fun chooseAPhysician(data: ConsultRequest): Physician {
        TODO("Not yet implemented")
    }
}