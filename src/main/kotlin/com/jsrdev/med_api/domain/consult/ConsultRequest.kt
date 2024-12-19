package com.jsrdev.med_api.domain.consult

import com.fasterxml.jackson.annotation.JsonAlias
import jakarta.validation.constraints.Future
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

data class ConsultRequest(
    @JsonAlias("idPatient", "id_patient")
    @field:NotNull(message = "idPatient must not be null.")
    val idPatient: Long,

    @JsonAlias("idPhysician", "id_physician")
    val idPhysician: Long?,

    @field:Future
    @field:NotNull(message = "date must not be null.")
    val date: LocalDateTime
)
