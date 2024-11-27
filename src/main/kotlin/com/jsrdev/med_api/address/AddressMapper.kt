package com.jsrdev.med_api.address

object AddressMapper {
    fun Address.updateFrom(ad: AddressData) {
        street = ad.street
        stateOrProvince = ad.stateOrProvince
        municipalityOrDelegation = ad.municipalityOrDelegation
        city = ad.city
        zipCode = ad.zipCode
        country = ad.country
        externalNumber = ad.externalNumber
        internalNumber = ad.internalNumber ?: this.internalNumber
        complement = ad.complement ?: this.complement
    }
}