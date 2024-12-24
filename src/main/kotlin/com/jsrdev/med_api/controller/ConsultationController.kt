package com.jsrdev.med_api.controller

import com.jsrdev.med_api.domain.consult.Consult
import com.jsrdev.med_api.domain.consult.ConsultMapper.toResponse
import com.jsrdev.med_api.domain.consult.cancel.CancellationRequest
import com.jsrdev.med_api.domain.consult.ConsultRequest
import com.jsrdev.med_api.domain.consult.ConsultResponse
import com.jsrdev.med_api.domain.consult.ConsultService
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/consultation")
class ConsultationController(
    private val consultService: ConsultService
) {

    @PostMapping
    @Transactional
    fun add(@RequestBody @Valid consultRequest: ConsultRequest): ResponseEntity<ConsultResponse> {
        val consult: Consult = consultService.addConsult(consultRequest)
        return ResponseEntity.ok(consult.toResponse())
    }

    @DeleteMapping
    @Transactional
    fun delete(@RequestBody @Valid cancelData: CancellationRequest): ResponseEntity<Boolean> {
        consultService.cancellationOfConsultation(cancelData)
        return ResponseEntity.noContent().build()
    }
}