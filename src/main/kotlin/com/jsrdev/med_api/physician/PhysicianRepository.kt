package com.jsrdev.med_api.physician

import org.springframework.data.jpa.repository.JpaRepository

interface PhysicianRepository : JpaRepository<Physician, Long>