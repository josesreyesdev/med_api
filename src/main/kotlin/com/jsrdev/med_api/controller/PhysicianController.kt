package com.jsrdev.med_api.controller

import com.jsrdev.med_api.physician.*
import com.jsrdev.med_api.physician.PhysicianMapper.toResponse
import com.jsrdev.med_api.physician.PhysicianMapper.updateFrom
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

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
        physicianRepository.findByActiveTrue(pagination)
            .map { it.toResponse() }

    @PutMapping
    @Transactional
    fun updatePhysician(@Valid @RequestBody updatePhysician: UpdatePhysician): ResponseEntity<PhysicianResponse> {
        val physician: Physician? = physicianRepository.findByIdOrNull(updatePhysician.id)

        return physician?.let {
            physician.updateFrom(updatePhysician)
            ResponseEntity.ok(physician.toResponse()) //200
        } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Physician not found.")
    }

    @DeleteMapping("/{id}")
    @Transactional
    fun deletePhysician(@PathVariable id: Long): ResponseEntity<Boolean>? {
        val physician: Physician? = physicianRepository.findByIdOrNull(id)

        return physician?.let {
            physician.deactivate()
            ResponseEntity.noContent().build() //204
        } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Physician not found.")
    }
}