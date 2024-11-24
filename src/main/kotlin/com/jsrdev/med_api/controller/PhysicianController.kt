package com.jsrdev.med_api.controller

import com.jsrdev.med_api.physician.Physician
import com.jsrdev.med_api.physician.PhysicianRepository
import com.jsrdev.med_api.physician.PhysicianRequest
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/physicians")
class PhysicianController @Autowired constructor(
    private val physicianRepository: PhysicianRepository
) {

    @PostMapping
    @Transactional
    fun createPhysician(@Valid @RequestBody physicianRequest: PhysicianRequest) {
        physicianRepository.save(Physician(physicianRequest))
    }
}