package com.jsrdev.med_api.infra.errors

import com.jsrdev.med_api.infra.exceptions.ValidateException
import jakarta.persistence.EntityNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgument(ex: IllegalArgumentException): ResponseEntity<String> {
        return ResponseEntity(ex.message, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun errorHandler400(ex: MethodArgumentNotValidException): ResponseEntity<Map<String, String?>> {
        val errors = ex.bindingResult.fieldErrors.map { ErrorValidationData(it) }.associate {
            it.field to it.error
        }
        return ResponseEntity.badRequest().body(errors)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun jsonParseError(ex: HttpMessageNotReadableException): ResponseEntity<String> {
        return ResponseEntity(ex.message, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(EntityNotFoundException::class)
    fun errorHandler404(ex: EntityNotFoundException): ResponseEntity<String> =
        ResponseEntity(ex.message, HttpStatus.NOT_FOUND)

    @ExceptionHandler(ValidateException::class)
    fun errorHandlerValidate(ex: ValidateException): ResponseEntity<String> {
        return ResponseEntity.badRequest().body(ex.message)
    }

    data class ErrorValidationData(val field: String, val error: String?) {
        constructor(error: FieldError) : this(
            field = error.field,
            error = error.defaultMessage
        )
    }
}