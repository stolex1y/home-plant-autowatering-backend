package ru.filimonov.hpa.common.converters

import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import ru.filimonov.hpa.common.toCalendar
import java.text.SimpleDateFormat
import java.util.*

@Component
class CalendarConverter : Converter<String, Calendar> {
    private val dateFormat = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH)

    override fun convert(source: String): Calendar? {
        source.toLongOrNull()?.toCalendar()?.let { return it }
        try {
            return Calendar.getInstance().apply {
                time = dateFormat.parse(source)
            }
        } catch (ex: Exception) {
            throw IllegalArgumentException("Expected unix time in milliseconds or date in '${dateFormat.toPattern()}' format")
        }
    }
}
