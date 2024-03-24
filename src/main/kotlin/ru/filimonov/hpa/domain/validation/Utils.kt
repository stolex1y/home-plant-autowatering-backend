package ru.filimonov.hpa.domain.validation

import ru.filimonov.hpa.domain.exceptions.DomainValidationErrorException

fun domainRequire(value: Boolean, field: String, errorMessage: () -> String) {
    if (!value) {
        throw DomainValidationErrorException(field = field, error = errorMessage())
    }
}

fun domainRequire(value: Boolean, errorMessage: () -> String) {
    if (!value) {
        throw DomainValidationErrorException(message = errorMessage())
    }
}

fun <T> domainRequireNotNull(value: T?, field: String, errorMessage: () -> String) {
    domainRequire(value = value != null, field = field, errorMessage = errorMessage)
}

fun <T> domainRequireNotNull(value: T?, errorMessage: () -> String) {
    domainRequire(value = value != null, errorMessage = errorMessage)
}

fun <T> domainRequireNull(value: T?, field: String, errorMessage: () -> String) {
    domainRequire(value = (value == null), field = field, errorMessage = errorMessage)
}

fun <T> domainRequireNull(value: T?, errorMessage: () -> String) {
    domainRequire(value = (value == null), errorMessage = errorMessage)
}
