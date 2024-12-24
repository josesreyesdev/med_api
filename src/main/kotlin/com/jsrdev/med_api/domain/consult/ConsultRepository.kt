package com.jsrdev.med_api.domain.consult

import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface ConsultRepository : JpaRepository<Consult, Long> {
    fun existsByPatientIdAndDateBetween(
        patientId: Long, firstSchedule: LocalDateTime, lastSchedule: LocalDateTime
    ): Boolean

    //fun existsByPhysicianIdAndDate(idPhysician: Long, date: LocalDateTime): Boolean

    fun existsByPhysicianIdAndDateAndCancellationReasonIsNull(idPhysician: Long, date: LocalDateTime): Boolean

}