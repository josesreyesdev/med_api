package com.jsrdev.med_api.domain.consult.cancel

import com.jsrdev.med_api.domain.consult.Consult
import com.jsrdev.med_api.domain.consult.ConsultRepository
import com.jsrdev.med_api.infra.exceptions.IntegrityValidation
import org.springframework.stereotype.Component
import java.time.Duration
import java.time.LocalDateTime

@Component//("AnticipationConsultationValidator") change name of the class if exist an error of duplicates
class AnticipationToConsultationCancel(
    private val consultRepository: ConsultRepository
) : ConsultationCancellationValidator {

    /*
    *  cancel consultation with 24 hours notice
    *  */

    override fun validate(cancel: CancellationRequest) {

        val consult: Consult = consultRepository.getReferenceById(cancel.id)
        val currentTime = LocalDateTime.now()
        val differenceInHours =  Duration.between(currentTime, consult.date).toHours()

        if (differenceInHours < 24) {
            throw IntegrityValidation("The consultation can only be cancelled with a minimum notice of 24 hours.")
        }
    }
}