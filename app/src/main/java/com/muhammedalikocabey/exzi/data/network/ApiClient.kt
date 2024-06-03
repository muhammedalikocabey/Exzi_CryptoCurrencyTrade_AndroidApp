package com.muhammedalikocabey.exzi.data.network

import com.muhammedalikocabey.exzi.data.model.CandleResponseModel
import com.muhammedalikocabey.exzi.data.model.OrderBookResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiClient {
    @GET(GET_CANDLES_URL)
    suspend fun getCandles(
        @Query("t") pair: String,
        @Query("r") resolution: String = "D",
        @Query("limit") limit: Int,
        @Query("end") end: Long
    ): Response<List<CandleResponseModel>>

    @GET(GET_ORDERS_URL)
    suspend fun getOrderBook(
        @Query("pair_id") pairId: Int,
        @Query("buy") buy: Int,
        @Query("sell") sell: Int
    ): Response<OrderBookResponseModel>
}
