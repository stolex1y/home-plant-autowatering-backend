package ru.filimonov.hpa.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
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

fun <T> Flow<T>.mapToResult() =
    this.map { Result.success(it) }.catch {
        emit(Result.failure(it))
    }
