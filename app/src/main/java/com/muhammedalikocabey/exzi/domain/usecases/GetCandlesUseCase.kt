package com.muhammedalikocabey.exzi.domain.usecases

import com.muhammedalikocabey.exzi.core.utils.NetworkResults
import com.muhammedalikocabey.exzi.data.model.CandleResponseModel
import com.muhammedalikocabey.exzi.domain.repository.GetRepository
import kotlinx.coroutines.flow.Flow

class GetCandlesUseCase(private val getRepository: GetRepository) {
    fun getCandles(pair: String, resolution: String, limit: Int, end: Long): Flow<NetworkResults<List<CandleResponseModel>>> {
        return getRepository.getCandles(pair, resolution, limit, end)
    }
}
