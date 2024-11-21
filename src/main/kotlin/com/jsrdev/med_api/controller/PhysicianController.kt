package com.jsrdev.med_api.controller

import com.jsrdev.med_api.physician.Physician
import com.jsrdev.med_api.physician.PhysicianRepository
import com.jsrdev.med_api.physician.RegisterPhysicianData
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
    fun createPhysician(@RequestBody register: RegisterPhysicianData) {
        physicianRepository.save(Physician(register))
    }
}