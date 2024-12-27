package com.jsrdev.med_api.controller

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

@SpringBootTest
@AutoConfigureMockMvc
class ConsultationControllerTest {

    @Autowired
    private lateinit var mock: MockMvc

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
}