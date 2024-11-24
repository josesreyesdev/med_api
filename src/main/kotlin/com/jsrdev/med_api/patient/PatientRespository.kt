package com.jsrdev.med_api.patient

import org.springframework.data.jpa.repository.JpaRepository

interface PatientRespository : JpaRepository<Patient, Long>