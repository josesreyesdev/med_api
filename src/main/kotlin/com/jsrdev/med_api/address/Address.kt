package com.jsrdev.med_api.address

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class Address(
    val street: String,
    @Column(name = "state_or_province")
    val stateOrProvince: String,
    @Column(name ="municipality_or_delegation")
    val municipalityOrDelegation: String,
    val city: String,
    @Column(name = "zip_code")
    val zipCode: String,
    val country: String,
    @Column(name = "external_number")
    val externalNumber: String,
    @Column(name = "internal_number")
    val internalNumber: String,
    val complement: String
)
