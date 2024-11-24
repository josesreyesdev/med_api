package com.jsrdev.med_api.physician

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.jsrdev.med_api.address.AddressData
import jakarta.validation.Valid
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern

data class PhysicianRequest(
    @field:NotBlank(message = "Name must not be empty.")
    val name: String,

    val avatar: String?,

    @field:NotBlank(message = "Email must not be empty.")
    @field:Email(message = "Email must be a valid format.")
    val email: String,

    @field:NotBlank(message = "Document must not be empty.")
    @field:Pattern(regexp = "\\d{4,9}", message = "Document must contain between 4 and 9 digits.")
    val document: String,

    @JsonAlias("phoneNumber", "phone_number")
    @field:NotBlank(message = "Phone number must not be empty.")
    val phoneNumber: String,

    @JsonDeserialize(using = SpecialtyDeserializer::class)
    @field:NotNull(message = "Specialty must not be null.")
    val specialty: Specialty,

    @JsonAlias("address", "addressData", "address_data")
    @field:NotNull(message = "Address data must not be null.")
    @field:Valid
    val addressData: AddressData
)

