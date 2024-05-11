package ru.filimonov.hpa.common.converters

import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import ru.filimonov.hpa.domain.model.PeriodUnit

@Component
class PeriodUnitConverter : Converter<String, PeriodUnit> {
    override fun convert(source: String): PeriodUnit? {
        return try {
            PeriodUnit.valueOf(source.uppercase())
        } catch (e: IllegalArgumentException) {
            val existedUnits = PeriodUnit.entries.joinToString(", ") {
                it.name.lowercase()
            }
            throw IllegalArgumentException(
                "Unknown period unit: $source. Please, use one of this: $existedUnits"
            )
        }
    }
}
