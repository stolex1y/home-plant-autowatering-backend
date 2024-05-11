package ru.filimonov.hpa.common.constraints

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Constraint(
    validatedBy = [CalendarRangeValidator::class]
)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class ValidCalendarRange(
    val message: String = "Invalid range of values",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
)
