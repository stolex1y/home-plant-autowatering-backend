package ru.filimonov.hpa.rest.common.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.util.*

data class ExceptionHandlerResponse(
    val message: String,
    val statusCode: Int,
    val statusReasonPhrase: String,
    val timestamp: Long,
) {
    constructor(
        message: String,
        status: HttpStatus,
    ) : this(
        message = message,
        statusCode = status.value(),
        statusReasonPhrase = status.reasonPhrase,
        timestamp = Calendar.getInstance().timeInMillis,
    )

    fun toResponseEntity() = ResponseEntity
        .status(statusCode)
        .body(this)
}
