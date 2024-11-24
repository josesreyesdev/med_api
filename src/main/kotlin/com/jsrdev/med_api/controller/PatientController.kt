package com.jsrdev.med_api.controller

import com.jsrdev.med_api.patient.RegisterPatientData
import com.jsrdev.med_api.patient.Patient
import com.jsrdev.med_api.patient.PatientRespository
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/patients")
class PatientController @Autowired constructor(
    private val patientRespository: PatientRespository
) {
    @PostMapping
    @Transactional
    fun createPatient(@RequestBody @Valid registerPatient: RegisterPatientData)  {
        patientRespository.save(Patient(registerPatient))
    }
}