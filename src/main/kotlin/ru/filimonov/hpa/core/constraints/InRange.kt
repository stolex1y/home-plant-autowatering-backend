package ru.filimonov.hpa.core.constraints

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Constraint(
    validatedBy = [InRangeValidator::class]
)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class InRange(
    val min: Double,
    val max: Double,
    val message: String = "Value must be in range",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
) {
}
