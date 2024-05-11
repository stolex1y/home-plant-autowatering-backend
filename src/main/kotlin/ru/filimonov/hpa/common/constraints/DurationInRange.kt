package ru.filimonov.hpa.common.constraints

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.FIELD,
    AnnotationTarget.ANNOTATION_CLASS,
    AnnotationTarget.CONSTRUCTOR,
    AnnotationTarget.TYPE_PARAMETER,
    AnnotationTarget.TYPE,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.VALUE_PARAMETER,
)
@Constraint(
    validatedBy = [DurationRangeValidator::class]
)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class DurationInRange(
    val minMs: Long,
    val maxMs: Long,
    val message: String = "Duration must be in range",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
)
