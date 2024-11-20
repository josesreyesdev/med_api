package com.jsrdev.med_api.physician

import com.jsrdev.med_api.address.AddressData
import com.jsrdev.med_api.service.SpecialtySerializer
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames


@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class RegisterPhysicianData(
    val name: String,
    val avatar: String,
    val email: String,
    val document: String,
    @JsonNames("phoneNumber", "phone_number")
    val phoneNumber: String,
    @Serializable(with = SpecialtySerializer::class)
    val specialty: Specialty,
    @JsonNames("address", "addressData", "address_data")
    val addressData: AddressData
)
