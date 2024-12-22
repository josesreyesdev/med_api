package com.jsrdev.med_api.domain.consult.validations

import com.jsrdev.med_api.domain.consult.ConsultRequest
import com.jsrdev.med_api.infra.exceptions.ValidateException
import java.time.DayOfWeek
import java.time.LocalDateTime

class HoursOfOperation {

    /*
    * verify that it is not Sunday and that you can only consult
    * from Monday to Saturday from 7 am to 7 pm.
    * */
    fun validate(data: ConsultRequest) {
        val consultDate: LocalDateTime = data.date

        val sunday: Boolean = consultDate.dayOfWeek.equals(DayOfWeek.SUNDAY)
        val openingTime = data.date.hour < 7
        val closingTime = data.date.hour > 18

        if (sunday || openingTime || closingTime)  {
            throw ValidateException("Opening hours are Monday to Saturday from 07:00 to 18:00 hours")
        }
    }
}