package com.jsrdev.med_api.address

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@ExperimentalSerializationApi
@Serializable
data class AddressData(
    val street: String,
    @JsonNames("stateOrProvince", "state_or_province")
    val stateOrProvince: String,
    @JsonNames("municipalityOrDelegation", "municipality_or_delegation")
    val municipalityOrDelegation: String,
    val city: String,
    @JsonNames("zipCode", "zip_code")
    val zipCode: String,
    val country: String,
    @JsonNames("externalNumber", "external_number")
    val externalNumber: String,
    @JsonNames("internalNumber", "internal_number")
    val internalNumber: String?,
    val complement: String
)
