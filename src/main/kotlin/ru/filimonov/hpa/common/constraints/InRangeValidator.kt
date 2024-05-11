package ru.filimonov.hpa.common.constraints

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class InRangeValidator : ConstraintValidator<InRange, Number> {
    private var min: Double = 0.0
    private var max: Double = 0.0

    override fun initialize(constraintAnnotation: InRange) {
        min = constraintAnnotation.min
        max = constraintAnnotation.max
    }

    override fun isValid(value: Number?, context: ConstraintValidatorContext?): Boolean {
        return if (value == null)
            false
        else
            value.toDouble() in min..max
    }
}
