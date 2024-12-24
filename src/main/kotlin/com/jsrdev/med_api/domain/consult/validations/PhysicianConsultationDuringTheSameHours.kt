package com.jsrdev.med_api.domain.consult.validations

import com.jsrdev.med_api.domain.consult.ConsultRepository
import com.jsrdev.med_api.domain.consult.ConsultRequest
import com.jsrdev.med_api.infra.exceptions.IntegrityValidation
import org.springframework.stereotype.Component

@Component
class PhysicianConsultationDuringTheSameHours(
    private val consultRepository: ConsultRepository
) : ConsultationValidator {

    /*
    * Physician with consultation
    * */
    override fun validate(data: ConsultRequest) {

        if (data.idPhysician == null) return

        val physicianWithConsultation: Boolean = consultRepository
            //.existsByPhysicianIdAndDate(data.idPhysician, data.date)
            .existsByPhysicianIdAndDateAndCancellationReasonIsNull(data.idPhysician, data.date)

        if (physicianWithConsultation) {
            throw IntegrityValidation("The physician already has a consultation on the same date and time.")
        }
    }
}