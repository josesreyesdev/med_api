package com.jsrdev.med_api.domain.patient

import com.jsrdev.med_api.domain.address.Address
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
    var name: String,
    var avatar: String?,
    val email: String,
    @Column(name = "document_identity")
    val documentIdentity: String,
    @Column(name = "phone_number")
    var phoneNumber: String,
    var active: Boolean,
    @Embedded
    val address: Address
) {
    constructor(patientData: PatientRequest) : this(
        id = null,
        name = patientData.name,
        avatar = patientData.avatar,
        email = patientData.email,
        documentIdentity = patientData.documentIdentity,
        phoneNumber = patientData.phoneNumber,
        active = true,
        address = Address(patientData.addressData)
    )

    fun deactivate() {
        this.active = false
    }
}
