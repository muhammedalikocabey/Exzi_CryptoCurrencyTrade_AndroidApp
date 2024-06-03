package com.muhammedalikocabey.exzi.core.utils

fun Int?.toPositiveIntOrDefault(defaultValue: Int = 0): Int {
    return this?.takeIf { it >= 0 } ?: defaultValue
}

fun Int?.toPairName(default: String = "Unknown Pair"): String {
    return if (this != null) {
        PairName.values().find { it.id == this }?.symbol ?: default
    } else {
        default
    }
}
