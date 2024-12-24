package com.jsrdev.med_api.domain.physician

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDateTime

interface PhysicianRepository : JpaRepository<Physician, Long> {
    fun findByActiveTrue(pagination: Pageable): Page<Physician>

    /*@Query(
        """
        SELECT p FROM Physician p
        WHERE p.active = true
        AND p.specialty = :specialty 
        AND p.id NOT IN(
            SELECT c.physician.id FROM Consult c 
            WHERE c.date = :date
        )
        ORDER BY rand()
        LIMIT 1
    """
    ) */
    @Query("""
            SELECT p FROM Physician p
            WHERE p.active = true
            AND p.specialty = :specialty
            AND 
            p.id NOT IN(
                SELECT c.physician.id FROM Consult c
                WHERE c.date = :date
                AND c.cancellationReason IS NULL 
            )
            ORDER BY rand()
            LIMIT 1
""")
    fun chooseARandomPhysicianAvailableOnTheDate(specialty: Specialty, date: LocalDateTime): Physician?

    @Query("""
                SELECT p.active FROM Physician p
                WHERE p.id = :idPhysician
            """)
    fun findActiveById(@Param("idPhysician") idPhysician: Long): Boolean

}