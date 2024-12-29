package com.jsrdev.med_api.controller

import com.jsrdev.med_api.domain.consult.ConsultRequest
import com.jsrdev.med_api.domain.consult.ConsultResponse
import com.jsrdev.med_api.domain.consult.ConsultService
import com.jsrdev.med_api.domain.consult.validations.cancel.CancellationRequest
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/consultation")
@SecurityRequirement(name = "bearer-key")
class ConsultationController(
    private val consultService: ConsultService
) {

    @PostMapping
    @Transactional
    fun add(@RequestBody @Valid consultRequest: ConsultRequest): ResponseEntity<ConsultResponse> {
        val consultResponse: ConsultResponse = consultService.addConsult(consultRequest)
        return ResponseEntity.ok(consultResponse)
    }

    @DeleteMapping
    @Transactional
    fun delete(@RequestBody @Valid cancelData: CancellationRequest): ResponseEntity<Boolean> {
        consultService.cancel(cancelData)
        return ResponseEntity.noContent().build()
    }
}