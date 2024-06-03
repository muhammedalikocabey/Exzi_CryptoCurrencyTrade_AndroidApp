package com.muhammedalikocabey.exzi.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhammedalikocabey.exzi.core.utils.NetworkResults
import com.muhammedalikocabey.exzi.core.utils.PairName
import com.muhammedalikocabey.exzi.core.utils.getBaseCurrency
import com.muhammedalikocabey.exzi.core.utils.getQuoteCurrency
import com.muhammedalikocabey.exzi.data.model.OrderBookResponseModel
import com.muhammedalikocabey.exzi.domain.usecases.GetOrderBookUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val getOrderBookUseCase: GetOrderBookUseCase) : ViewModel() {

    companion object {
        const val DEFAULT_ORDER_COUNT = 20
    }

    private var _orderResponse: MutableLiveData<NetworkResults<OrderBookResponseModel>> = MutableLiveData()
    var orderResponse: LiveData<NetworkResults<OrderBookResponseModel>> = _orderResponse

    private var listedPair: PairName

    private var job: Job? = null

    init {
        startFetchingOrderBook(pairId = 1041, buy = DEFAULT_ORDER_COUNT, sell = DEFAULT_ORDER_COUNT)
        listedPair = PairName.BTC_USDT
    }

    fun startFetchingOrderBook(pairId: Int, buy: Int, sell: Int) {
        job?.cancel()
        job = viewModelScope.launch {
            while (isActive) {
                getOrderBookUseCase.getOrderBook(pairId, buy, sell).collect {
                    _orderResponse.postValue(it)
                }
                delay(3000)
            }
        }
    }

    fun stopFetchingOrderBook() {
        job?.cancel()
    }

    fun getPairName() = listedPair.symbol

    fun getPair() = listedPair

    fun getFirstCurrency() = listedPair.getBaseCurrency()

    fun getSecondCurrency() = listedPair.getQuoteCurrency()

    fun formatAndGetSecondCurrency(): String {
        val secondCurrency = getSecondCurrency()
        return "($secondCurrency)"
    }
}
