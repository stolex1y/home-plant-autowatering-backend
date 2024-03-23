package ru.filimonov.hpa.rest.converters

import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import ru.filimonov.hpa.core.toCalendar
import java.util.*

@Component
class CalendarConverter : Converter<String, Calendar> {
    override fun convert(source: String): Calendar? {
        return source.toLongOrNull()?.toCalendar() ?: throw IllegalArgumentException("Expected unix time in milliseconds")
    }
}
