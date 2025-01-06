package com.jsrdev.med_api.domain.consult.validations.reserve

import com.jsrdev.med_api.domain.consult.ConsultRequest
import com.jsrdev.med_api.infra.exceptions.IntegrityValidation
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import java.time.LocalDateTime
import java.time.Month

class HoursOfOperationTest {

    private val hoursOfOperation = HoursOfOperation()

    @Test
    fun `should throw IntegrityValidation exception when the date is Sunday`() {
        // Arrange
        val consultRequest: ConsultRequest = mock {
            on { date } doReturn LocalDateTime.of(2025, Month.JANUARY, 7, 0, 0)  // Sunday
        }

        // Act & Assert
        val exception = assertThrows<IntegrityValidation> {
            hoursOfOperation.validate(consultRequest)
        }
        assert(exception.message == "Opening hours are Monday to Saturday from 07:00 to 18:00 hours")
    }

    @Test
    fun `should throw IntegrityValidation exception when the time is before 7 am`() {
        // Arrange
        val consultRequest: ConsultRequest = mock {
            on { date } doReturn LocalDateTime.of(2025, Month.JANUARY, 6, 6, 30)  // 6:30 am, Monday
        }

        // Act & Assert
        val exception = assertThrows<IntegrityValidation> {
            hoursOfOperation.validate(consultRequest)
        }
        assert(exception.message == "Opening hours are Monday to Saturday from 07:00 to 18:00 hours")
    }

    @Test
    fun `should throw IntegrityValidation exception when the time is after 7 pm`() {
        // Arrange
        val consultRequest: ConsultRequest = mock {
            on { date } doReturn LocalDateTime.of(2025, Month.JANUARY, 6, 19, 30)  // 7:30 pm, Monday
        }

        // Act & Assert
        val exception = assertThrows<IntegrityValidation> {
            hoursOfOperation.validate(consultRequest)
        }
        assert(exception.message == "Opening hours are Monday to Saturday from 07:00 to 18:00 hours")
    }

    @Test
    fun `should not throw exception when the date is within working hours`() {
        // Arrange
        val consultRequest: ConsultRequest = mock {
            on { date } doReturn LocalDateTime.of(2025, Month.JANUARY, 6, 10, 30)  // 10:30 am, Monday
        }

        // Act & Assert
        assertDoesNotThrow {
            hoursOfOperation.validate(consultRequest)
        }
    }
}
