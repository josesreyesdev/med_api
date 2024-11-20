package com.jsrdev.med_api.controller

import com.jsrdev.med_api.physician.RegisterPhysicianData
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/physicians")
class PhysicianController {

    @PostMapping
    fun createPhysician(@RequestBody register: RegisterPhysicianData) {
        println("Correctly Physician Request: $register")
    }
}