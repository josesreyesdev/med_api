package com.jsrdev.med_api.controller

import com.jsrdev.med_api.physician.*
import com.jsrdev.med_api.physician.PhysicianMapper.toPhysician
import com.jsrdev.med_api.physician.PhysicianMapper.toResponse
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*

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

    @PutMapping
    @Transactional
    fun updatePhysician(@Valid @RequestBody updatePhysician: UpdatePhysician) {
        val physician: Physician = physicianRepository.findByIdOrNull(updatePhysician.id)
            ?: throw IllegalArgumentException("Physician not found with this id: ${updatePhysician.id}")

        updatePhysician.toPhysician(physician)
    }
}