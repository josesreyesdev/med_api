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
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/api/patients")
class PatientController @Autowired constructor(
    private val patientRepository: PatientRepository
) {
    @PostMapping
    @Transactional
    fun createPatient(
        @RequestBody @Valid patientRequest: PatientRequest,
        uriComponentsBuilder: UriComponentsBuilder
    ): ResponseEntity<PatientResponse> {
        val patient: Patient = patientRepository.save(Patient(patientRequest))

        val url = uriComponentsBuilder.path("/api/patients/{id}").buildAndExpand(patient.id).toUri()
        return ResponseEntity.created(url).body(patient.toResponse())
    }

    @GetMapping
    fun getPatients(
        @PageableDefault(size = 15) pagination: Pageable,
        assembler: PagedResourcesAssembler<PatientResponse>
    ): ResponseEntity<PagedModel<EntityModel<PatientResponse>>> {

        val patientsPage = patientRepository.findByActiveTrue(pagination)
            .map { it.toResponse() }

        return ResponseEntity.ok(assembler.toModel(patientsPage))
    }

    @GetMapping("/{id}")
    fun getPatient(@PathVariable id: Long): ResponseEntity<PatientResponse>{
        val patient: Patient ? = patientRepository.findByIdOrNull(id)

        return patient?.let {
            ResponseEntity.ok(patient.toResponse())
        } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found.")
    }

    @PutMapping
    @Transactional
    fun updatePatient(@Valid @RequestBody updatePatient: UpdatePatient): ResponseEntity<PatientResponse> {
        val patient: Patient? = patientRepository.findByIdOrNull(updatePatient.id)

        return patient?.let {
            patient.updateFrom(updatePatient)
            ResponseEntity.ok(patient.toResponse())
        } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found.")
    }

    @DeleteMapping("/{id}")
    @Transactional
    fun deletePatient(@PathVariable id: Long): ResponseEntity<Boolean>? {
        val patient: Patient? = patientRepository.findByIdOrNull(id)

        return patient?.let {
            patient.deactivate()
            ResponseEntity.noContent().build()
        } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found.")
    }
}