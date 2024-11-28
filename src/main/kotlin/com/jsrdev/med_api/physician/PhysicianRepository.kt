package com.jsrdev.med_api.physician

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface PhysicianRepository : JpaRepository<Physician, Long> {
    fun findByActiveTrue(pagination: Pageable): Page<Physician>
}