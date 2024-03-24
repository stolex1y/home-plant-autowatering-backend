package ru.filimonov.hpa.rest.common.constraints

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Constraint(
    validatedBy = [MacAddressValidator::class]
)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class MacAddress(
    val message: String = "Does not match the format of the MAC address",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
) {
}
