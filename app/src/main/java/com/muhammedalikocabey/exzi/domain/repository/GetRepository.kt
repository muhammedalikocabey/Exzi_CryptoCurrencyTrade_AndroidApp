package com.muhammedalikocabey.exzi.domain.repository

import com.muhammedalikocabey.exzi.core.utils.NetworkResults
import com.muhammedalikocabey.exzi.data.model.CandleResponseModel
import com.muhammedalikocabey.exzi.data.model.OrderBookResponseModel
import kotlinx.coroutines.flow.Flow

interface GetRepository {
    fun getCandles(pair: String, resolution: String, limit: Int, end: Long): Flow<NetworkResults<List<CandleResponseModel>>>
    fun getOrderBook(pairId: Int, buy: Int, sell: Int): Flow<NetworkResults<OrderBookResponseModel>>
}
