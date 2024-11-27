package com.jsrdev.med_api.controller

import com.jsrdev.med_api.patient.*
import com.jsrdev.med_api.patient.PatientMapper.toResponse
import com.jsrdev.med_api.patient.PatientMapper.updateFrom
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.data.web.PageableDefault
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.PagedModel
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/patients")
class PatientController @Autowired constructor(
    private val patientRepository: PatientRepository
) {
    @PostMapping
    @Transactional
    fun createPatient(@RequestBody @Valid patientRequest: PatientRequest) {
        patientRepository.save(Patient(patientRequest))
    }

    @GetMapping
    fun getPatients(
        @PageableDefault(size = 15) pagination: Pageable,
        assembler: PagedResourcesAssembler<PatientResponse>
    ): PagedModel<EntityModel<PatientResponse>> {

        val patientsPage = patientRepository.findAll(pagination)
            .map { it.toResponse() }

        return assembler.toModel(patientsPage)
    }

    @PutMapping
    @Transactional
    fun updatePatient(@Valid @RequestBody updatePatient: UpdatePatient) {
        val patient: Patient = patientRepository.findByIdOrNull(updatePatient.id)
            ?: throw IllegalArgumentException("Physician not found with this id: ${updatePatient.id}")

        patient.updateFrom(updatePatient)
    }
}