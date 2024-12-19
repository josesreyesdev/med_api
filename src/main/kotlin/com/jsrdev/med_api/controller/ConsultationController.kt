package com.jsrdev.med_api.controller

import com.jsrdev.med_api.domain.consult.ConsultRequest
import com.jsrdev.med_api.domain.consult.ConsultResponse
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/consult")
class ConsultationController {

    @PostMapping
    @Transactional
    fun add(@RequestBody @Valid consult: ConsultRequest): ResponseEntity<ConsultResponse> {
        return ResponseEntity.ok(ConsultResponse(null, null, null, null))
    }
}