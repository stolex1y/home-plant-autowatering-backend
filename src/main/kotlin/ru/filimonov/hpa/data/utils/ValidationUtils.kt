package ru.filimonov.hpa.data.utils

import jakarta.validation.ConstraintViolation
import jakarta.validation.ConstraintViolationException
import jakarta.validation.Validator

fun <T> Validator.throwableValidate(t: T) {
    val violations: Set<ConstraintViolation<T>> = validate(t)

    if (violations.isNotEmpty()) {
        val sb = StringBuilder()
        for (constraintViolation in violations) {
            sb.append(constraintViolation.message)
        }
        throw ConstraintViolationException("Validation error: $sb", violations)
    }
}
