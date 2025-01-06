package com.jsrdev.med_api.domain.consult

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

interface ConsultRepository : JpaRepository<Consult, Long> {
    fun existsByPatientIdAndDateBetween(
        patientId: Long, firstSchedule: LocalDateTime, lastSchedule: LocalDateTime
    ): Boolean

    //fun existsByPhysicianIdAndDate(idPhysician: Long, date: LocalDateTime): Boolean

    fun existsByPhysicianIdAndDateAndCancellationReasonIsNull(idPhysician: Long, date: LocalDateTime): Boolean

    @Query("""
            SELECT new com.jsrdev.med_api.domain.consult.MonthlyConsultationReport(c.physician.name, COUNT(c))
            FROM Consult c
            WHERE YEAR(c.date) = :year
            AND MONTH(c.date) = :month
            GROUP BY c.physician.name
            """
    )
    fun findMonthlyConsultation(year: Int, month: Int, pageable: Pageable): Page<MonthlyConsultationReport>

}