package com.jsrdev.med_api.patient

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface PatientRepository : JpaRepository<Patient, Long> {
    fun findByActiveTrue(pagination: Pageable): Page<Patient>
}