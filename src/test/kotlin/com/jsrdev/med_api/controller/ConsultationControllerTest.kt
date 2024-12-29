package com.jsrdev.med_api.controller

import com.jsrdev.med_api.domain.consult.ConsultRequest
import com.jsrdev.med_api.domain.consult.ConsultResponse
import com.jsrdev.med_api.domain.consult.ConsultService
import com.jsrdev.med_api.domain.physician.Specialty
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.json.JacksonTester
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import java.time.LocalDateTime
import org.mockito.kotlin.any as kAny

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultationControllerTest {

    @Autowired
    private lateinit var mock: MockMvc

    @Autowired
    private lateinit var jsonConsultRequest: JacksonTester<ConsultRequest>

    @Autowired
    private lateinit var jsonConsultResponse: JacksonTester<ConsultResponse>

    @MockBean
    private lateinit var addConsultation: ConsultService

    @Test
    @DisplayName("Should return http 400 error when the request is empty")
    @WithMockUser
    fun addScenario1() {
        /* GIVEN and WHEN */
        val response = mock.perform(post("/api/consultation"))
            .andReturn().response

        /* THEN */
        assertThat(response.status).isEqualTo(HttpStatus.BAD_REQUEST.value())
    }

    @Test
    @DisplayName("Should return http 200 success when the request receives a valid json")
    @WithMockUser
    fun addScenario2() {

        /* GIVEN */
        val myDate = LocalDateTime.now().plusHours(1)
        val consultResponse = ConsultResponse(
            id = null,
            physicianId = 1L,
            patientId = 2L,
            date = myDate
        )

        Mockito.`when` (addConsultation.addConsult(kAny())).thenReturn(consultResponse)

        val response = mock.perform(
            post("/api/consultation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    jsonConsultRequest.write(
                        ConsultRequest(
                            idPatient = 2L,
                            idPhysician = 1L,
                            date = myDate,
                            specialty = Specialty.CARDIOLOGY
                        )
                    ).json
                )
        ).andReturn().response

        /* WHEN */
        val jsonExpected = jsonConsultResponse.write(consultResponse).json

        /* THEN */
        assertThat(response.status).isEqualTo(HttpStatus.OK.value())
        assertThat(response.contentAsString).isEqualTo(jsonExpected)
    }
}