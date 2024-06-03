package com.muhammedalikocabey.exzi.core.utils

import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.Locale

fun String?.isNullOrEmpty(): Boolean {
    return this == null || this.isEmpty()
}

fun String?.isNullOrBlank(): Boolean {
    return this == null || this.isBlank()
}

fun String?.toFormattedPrice(default: String = "Unknown Price"): String {
    return if (!this.isNullOrBlank()) {
        try {
            val value = this?.toDouble()
            val symbols = DecimalFormatSymbols(Locale.US).apply {
                decimalSeparator = '.'
                groupingSeparator = ','
            }
            val format = DecimalFormat("#,##0.0000", symbols)
            format.format(value)
        } catch (e: NumberFormatException) {
            default
        }
    } else {
        default
    }
}

fun String?.toFormattedRateF(): String {
    val defaultValue = "0.000,00"

    if (this.isNullOrEmpty()) {
        return defaultValue
    }

    return try {
        val value = this?.toDouble()

        val numberFormat = NumberFormat.getNumberInstance(Locale.GERMANY).apply {
            minimumFractionDigits = 2
            maximumFractionDigits = 2
        }

        numberFormat.format(value)
    } catch (e: NumberFormatException) {
        defaultValue
    }
}

fun String?.toFormattedVolumeF(): String {
    val defaultValue = "0.0000"

    if (this.isNullOrEmpty()) {
        return defaultValue
    }

    return try {
        val value = this?.toBigDecimalOrNull() ?: return defaultValue
        value.setScale(4, RoundingMode.DOWN).toPlainString()
    } catch (e: NumberFormatException) {
        defaultValue
    }
}
