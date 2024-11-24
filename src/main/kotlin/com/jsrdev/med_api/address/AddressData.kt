package com.jsrdev.med_api.address

import com.fasterxml.jackson.annotation.JsonAlias
import jakarta.validation.constraints.NotBlank

data class AddressData(
    @field:NotBlank(message = "Street must not be empty.")
    val street: String,

    @JsonAlias("stateOrProvince", "state_or_province")
    @field:NotBlank(message = "State Or Province must not be empty.")
    val stateOrProvince: String,

    @JsonAlias("municipalityOrDelegation", "municipality_or_delegation")
    @field:NotBlank(message = "Municipality Or Delegation must not be empty.")
    val municipalityOrDelegation: String,

    @field:NotBlank(message = "City must not be empty.")
    val city: String,

    @JsonAlias("zipCode", "zip_code")
    @field:NotBlank(message = "Zip Code must not be empty.")
    val zipCode: String,

    @field:NotBlank(message = "Country must not be empty.")
    val country: String,

    @JsonAlias("externalNumber", "external_number", "ext_number")
    @field:NotBlank(message = "Ext Number must not be empty.")
    val externalNumber: String,

    @JsonAlias("internalNumber", "internal_number", "int_number")
    val internalNumber: String? = null,

    val complement: String? = null
)
