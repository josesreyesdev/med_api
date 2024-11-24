package com.jsrdev.med_api.controller

import com.jsrdev.med_api.patient.Patient
import com.jsrdev.med_api.patient.PatientMapper.toResponse
import com.jsrdev.med_api.patient.PatientRepository
import com.jsrdev.med_api.patient.PatientRequest
import com.jsrdev.med_api.patient.PatientResponse
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
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
        pagination: Pageable,
        assembler: PagedResourcesAssembler<PatientResponse>
    ): PagedModel<EntityModel<PatientResponse>> {

        val patientsPage = patientRepository.findAll(pagination)
            .map { it.toResponse() }

        return assembler.toModel(patientsPage)
    }
}