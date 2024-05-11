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
    validatedBy = [MacAddressValidator::class]
)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class MacAddress(
    val message: String = "Does not match the format of the MAC address",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
)
