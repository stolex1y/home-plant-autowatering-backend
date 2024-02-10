package ru.filimonov.hpa

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HomePlantAutowateringBackendApplication

fun main(args: Array<String>) {
    runApplication<HomePlantAutowateringBackendApplication>(*args)
}
