package com.jsrdev.med_api.controller

import com.jsrdev.med_api.physician.Physician
import com.jsrdev.med_api.physician.PhysicianMapper.toResponse
import com.jsrdev.med_api.physician.PhysicianRepository
import com.jsrdev.med_api.physician.PhysicianRequest
import com.jsrdev.med_api.physician.PhysicianResponse
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.GetMapping
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

    @GetMapping
    fun getPhysicians(@PageableDefault(size = 15) pagination: Pageable): Page<PhysicianResponse> =
        physicianRepository.findAll(pagination)
            .map { it.toResponse() }
}