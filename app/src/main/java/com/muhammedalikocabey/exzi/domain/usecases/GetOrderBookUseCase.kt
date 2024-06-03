package com.muhammedalikocabey.exzi.domain.usecases

import com.muhammedalikocabey.exzi.core.utils.NetworkResults
import com.muhammedalikocabey.exzi.data.model.OrderBookResponseModel
import com.muhammedalikocabey.exzi.domain.repository.GetRepository
import kotlinx.coroutines.flow.Flow

class GetOrderBookUseCase(private val getRepository: GetRepository) {
    fun getOrderBook(pairId: Int, buy: Int, sell: Int): Flow<NetworkResults<OrderBookResponseModel>> {
        return getRepository.getOrderBook(pairId, buy, sell)
    }
}
