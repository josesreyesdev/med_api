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

    fun Address.toResponse(): AddressData =
        AddressData(
            street = this.street,
            stateOrProvince = this.stateOrProvince,
            municipalityOrDelegation = this.municipalityOrDelegation,
            city = this.city,
            zipCode = this.zipCode,
            country = this.country,
            externalNumber = this.externalNumber,
            internalNumber = this.internalNumber,
            complement = this.complement
        )
}