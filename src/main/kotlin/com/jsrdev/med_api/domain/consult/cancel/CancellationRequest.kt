package com.jsrdev.med_api.domain.consult.cancel

import com.fasterxml.jackson.annotation.JsonAlias
import jakarta.validation.constraints.NotNull

data class CancellationRequest(

    @field:NotNull(message = "Id must not be null.")
    val id: Long,

    @JsonAlias("cancellationReason", "cancellation_reason")
    @field:NotNull(message = "cancellationReason must not be null.")
    val cancellationReason: CancellationReason,
)
