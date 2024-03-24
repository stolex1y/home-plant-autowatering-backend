package ru.filimonov.hpa.rest.common.constraints

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class MacAddressValidator : ConstraintValidator<MacAddress, String> {
    companion object {
        private val regex = Regex("^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$")
    }

    override fun isValid(value: String?, context: ConstraintValidatorContext): Boolean {
        return value?.takeIf { it.matches(regex) } != null
    }
}
