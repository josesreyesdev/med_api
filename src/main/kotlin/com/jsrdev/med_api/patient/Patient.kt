package com.jsrdev.med_api.patient

import com.jsrdev.med_api.address.Address
import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.Id
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Column
import jakarta.persistence.Embedded

@Entity(name = "Patient")
@Table(name = "patients")
data class Patient(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    val name: String,
    val avatar: String?,
    val email: String,
    @Column(name = "document_identity")
    val documentIdentity: String,
    @Column(name = "phone_number")
    val phoneNumber: String,
    @Embedded
    val address: Address
) {
    constructor(patientData: RegisterPatientData) : this(
        id = null,
        name = patientData.name,
        avatar = patientData.avatar,
        email = patientData.email,
        documentIdentity = patientData.documentIdentity,
        phoneNumber = patientData.phoneNumber,
        address = Address(patientData.addressData)
    )
}
