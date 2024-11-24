package com.jsrdev.med_api.patient

import com.fasterxml.jackson.annotation.JsonAlias
import com.jsrdev.med_api.address.AddressData
import jakarta.validation.Valid
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class RegisterPatientData(
    @field:NotBlank(message = "Name must not be empty.")
    val name: String,

    val avatar: String?,

    @field:NotBlank(message = "Email must not be empty.")
    @field:Email(message = "Email must be a valid format.")
    val email: String,

    @JsonAlias("document_identity", "documentIdentity", "document")
    @field:NotBlank(message = "Document identity must not be empty.")
    val documentIdentity: String,

    @JsonAlias("phone_number", "phoneNumber")
    @field:NotBlank(message = "Phone number must not be empty.")
    val phoneNumber: String,

    @JsonAlias("address", "addressData", "address_data")
    @field:NotNull(message = "Address data must not be null.")
    @field:Valid
    val addressData: AddressData
)
