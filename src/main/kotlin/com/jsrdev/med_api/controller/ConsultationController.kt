package com.jsrdev.med_api.controller

import com.jsrdev.med_api.domain.consult.*
import com.jsrdev.med_api.domain.consult.validations.cancel.CancellationRequest
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.PagedModel
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.YearMonth

@RestController
@RequestMapping("/api/consultation")
@SecurityRequirement(name = "bearer-key")
class ConsultationController(
    private val consultService: ConsultService,
    private val consultRepository: ConsultRepository
) {

    @PostMapping
    @Transactional
    fun add(@RequestBody @Valid consultRequest: ConsultRequest): ResponseEntity<ConsultResponse> {
        val consultResponse: ConsultResponse = consultService.addConsult(consultRequest)
        return ResponseEntity.ok(consultResponse)
    }

    @GetMapping
    fun getConsultations(
        @PageableDefault(
            size = 10, sort = ["date"]
        ) pagination: Pageable,
        assembler: PagedResourcesAssembler<DetailConsultationResponse>
    ): ResponseEntity<PagedModel<EntityModel<DetailConsultationResponse>>> {
        val response: Page<DetailConsultationResponse> = consultService.consultation(pagination)
        return ResponseEntity.ok(assembler.toModel(response))
    }

    @GetMapping("/monthly-report/{month}")
    fun monthlyReport(
        @PathVariable(name = "month") yearMonth: YearMonth,
        @PageableDefault(size = 10, sort = ["physician.name"]) pagination: Pageable,
        assembler: PagedResourcesAssembler<MonthlyConsultationReport>
    ): ResponseEntity<PagedModel<EntityModel<MonthlyConsultationReport>>> {
        val year: Int = yearMonth.year
        val month: Int = yearMonth.monthValue
        val reportPage: Page<MonthlyConsultationReport> = consultRepository.findMonthlyConsultation(year, month, pagination)
        return ResponseEntity.ok(assembler.toModel(reportPage))
    }

    @DeleteMapping
    @Transactional
    fun delete(@RequestBody @Valid cancelData: CancellationRequest): ResponseEntity<Boolean> {
        consultService.cancel(cancelData)
        return ResponseEntity.noContent().build()
    }
}