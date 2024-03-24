package ru.filimonov.hpa.core

import org.springframework.data.domain.Range

fun <T : Comparable<T>> Range<T>.isValid(): Boolean {
    if (upperBound.value.isEmpty || lowerBound.value.isEmpty) {
        return true
    }
    return if (upperBound.isInclusive) {
        if (lowerBound.isInclusive) {
            upperBound.value.get() >= lowerBound.value.get()
        } else {
            upperBound.value.get() > lowerBound.value.get()
        }
    } else {
        if (lowerBound.isInclusive) {
            upperBound.value.get() > lowerBound.value.get()
        } else {
            upperBound.value.get() >= lowerBound.value.get()
        }
    }
}
