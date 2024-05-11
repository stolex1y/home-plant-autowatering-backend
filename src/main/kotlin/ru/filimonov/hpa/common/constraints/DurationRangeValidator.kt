package ru.filimonov.hpa.common.constraints

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

class DurationRangeValidator : ConstraintValidator<InRange, Duration> {
    private var min: Duration = Duration.ZERO
    private var max: Duration = Duration.INFINITE

    override fun initialize(constraintAnnotation: InRange) {
        min = constraintAnnotation.min.milliseconds
        max = constraintAnnotation.max.milliseconds
    }

    override fun isValid(value: Duration?, context: ConstraintValidatorContext?): Boolean {
        return if (value == null)
            false
        else
            value in min..max
    }
}
