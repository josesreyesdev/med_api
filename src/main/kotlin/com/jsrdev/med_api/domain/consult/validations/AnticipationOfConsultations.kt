package com.jsrdev.med_api.domain.consult.validations

import com.jsrdev.med_api.domain.consult.ConsultRequest
import com.jsrdev.med_api.infra.exceptions.ValidateException
import org.springframework.stereotype.Component
import java.time.Duration
import java.time.LocalDateTime

@Component
class AnticipationOfConsultations : ConsultationValidator {

    /*
    * Anticipation of the consultation, it cannot be less than 30 minutes.
    * */
    override fun validate(data: ConsultRequest) {

        val currentTime: LocalDateTime = LocalDateTime.now()
        val consultationTime: LocalDateTime = data.date
        val differenceInMinutes = Duration.between(currentTime, consultationTime).toMinutes()

        if (differenceInMinutes < 30) {
            throw ValidateException("Anticipation of the consultation, it cannot be less than 30 minutes.")
        }
    }
}