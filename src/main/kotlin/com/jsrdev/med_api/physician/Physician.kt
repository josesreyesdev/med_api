package com.jsrdev.med_api.physician

import com.jsrdev.med_api.address.Address
import jakarta.persistence.*

@Table(name = "physicians")
@Entity(name = "Physician")
data class Physician(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    var name: String,
    var avatar: String?,
    val email: String,
    var document: String,
    @Column(name = "phone_number")
    val phoneNumber: String,
    @Enumerated(EnumType.STRING)
    val specialty: Specialty,
    var active: Boolean,
    @Embedded
    val address: Address
) {
    constructor(registerData: PhysicianRequest) : this(
        id = null,
        name = registerData.name,
        avatar = registerData.avatar,
        email = registerData.email,
        document = registerData.document,
        phoneNumber = registerData.phoneNumber,
        specialty = registerData.specialty,
        active = true,
        address = Address(registerData.addressData)
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Physician) return false
        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    fun deactivate() {
        this.active = false
    }
}
