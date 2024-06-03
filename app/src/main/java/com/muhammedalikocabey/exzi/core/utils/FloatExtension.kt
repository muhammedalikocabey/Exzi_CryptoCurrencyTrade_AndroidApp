package com.muhammedalikocabey.exzi.core.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Float?.isNullOrZero(): Boolean {
    return this == null || this == 0.0f
}

fun Float?.toDefaultIfNull(defaultValue: Float = 0.0f): Float {
    return this ?: defaultValue
}

fun Float.round(decimals: Int): Float {
    var multiplier = 1.0f
    repeat(decimals) { multiplier *= 10 }
    return kotlin.math.round(this * multiplier) / multiplier
}

fun Double?.isNullOrZero(): Boolean {
    return this == null || this == 0.0
}

fun Double?.toDefaultIfNull(defaultValue: Double = 0.0): Double {
    return this ?: defaultValue
}

fun Double.round(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return kotlin.math.round(this * multiplier) / multiplier
}

fun Long?.isNullOrZero(): Boolean {
    return this == null || this == 0L
}

fun Long?.toDefaultIfNull(defaultValue: Long = 0L): Long {
    return this ?: defaultValue
}

fun Long?.toReadableDate(default: String = "Unknow Date"): String {
    return if (this != null && this > 0) {
        val date = Date(this * 1000) // Convert seconds to milliseconds
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        format.format(date)
    } else {
        default
    }
}

fun Long?.toLHCurrencyFormat(defaultValue: String = "0.00"): String {
    return try {
        val value = this ?: return defaultValue

        val trimmedValue = value / 10000

        String.format("%,.2f", trimmedValue / 100.0)
    } catch (e: Exception) {
        defaultValue
    }
}

fun Long?.toVolumeCurrencyFormat(defaultValue: String = "0.00"): String {
    return try {
        val value = this ?: return defaultValue

        val trimmedValue = value / 10000

        String.format("%,.2f", trimmedValue / 100.0)
    } catch (e: Exception) {
        defaultValue
    }
}

fun Long?.toOpenCloseCurrencyFormat(defaultValue: String = "0,00"): String {
    val value = this ?: return defaultValue

    val shortenedValue = value / 10000

    val stringValue = shortenedValue.toString()

    val paddedString = stringValue.padStart(7, '0')

    val integerPart = paddedString.substring(0, paddedString.length - 4)
    val fractionalPart = paddedString.substring(paddedString.length - 2)

    val formattedIntegerPart = integerPart.reversed().chunked(3).joinToString(".").reversed()

    return "$formattedIntegerPart,$fractionalPart"
}
