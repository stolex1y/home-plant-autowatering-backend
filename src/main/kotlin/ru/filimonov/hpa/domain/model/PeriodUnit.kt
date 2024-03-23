package ru.filimonov.hpa.domain.model

import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours

enum class PeriodUnit(val duration: Duration) {
    DAY(1.days),
    HOUR(1.hours);
}
