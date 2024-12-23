package com.jsrdev.med_api.domain.consult

import com.jsrdev.med_api.domain.consult.cancel.CancellationRequest
import com.jsrdev.med_api.domain.consult.validations.ConsultationValidator
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
    private val consultRepository: ConsultRepository,
    private val validators: List<ConsultationValidator>
) {

    fun addConsult(data: ConsultRequest): Consult {

        /**
         *  validators => it´s part of strategy pattern and SOLID principles:
         *  1.- Single responsibility
         *  2.- Open-closed
         *  3.- Dependency Inversion
         */
        validators.forEach { v -> v.validate(data) }

        val patient = patientRepository.findByIdOrNull(data.idPatient)
            ?: throw IllegalArgumentException("Patient id: ${data.idPatient} not found in DB")

        val consult = Consult(
            physician = chooseAPhysician(data),
            patient = patient,
            date = data.date
        )

        return consultRepository.save(consult)
    }

    private fun chooseAPhysician(data: ConsultRequest): Physician {
        data.idPhysician?.let { id ->
            if (!physicianRepository.existsById(id)) {
                throw ValidateException("The specified physician (ID: $id) does not exist in the database.")
            }
            if (!physicianRepository.findActiveById(id)) {
                throw ValidateException("The specified physician (ID: $id) is not active in the database.")
            }
            return physicianRepository.findByIdOrNull(id)
                ?: throw ValidateException("The physician with ID: $id was not found in the database or is inactive")
        }

        val specialty = data.specialty
            ?: throw ValidateException("A specialty must be specified when a physician id is not provided.")

        return physicianRepository.chooseARandomPhysicianAvailableOnTheDate(specialty, data.date)
            ?: throw ValidateException("No available physicians were found for the specified specialty and date.")
    }

    fun cancellationOfConsultation(cancelData: CancellationRequest) {
        if (!consultRepository.existsById(cancelData.id)) {
            throw ValidateException("The specified consult (ID: ${cancelData.id}) does not exist in the database.")
        }

        val consult: Consult = consultRepository.getReferenceById(cancelData.id)

        consult.cancel(cancelData.cancellationReason)
    }
}