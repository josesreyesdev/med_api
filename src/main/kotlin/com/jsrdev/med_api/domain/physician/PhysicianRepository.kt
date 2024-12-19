package com.jsrdev.med_api.domain.physician

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDateTime

interface PhysicianRepository : JpaRepository<Physician, Long> {
    fun findByActiveTrue(pagination: Pageable): Page<Physician>

    @Query("""
        SELECT p FROM Physician p
        WHERE p.active = true
        AND p.specialty = : specialty 
        AND p.id NOT IN(
            SELECT c.physicianId.id FROM Consult c 
            WHERE c.date = :date
        )
        ORDER BY rand()
        LIMIT 1
    """)
    fun chooseARandomPhysicianAvailableOnTheDate(specialty: Specialty, date: LocalDateTime): Physician?

    @Query("""
        SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Physician p
        WHERE p.id = :idPhysician 
        AND p.active = true
    """)
    fun findPhysicianByActiveTrue(@Param("idPhysician") idPhysician: Long): Boolean

}