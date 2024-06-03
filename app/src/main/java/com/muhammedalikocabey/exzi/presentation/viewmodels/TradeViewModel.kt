package com.muhammedalikocabey.exzi.presentation.viewmodels

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhammedalikocabey.exzi.core.utils.NetworkResults
import com.muhammedalikocabey.exzi.core.utils.PairName
import com.muhammedalikocabey.exzi.core.utils.getBaseCurrency
import com.muhammedalikocabey.exzi.core.utils.getQuoteCurrency
import com.muhammedalikocabey.exzi.core.utils.toApiFormat
import com.muhammedalikocabey.exzi.data.model.CandleResponseModel
import com.muhammedalikocabey.exzi.domain.usecases.GetCandlesUseCase
import com.muhammedalikocabey.exzi.presentation.home.HomeFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TradeViewModel @Inject constructor(private val getCandlesUseCase: GetCandlesUseCase) : ViewModel() {

    private var _candleList: MutableLiveData<NetworkResults<List<CandleResponseModel>>> = MutableLiveData()
    var candleList: LiveData<NetworkResults<List<CandleResponseModel>>> = _candleList

    private var selectedCurrency: PairName = DEFAULT_CURRENCY

    private var job: Job? = null

    companion object {
        const val LIMIT = 100
        const val resolution = "D"
        val DEFAULT_CURRENCY = PairName.BTC_USDT
    }

    init {
        startFetchingCandles()
    }

    fun startFetchingCandles() {
        job?.cancel()
        job = viewModelScope.launch {
            while (isActive) {
                fetchCandles()
                delay(3000)
            }
        }
    }

    private fun fetchCandles() {
        viewModelScope.launch {
            getCandlesUseCase.getCandles(
                selectedCurrency.toApiFormat(),
                resolution,
                LIMIT,
                getCurrentEpochTime()
            ).onEach {
                _candleList.value = it
            }.launchIn(this)
        }
    }

    fun stopFetchingCandles() {
        job?.cancel()
    }

    fun parseArgs(arguments: Bundle?) {
        arguments?.let { arg ->
            val currency = arg.getSerializable(HomeFragment.SELECTED_CURRENCY) as? PairName
            if (currency != null) {
                selectedCurrency = currency
            } else {
                selectedCurrency = PairName.BTC_USDT
            }
        } ?: run {
            selectedCurrency = PairName.BTC_USDT
        }
    }

    fun getPairName() = selectedCurrency.symbol

    fun getPair() = selectedCurrency

    fun getFirstCurrency() = selectedCurrency.getBaseCurrency()

    fun getSecondCurrency() = selectedCurrency.getQuoteCurrency()

    private fun getCurrentEpochTime(): Long {
        return System.currentTimeMillis() / 1000
    }

    fun onSpotTabClicked() {
        // Handle Spot tab click in 2 way dataBinding from layout
    }

    fun onMarginTabClicked() {
        // Handle Margin tab click in 2 way dataBinding from layout
    }

    fun onConvertTabClicked() {
        // Handle Convert tab click in 2 way dataBinding from layout
    }
}
