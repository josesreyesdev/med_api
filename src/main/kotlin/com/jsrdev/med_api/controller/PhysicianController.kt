package com.jsrdev.med_api.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/physicians")
class PhysicianController {

    @PostMapping
    fun createPhysician(@RequestBody physicianRequest: String) {
        println("Correctly Physician Request: $physicianRequest")
    }
}