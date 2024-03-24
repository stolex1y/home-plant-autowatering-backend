package ru.filimonov.hpa.domain.exceptions

class DomainValidationErrorException(
    override val message: String,
    override val cause: Throwable? = null,
) : RuntimeException() {
    constructor(
        field: String,
        error: String,
        cause: Throwable? = null,
    ) : this(
        message = "Error in the field '$field': $error",
        cause = cause,
    )
}
