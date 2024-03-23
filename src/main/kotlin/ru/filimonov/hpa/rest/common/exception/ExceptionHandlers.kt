package ru.filimonov.hpa.rest.common.exception

import org.springframework.core.convert.ConversionFailedException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException

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
    fun handleException(ex: Throwable): ResponseEntity<ExceptionHandlerResponse> {
        return ExceptionHandlerResponse(
            message = "Internal server error",
            status = HttpStatus.INTERNAL_SERVER_ERROR,
        ).toResponseEntity()
    }
}
