package com.jsrdev.med_api.domain.consult.validations

import com.jsrdev.med_api.domain.consult.ConsultRequest
import com.jsrdev.med_api.domain.physician.PhysicianRepository
import com.jsrdev.med_api.infra.exceptions.ValidateException

class ActivePhysician(private val physicianRepository: PhysicianRepository) {

    fun validate(data: ConsultRequest) {

        if (data.idPhysician == null) return

        val isActivePhysician: Boolean = physicianRepository.findActiveById(data.idPhysician)

        if (!isActivePhysician) {
            throw ValidateException("Consultation cannot be booked with inactive physician")
        }

    }
}