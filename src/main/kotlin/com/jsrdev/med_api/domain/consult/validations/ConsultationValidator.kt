package com.jsrdev.med_api.domain.consult.validations

import com.jsrdev.med_api.domain.consult.ConsultRequest

interface ConsultationValidator {
    fun validate(data: ConsultRequest)
}