package com.jsrdev.med_api.domain.patient

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface PatientRepository : JpaRepository<Patient, Long> {
    fun findByActiveTrue(pagination: Pageable): Page<Patient>

    @Query("""
                SELECT p.active FROM Patient p
                WHERE p.id = :idPatient
            """)
    fun findActiveById(@Param("idPatient") idPatient: Long): Boolean
}