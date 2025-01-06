package com.jsrdev.med_api.domain.consult

import com.jsrdev.med_api.domain.consult.ConsultMapper.toDetailResponse
import com.jsrdev.med_api.domain.consult.ConsultMapper.toResponse
import com.jsrdev.med_api.domain.consult.validations.cancel.CancellationRequest
import com.jsrdev.med_api.domain.consult.validations.cancel.ConsultationCancellationValidator
import com.jsrdev.med_api.domain.consult.validations.reserve.ConsultationValidator
import com.jsrdev.med_api.domain.patient.PatientRepository
import com.jsrdev.med_api.domain.physician.Physician
import com.jsrdev.med_api.domain.physician.PhysicianRepository
import com.jsrdev.med_api.infra.exceptions.IntegrityValidation
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ConsultService(
    private val physicianRepository: PhysicianRepository,
    private val patientRepository: PatientRepository,
    private val consultRepository: ConsultRepository,
    private val validators: List<ConsultationValidator>,
    private val cancelValidators: List<ConsultationCancellationValidator>
) {

    fun addConsult(data: ConsultRequest): ConsultResponse {

        if (!patientRepository.existsById(data.idPatient)) {
            throw IntegrityValidation("The specified patient (ID: ${data.idPatient}) does not exist in the database.")
        }

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

        consultRepository.save(consult)

        return consult.toResponse()
    }

    private fun chooseAPhysician(data: ConsultRequest): Physician {
        data.idPhysician?.let { id ->
            if (!physicianRepository.existsById(id)) {
                throw IntegrityValidation("The specified physician (ID: $id) does not exist in the database.")
            }
            if (!physicianRepository.findActiveById(id)) {
                throw IntegrityValidation("The specified physician (ID: $id) is not active in the database.")
            }
            return physicianRepository.findByIdOrNull(id)
                ?: throw IntegrityValidation("The physician with ID: $id was not found in the database")
        }

        val specialty = data.specialty
            ?: throw IntegrityValidation("A specialty must be specified when a physician id is not provided.")

        return physicianRepository.chooseARandomPhysicianAvailableOnTheDate(specialty, data.date)
            ?: throw IntegrityValidation("No available physicians were found for the specified specialty and date.")
    }

    fun cancel(cancelData: CancellationRequest) {
        if (!consultRepository.existsById(cancelData.id)) {
            throw IntegrityValidation("The specified consult (ID: ${cancelData.id}) does not exist in the database.")
        }

        cancelValidators.forEach { v -> v.validate(cancelData) }

        val consult: Consult = consultRepository.findByIdOrNull(cancelData.id)
            ?: throw IntegrityValidation("The consultation with ID: ${cancelData.id} was not found in the database")

        consult.cancel(cancelData.cancellationReason)
    }

    fun consultation(pagination: Pageable): Page<DetailConsultationResponse> {
        val consultationsPage = consultRepository.findAll(pagination)

        // filter current or future consultations
        val filteredConsultations = consultationsPage.content.filter {
            it.date.isAfter(LocalDateTime.now()) || it.date.isEqual(LocalDateTime.now())
        }

        // create new filtered page
        val filteredPage = PageImpl(filteredConsultations, pagination, consultationsPage.totalElements)

        return filteredPage.map { it.toDetailResponse() }
    }
}