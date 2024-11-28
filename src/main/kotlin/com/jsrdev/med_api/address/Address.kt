package com.jsrdev.med_api.address

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class Address(
    var street: String,
    @Column(name = "state_or_province")
    var stateOrProvince: String,
    @Column(name = "municipality_or_delegation")
    var municipalityOrDelegation: String,
    var city: String,
    @Column(name = "zip_code")
    var zipCode: String,
    var country: String,
    @Column(name = "external_number")
    var externalNumber: String,
    @Column(name = "internal_number")
    var internalNumber: String?,
    var complement: String?
) {
    constructor(addressData: AddressData) : this(
        street = addressData.street,
        stateOrProvince = addressData.stateOrProvince,
        municipalityOrDelegation = addressData.municipalityOrDelegation,
        city = addressData.city,
        zipCode = addressData.zipCode,
        country = addressData.country,
        externalNumber = addressData.externalNumber,
        internalNumber = addressData.internalNumber,
        complement = addressData.complement
    )
}
