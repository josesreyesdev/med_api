package com.jsrdev.med_api.domain.physician

import com.jsrdev.med_api.domain.address.AddressData
import com.jsrdev.med_api.domain.consult.Consult
import com.jsrdev.med_api.domain.patient.Patient
import com.jsrdev.med_api.domain.patient.PatientRequest
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.TemporalAdjusters


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class PhysicianRepositoryTest {

    @Autowired
    private lateinit var physicianRepository: PhysicianRepository

    @Autowired
    private lateinit var entityManager: EntityManager

    @Test
    @DisplayName("It should return null when the physician is in consultation with another patient at that time.")
    fun chooseARandomPhysicianAvailableOnTheDateScenario1() {

        /* GIVEN */
        val nextMondayAt10h = LocalDate.now()
            .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
            .atTime(10, 0) // 10 am

        /*
        * Register a physician, patient and consultation
        * */
        val physician = addPhysician(
            "Juan P",
            "https://imagen-physician.com",
            "juanp@example.com",
            "12443",
            Specialty.CARDIOLOGY
        )

        val patient = addPatient(
            "Paciente Prueba",
            "https://imagen-patient.com",
            "patienttest@example.com",
            "213242"
        )

        addConsultation(physician, patient, nextMondayAt10h)

        /* WHEN */
        val freePhysician = physicianRepository
            .chooseARandomPhysicianAvailableOnTheDate(Specialty.CARDIOLOGY, nextMondayAt10h)

        /* THEN */
        assertThat(freePhysician).isNull()
    }

    @Test
    @DisplayName("Must return a physician when available on that date")
    fun chooseARandomPhysicianAvailableOnTheDateScenario2() {

        /* GIVEN */
        val nextMondayAt10h = LocalDate.now()
            .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
            .atTime(10, 0) // 10 am

        /*
        * Register a physician, patient and consultation
        * */
        val physician = addPhysician(
            "Juan P",
            "https://imagen-physician.com",
            "juanp@example.com",
            "12443",
            Specialty.CARDIOLOGY
        )

        /* WHEN */
        val freePhysician = physicianRepository
            .chooseARandomPhysicianAvailableOnTheDate(Specialty.CARDIOLOGY, nextMondayAt10h)

        /* THEN */
        assertThat(freePhysician).isEqualTo(physician)
    }

    private fun addConsultation(physician: Physician, patient: Patient, date: LocalDateTime) {
        entityManager.persist(Consult(null, physician, patient, date, null));
    }

    private fun addPhysician(
        name: String,
        avatar: String,
        email: String,
        document: String,
        specialty: Specialty
    ): Physician {
        val physician = Physician(physicianData(name, avatar, email, document, specialty))

        entityManager.persist(physician)
        return physician
    }

    private fun addPatient(name: String, avatar: String, email: String, document: String): Patient {
        val patient = Patient(patientData(name, avatar, email, document))

        entityManager.persist(patient)
        return patient
    }

    private fun physicianData(
        name: String,
        avatar: String,
        email: String,
        document: String,
        specialty: Specialty
    ): PhysicianRequest {
        return PhysicianRequest(
            name,
            avatar,
            email,
            document,
            "123123",
            specialty,
            addressData()
        )
    }

    private fun patientData(
        name: String,
        avatar: String,
        email: String,
        document: String
    ): PatientRequest {
        return PatientRequest(
            name,
            avatar,
            email,
            document,
            "131231",
            addressData()
        )
    }

    private fun addressData(): AddressData {
        return AddressData(
            "Mexico street",
            "Mexico",
            "Mexico muni or del.",
            "Mexico City",
            "14737",
            "mexico",
            "1212",
            "123",
            "City central complement"
        )
    }

}