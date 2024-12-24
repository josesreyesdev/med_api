package com.jsrdev.med_api.domain.consult.validations.reserve

import com.jsrdev.med_api.domain.consult.ConsultRequest
import com.jsrdev.med_api.infra.exceptions.IntegrityValidation
import org.springframework.stereotype.Component
import java.time.DayOfWeek
import java.time.LocalDateTime

@Component
class HoursOfOperation : ConsultationValidator {

    /*
    * verify that it is not Sunday and that you can only consult
    * from Monday to Saturday from 7 am to 7 pm.
    * */
    override fun validate(data: ConsultRequest) {
        val consultDate: LocalDateTime = data.date

        val sunday: Boolean = consultDate.dayOfWeek.equals(DayOfWeek.SUNDAY)
        val openingTime = data.date.hour < 7
        val closingTime = data.date.hour > 18

        if (sunday || openingTime || closingTime)  {
            throw IntegrityValidation("Opening hours are Monday to Saturday from 07:00 to 18:00 hours")
        }
    }
}