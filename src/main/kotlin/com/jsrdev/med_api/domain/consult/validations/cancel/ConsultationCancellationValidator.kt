package com.jsrdev.med_api.domain.consult.validations.cancel

interface ConsultationCancellationValidator {

    fun validate(cancel: CancellationRequest)
}