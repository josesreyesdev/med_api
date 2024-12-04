package com.jsrdev.med_api.domain.patient

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.jsrdev.med_api.domain.address.AddressData
import com.jsrdev.med_api.domain.core.NonEmptyLongDeserializer
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull

data class UpdatePatient(
    @JsonDeserialize(using = NonEmptyLongDeserializer::class)
    @field:NotNull(message = "Id must not be null.")
    val id: Long,

    val name: String?,

    val avatar: String?,

    @JsonAlias("phone_number", "phoneNumber")
    val phoneNumber: String?,

    @JsonAlias("address", "addressData", "address_data")
    @field:Valid
    val addressData: AddressData?
)
