package com.muhammedalikocabey.exzi.core.utils

import com.github.mikephil.charting.data.CandleEntry
import com.muhammedalikocabey.exzi.data.model.CandleResponseModel

fun List<CandleResponseModel.ViewEntity>.getRecentLowestPrices(count: Int = 6): List<CandleResponseModel.ViewEntity> {
    return this.sortedWith(
        compareByDescending<CandleResponseModel.ViewEntity> { it.time?.toLongOrNull() ?: Long.MIN_VALUE }
            .thenBy { it.low?.toDouble() ?: Double.MAX_VALUE }
    )
}

fun List<CandleResponseModel.ViewEntity>.getRecentHighestPrices(count: Int = 6): List<CandleResponseModel.ViewEntity> {
    return this.sortedWith(
        compareByDescending<CandleResponseModel.ViewEntity> { it.time?.toLongOrNull() ?: Long.MIN_VALUE }
            .thenByDescending { it.high?.toDouble() ?: Double.MIN_VALUE }
    )
}

fun PairName?.toApiFormat(default: String = "BTCUSDT"): String {
    return this?.symbol?.replace("/", "") ?: default
}

fun PairName?.getBaseCurrency(default: String = "BTC"): String {
    return this?.symbol?.split("/")?.firstOrNull() ?: default
}

fun PairName?.getQuoteCurrency(default: String = "USDT"): String {
    return this?.symbol?.split("/")?.lastOrNull() ?: default
}

fun List<CandleResponseModel.ViewEntity>.toCandleEntries(): List<CandleEntry> {
    return mapIndexed { index, candle ->
        CandleEntry(
            index.toFloat(),
            candle.high?.toFloat() ?: 0f,
            candle.low?.toFloat() ?: 0f,
            candle.open?.toFloat() ?: 0f,
            candle.close?.toFloat() ?: 0f
        )
    }
}
