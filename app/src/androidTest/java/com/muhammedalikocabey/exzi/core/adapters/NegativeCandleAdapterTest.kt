package com.muhammedalikocabey.exzi.core.adapters

import com.muhammedalikocabey.exzi.data.model.CandleResponseModel
import org.junit.Assert.*
import org.junit.Test

class NegativeCandleAdapterTest {

    private val candleDiffCallback = NegativeCandleAdapter.CandleDiffCallback()

    @Test
    fun testCandleDiffCallbackAreItemsTheSameReturnsTrueForSameItems() {
        val candle1 = createCandle("1624024800", 1000)
        val candle2 = createCandle("1624024800", 1000)
        assertTrue(candleDiffCallback.areItemsTheSame(candle1, candle2))
    }

    @Test
    fun testCandleDiffCallbackAreItemsTheSameReturnsFalseForDifferentItems() {
        val candle1 = createCandle("1624024800", 1000)
        val candle2 = createCandle("1624024801", 1001)
        assertFalse(candleDiffCallback.areItemsTheSame(candle1, candle2))
    }

    @Test
    fun testCandleDiffCallbackAreContentsTheSameReturnsTrueForsameContents() {
        val candle1 = createCandle("1624024800", 1000)
        val candle2 = createCandle("1624024800", 1000)
        assertTrue(candleDiffCallback.areContentsTheSame(candle1, candle2))
    }

    @Test
    fun testCandleDiffCallbackAreContentsTheSameReturnsFalseForDifferentContents() {
        val candle1 = createCandle("1624024800", 1000)
        val candle2 = createCandle("1624024800", 1001)
        assertFalse(candleDiffCallback.areContentsTheSame(candle1, candle2))
    }

    @Test
    fun testCalculateInverseScaledValueStartValue() {
        val adapter = NegativeCandleAdapter()
        val result = adapter.calculateInverseScaledValue(1, 10)
        assertEquals(100.0, result, 0.01)
    }

    @Test
    fun testCalculateInverseScaledValueMiddleValue() {
        val adapter = NegativeCandleAdapter()
        val result = adapter.calculateInverseScaledValue(5, 10)
        assertEquals(55.55555555555556, result, 0.01)
    }

    @Test
    fun testCalculateInverseScaledValueEndValue() {
        val adapter = NegativeCandleAdapter()
        val result = adapter.calculateInverseScaledValue(10, 10)
        assertEquals(0.0, result, 0.01)
    }

    @Test
    fun testCalculateInverseScaledValueArbitraryValue() {
        val adapter = NegativeCandleAdapter()
        val result = adapter.calculateInverseScaledValue(3, 7)
        assertEquals(66.66666666666667, result, 0.01)
    }

    private fun createCandle(time: String, volume: Long): CandleResponseModel.ViewEntity {
        return CandleResponseModel.ViewEntity(
            low = 1000,
            high = 200,
            volume = volume,
            time = time,
            open = 150,
            close = 180,
            pairId = "BTCUSDT",
            times = null,
            lowF = "100.00",
            highF = "200.00",
            openF = "150.00",
            closeF = "180.00",
            volumeF = "0.01"
        )
    }
}

