package com.muhammedalikocabey.exzi.data.remote

import com.muhammedalikocabey.exzi.data.model.CandleResponseModel
import com.muhammedalikocabey.exzi.data.model.OrderBookResponseModel
import com.muhammedalikocabey.exzi.data.network.ApiClient
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiClient: ApiClient) {

    suspend fun getCandles(pair: String, resolution: String, limit: Int, end: Long): Response<List<CandleResponseModel>> {
        return apiClient.getCandles(pair, resolution, limit, end)
    }

    suspend fun getOrderBook(pairId: Int, buy: Int, sell: Int): Response<OrderBookResponseModel> {
        return apiClient.getOrderBook(pairId, buy, sell)
    }
}
