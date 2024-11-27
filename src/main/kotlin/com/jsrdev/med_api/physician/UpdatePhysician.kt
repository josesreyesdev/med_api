package com.jsrdev.med_api.physician

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.jsrdev.med_api.address.AddressData
import com.jsrdev.med_api.core.NonEmptyLongDeserializer
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern

data class UpdatePhysician(
    @JsonDeserialize(using = NonEmptyLongDeserializer::class)
    @field:NotNull(message = "Id must not be null.")
    val id: Long,

    val name: String?,

    val avatar: String?,

    @field:Pattern(regexp = "\\d{4,9}", message = "Document must contain between 4 and 9 digits.")
    val document: String?,

    @JsonAlias("address", "addressData", "address_data")
    @field:Valid
    val addressData: AddressData?
)
