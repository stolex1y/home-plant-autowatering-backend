package ru.filimonov.hpa.core.constraints

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.springframework.data.domain.Range
import ru.filimonov.hpa.core.isValid
import java.util.*

class CalendarRangeValidator : ConstraintValidator<ValidCalendarRange, Range<Calendar>> {
    override fun isValid(value: Range<Calendar>, context: ConstraintValidatorContext?): Boolean {
        return value.isValid()
    }
}
