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
    val id: Long? = null,
    val name: String,
    val avatar: String?,
    val email: String,
    val document: String,
    @Column(name = "phone_number")
    val phoneNumber: String,
    @Embedded
    val address: Address
)
