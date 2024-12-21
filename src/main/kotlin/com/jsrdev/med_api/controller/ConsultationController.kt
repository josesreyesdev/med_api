package com.jsrdev.med_api.controller

import com.jsrdev.med_api.domain.consult.CancellationRequest
import com.jsrdev.med_api.domain.consult.ConsultRequest
import com.jsrdev.med_api.domain.consult.ConsultResponse
import com.jsrdev.med_api.domain.consult.ConsultService
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/consult")
class ConsultationController(
    private val consultService: ConsultService
) {

    @PostMapping
    @Transactional
    fun add(@RequestBody @Valid consult: ConsultRequest): ResponseEntity<ConsultResponse> {
        consultService.addConsult(consult)
        return ResponseEntity.ok(ConsultResponse(null, null, null, null))
    }

    @DeleteMapping()
    @Transactional
    fun delete(@RequestBody @Valid cancelData: CancellationRequest): ResponseEntity<Boolean> {
        consultService.cancellationOfConsultation(cancelData)
        return ResponseEntity.noContent().build()
    }
}