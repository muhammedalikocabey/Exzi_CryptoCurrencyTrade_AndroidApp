package com.muhammedalikocabey.exzi.data.repository

import android.app.Application
import com.muhammedalikocabey.exzi.core.utils.NetworkResults
import com.muhammedalikocabey.exzi.core.utils.isNetworkAvailable
import com.muhammedalikocabey.exzi.data.model.CandleResponseModel
import com.muhammedalikocabey.exzi.data.model.OrderBookResponseModel
import com.muhammedalikocabey.exzi.data.remote.RemoteDataSource
import com.muhammedalikocabey.exzi.domain.repository.GetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

class GetRepositoryImp(
    private val appContext: Application,
    private val remoteDataSource: RemoteDataSource
) : GetRepository {

    override fun getCandles(pair: String, resolution: String, limit: Int, end: Long): Flow<NetworkResults<List<CandleResponseModel>>> = flow {
        if (isNetworkAvailable(appContext)) {
            val response = remoteDataSource.getCandles(pair, resolution, limit, end)
            if (response.isSuccessful) {
                emit(NetworkResults.Success(response.body()!!))
            } else {
                emit(NetworkResults.Error(response.message()))
            }
        } else {
            emit(NetworkResults.Error("No internet connection"))
        }
    }.onStart {
        emit(NetworkResults.Loading())
    }.catch { e ->
        emit(NetworkResults.Error(e.message ?: "Unknown error"))
    }

    override fun getOrderBook(pairId: Int, buy: Int, sell: Int): Flow<NetworkResults<OrderBookResponseModel>> = flow {
        if (isNetworkAvailable(appContext)) {
            val response = remoteDataSource.getOrderBook(pairId, buy, sell)
            if (response.isSuccessful) {
                emit(NetworkResults.Success(response.body()!!))
            } else {
                emit(NetworkResults.Error(response.message()))
            }
        } else {
            emit(NetworkResults.Error("No internet connection"))
        }
    }.onStart {
        emit(NetworkResults.Loading())
    }.catch { e ->
        emit(NetworkResults.Error(e.message ?: "Unknown error"))
    }
}
