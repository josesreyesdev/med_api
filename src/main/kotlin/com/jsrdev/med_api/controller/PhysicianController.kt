package com.jsrdev.med_api.controller

import com.jsrdev.med_api.domain.physician.*
import com.jsrdev.med_api.domain.physician.PhysicianMapper.toResponse
import com.jsrdev.med_api.domain.physician.PhysicianMapper.updateFrom
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.data.web.PageableDefault
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.PagedModel
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/api/physicians")
@SecurityRequirement(name = "bearer-key")
class PhysicianController @Autowired constructor(
    private val physicianRepository: PhysicianRepository
) {
    @PostMapping
    @Transactional
    fun createPhysician(
        @Valid @RequestBody physicianRequest: PhysicianRequest,
        uriComponentsBuilder: UriComponentsBuilder
    ): ResponseEntity<PhysicianResponse> {
        val physician: Physician = physicianRepository.save(Physician(physicianRequest))

        val url = uriComponentsBuilder.path("/api/physicians/{id}").buildAndExpand(physician.id).toUri()
        return ResponseEntity.created(url).body(physician.toResponse())// 201 - url
    }

    @GetMapping
    fun getPhysicians(
        @PageableDefault(size = 15) pagination: Pageable,
        assembler: PagedResourcesAssembler<PhysicianResponse>
    ): ResponseEntity<PagedModel<EntityModel<PhysicianResponse>>> {
        val physiciansPage = physicianRepository.findByActiveTrue(pagination)
            .map { it.toResponse() }

        return ResponseEntity.ok(assembler.toModel(physiciansPage))
    }

    @GetMapping("/{id}")
    fun getPhysician(@PathVariable id: Long): ResponseEntity<PhysicianResponse> {
        val physician = physicianRepository.findByIdOrNull(id)

        return physician?.let {
            ResponseEntity.ok(physician.toResponse())
        } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Physician not found.")
    }

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