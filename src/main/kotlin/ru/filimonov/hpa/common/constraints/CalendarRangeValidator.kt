package ru.filimonov.hpa.common.constraints

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.springframework.data.domain.Range
import ru.filimonov.hpa.common.isValid
import java.util.*

class CalendarRangeValidator : ConstraintValidator<ValidCalendarRange, Range<Calendar>> {
    override fun isValid(value: Range<Calendar>, context: ConstraintValidatorContext?): Boolean {
        return value.isValid()
    }
}
