package com.jsrdev.med_api.controller

import com.jsrdev.med_api.patient.PatientRequest
import com.jsrdev.med_api.patient.Patient
import com.jsrdev.med_api.patient.PatientMapper.toResponse
import com.jsrdev.med_api.patient.PatientResponse
import com.jsrdev.med_api.patient.PatientRespository
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/patients")
class PatientController @Autowired constructor(
    private val patientRepository: PatientRespository
) {
    @PostMapping
    @Transactional
    fun createPatient(@RequestBody @Valid patientRequest: PatientRequest)  {
        patientRepository.save(Patient(patientRequest))
    }

    @GetMapping
    fun getPatients(): List<PatientResponse> =
        patientRepository.findAll()
            .map { it.toResponse() }
}