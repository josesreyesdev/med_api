package com.jsrdev.med_api.domain.consult.cancel

interface ConsultationCancellationValidator {

    fun validate(cancel: CancellationRequest)
}