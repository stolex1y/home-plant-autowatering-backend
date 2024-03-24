package ru.filimonov.hpa

import jakarta.validation.constraints.NotBlank
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.bind.ConstructorBinding
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.validation.annotation.Validated
import java.util.*

private const val LOCATION_PROPS = "app.location"

@SpringBootApplication
@ConfigurationPropertiesScan
class HomePlantAutowateringBackendApplication {

    @ConfigurationProperties(prefix = LOCATION_PROPS)
    @Validated
    class LocationProperties @ConstructorBinding constructor(
        @NotBlank
        val timeZone: String,
        @NotBlank
        val locale: String
    )

    @Bean
    fun setLocation(locationProperties: LocationProperties?) = CommandLineRunner {
        TimeZone.setDefault(TimeZone.getTimeZone(locationProperties?.timeZone ?: "UTC"))
        Locale.setDefault(Locale.forLanguageTag(locationProperties?.locale ?: "en-US"))
    }
}

fun main(args: Array<String>) {
    runApplication<HomePlantAutowateringBackendApplication>(*args)
}
