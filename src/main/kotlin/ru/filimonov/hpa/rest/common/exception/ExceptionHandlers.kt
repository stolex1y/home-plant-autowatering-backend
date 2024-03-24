package ru.filimonov.hpa.rest.common.exception

import org.hibernate.exception.ConstraintViolationException
import org.springframework.core.convert.ConversionFailedException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.stereotype.Component
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import ru.filimonov.hpa.domain.exceptions.DomainValidationErrorException

@RestControllerAdvice
@Component
class ExceptionHandlers {
    @ExceptionHandler
    fun handleException(ex: IllegalAccessException): ResponseEntity<ExceptionHandlerResponse> {
        return ExceptionHandlerResponse(
            message = "Tried to access non-existent data",
            status = HttpStatus.NOT_FOUND,
        ).toResponseEntity()
    }

    @ExceptionHandler
    fun handleException(
        ex: MethodArgumentTypeMismatchException
    ): ResponseEntity<ExceptionHandlerResponse> {
        val cause = if (ex.cause is ConversionFailedException)
            ex.cause?.cause
        else
            ex.cause
        return ExceptionHandlerResponse(
            message = "Bad request argument with name '${ex.name}'. ${cause?.message}",
            status = HttpStatus.BAD_REQUEST,
        ).toResponseEntity()
    }

    @ExceptionHandler
    fun handleException(ex: HttpMessageNotReadableException): ResponseEntity<ExceptionHandlerResponse> {
        return ExceptionHandlerResponse(
            message = "Bad request argument. ${ex.message}",
            status = HttpStatus.BAD_REQUEST,
        ).toResponseEntity()
    }

    @ExceptionHandler
    fun handleException(ex: DataIntegrityViolationException): ResponseEntity<ExceptionHandlerResponse> {
        val cause = ex.cause
        val message = if (cause is ConstraintViolationException) {
            cause.errorMessage
        } else {
            ex.message
        }
        return ExceptionHandlerResponse(
            message = "Invalid request. $message",
            status = HttpStatus.BAD_REQUEST,
        ).toResponseEntity()
    }

    @ExceptionHandler
    fun handleException(ex: jakarta.validation.ConstraintViolationException): ResponseEntity<ExceptionHandlerResponse> {
        val message =
            ex.constraintViolations.joinToString(". ") {
                "Error in the field '${it.propertyPath}': ${it.message}"
            }
        return ExceptionHandlerResponse(
            message = "Invalid request. $message",
            status = HttpStatus.BAD_REQUEST,
        ).toResponseEntity()
    }

    @ExceptionHandler
    fun handleException(ex: DomainValidationErrorException): ResponseEntity<ExceptionHandlerResponse> {
        return ExceptionHandlerResponse(
            message = "Invalid request. ${ex.message}",
            status = HttpStatus.BAD_REQUEST,
        ).toResponseEntity()
    }

    @ExceptionHandler
    fun handleException(ex: MethodArgumentNotValidException): ResponseEntity<ExceptionHandlerResponse> {
        val message =
            ex.bindingResult.fieldErrors.joinToString(". ") {
                "Error in the field '${it.field}': ${it.defaultMessage}"
            }
        return ExceptionHandlerResponse(
            message = "Invalid request. $message",
            status = HttpStatus.BAD_REQUEST,
        ).toResponseEntity()
    }


    @ExceptionHandler
    fun handleException(ex: Throwable): ResponseEntity<ExceptionHandlerResponse> {
        return ExceptionHandlerResponse(
            message = "Internal server error",
            status = HttpStatus.INTERNAL_SERVER_ERROR,
        ).toResponseEntity()
    }
}
