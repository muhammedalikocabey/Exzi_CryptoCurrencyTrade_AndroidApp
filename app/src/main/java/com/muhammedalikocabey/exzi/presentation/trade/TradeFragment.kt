package com.muhammedalikocabey.exzi.presentation.trade

import android.graphics.Color
import android.graphics.Paint
import android.net.Network
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.data.CandleData
import com.github.mikephil.charting.data.CandleDataSet
import com.muhammedalikocabey.exzi.R
import com.muhammedalikocabey.exzi.core.adapters.NegativeCandleAdapter
import com.muhammedalikocabey.exzi.core.adapters.PositiveCandleAdapter
import com.muhammedalikocabey.exzi.core.utils.NetworkResults
import com.muhammedalikocabey.exzi.core.utils.getRecentHighestPrices
import com.muhammedalikocabey.exzi.core.utils.getRecentLowestPrices
import com.muhammedalikocabey.exzi.core.utils.isNetworkAvailable
import com.muhammedalikocabey.exzi.core.utils.toCandleEntries
import com.muhammedalikocabey.exzi.core.utils.toOpenCloseCurrencyFormat
import com.muhammedalikocabey.exzi.data.model.CandleResponseModel
import com.muhammedalikocabey.exzi.databinding.FragmentTradeBinding
import com.muhammedalikocabey.exzi.presentation.base.BaseFragment
import com.muhammedalikocabey.exzi.presentation.viewmodels.TradeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TradeFragment : BaseFragment() {

    private var _binding: FragmentTradeBinding? = null
    private val binding get() = _binding!!
    private val tradeViewModel: TradeViewModel by viewModels()

    private lateinit var negativeCandleAdapter: NegativeCandleAdapter
    private lateinit var positiveCandleAdapter: PositiveCandleAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_trade, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tradeViewModel.parseArgs(arguments)
        initView()
        handleClickListeners()
        observer()
        setDynamicValues()
    }
    private fun handleClickListeners() {
        binding.apply {
        }
    }
    private fun initView() {
        negativeCandleAdapter = NegativeCandleAdapter()
        positiveCandleAdapter = PositiveCandleAdapter()

        binding.negativeOrderRecycler.apply {
            adapter = negativeCandleAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        binding.positiveOrderRecycler.apply {
            adapter = positiveCandleAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observer() {
        tradeViewModel.candleList.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResults.Success -> binding.apply {
                    it.data?.let { candleList ->
                        val viewEntity = candleList.map { candle -> candle.toViewEntity() }
                        prepareChart(viewEntity)
                        prepareChart2(viewEntity)
                        val negativeLimitedList = viewEntity.getRecentLowestPrices()
                        val positiveLimitedList = viewEntity.getRecentHighestPrices()
                        binding.transactionPrice.text = positiveLimitedList[0].high.toOpenCloseCurrencyFormat()

                        negativeCandleAdapter.submitList(negativeLimitedList)
                        positiveCandleAdapter.submitList(positiveLimitedList)
                    }
                    setDynamicValues()
                }

                is NetworkResults.Error -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }

                is NetworkResults.Loading -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun prepareChart(candleResponseModel: List<CandleResponseModel.ViewEntity>) {
        val candleEntries = candleResponseModel.toCandleEntries()

        val candleDataSet = CandleDataSet(candleEntries, "")
        candleDataSet.color = Color.rgb(80, 80, 80)
        candleDataSet.shadowColor = Color.DKGRAY
        candleDataSet.shadowWidth = 0.7f
        candleDataSet.decreasingColor = Color.rgb(246, 84, 84)
        candleDataSet.decreasingPaintStyle = Paint.Style.FILL
        candleDataSet.increasingColor = Color.rgb(30, 204, 149)
        candleDataSet.increasingPaintStyle = Paint.Style.FILL
        candleDataSet.neutralColor = Color.BLUE

        val candleData = CandleData(candleDataSet)
        binding.candleStickChart.data = candleData

        val xAxis = binding.candleStickChart.xAxis
        xAxis.setDrawLabels(false)
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        val yAxisLeft = binding.candleStickChart.axisLeft
        yAxisLeft.setDrawLabels(false)
        yAxisLeft.setDrawGridLines(false)
        yAxisLeft.setDrawAxisLine(false)

        val yAxisRight = binding.candleStickChart.axisRight
        yAxisRight.setDrawLabels(false)
        yAxisRight.setDrawGridLines(false)
        yAxisRight.setDrawAxisLine(false)

        binding.candleStickChart.description.isEnabled = false
        binding.candleStickChart.legend.isEnabled = false

        binding.candleStickChart.setBackgroundColor(Color.rgb(20, 20, 30))
        binding.candleStickChart.setDrawGridBackground(false)
        binding.candleStickChart.setDrawBorders(false)

        binding.candleStickChart.setDrawGridBackground(false)
        binding.candleStickChart.axisLeft.setDrawGridLines(false)
        binding.candleStickChart.xAxis.setDrawGridLines(false)
        binding.candleStickChart.axisRight.setDrawGridLines(false)

        binding.candleStickChart.xAxis.setDrawAxisLine(false)
        binding.candleStickChart.axisLeft.setDrawAxisLine(false)
        binding.candleStickChart.axisRight.setDrawAxisLine(false)

        binding.candleStickChart.invalidate()
    }

    fun prepareChart2(candleResponseModel: List<CandleResponseModel.ViewEntity>) {
        val candleEntries = candleResponseModel.toCandleEntries()

        val candleDataSet = CandleDataSet(candleEntries, "")
        candleDataSet.color = Color.rgb(80, 80, 80)
        candleDataSet.shadowColor = Color.DKGRAY
        candleDataSet.shadowWidth = 0.7f
        candleDataSet.decreasingColor = Color.rgb(246, 84, 84)
        candleDataSet.decreasingPaintStyle = Paint.Style.FILL
        candleDataSet.increasingColor = Color.rgb(30, 204, 149)
        candleDataSet.increasingPaintStyle = Paint.Style.FILL
        candleDataSet.neutralColor = Color.BLUE

        val candleData = CandleData(candleDataSet)
        binding.candleStickChart2.data = candleData

        val xAxis = binding.candleStickChart2.xAxis
        xAxis.setDrawLabels(false)
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        val yAxisLeft = binding.candleStickChart2.axisLeft
        yAxisLeft.setDrawLabels(false)
        yAxisLeft.setDrawGridLines(false)
        yAxisLeft.setDrawAxisLine(false)

        val yAxisRight = binding.candleStickChart2.axisRight
        yAxisRight.setDrawLabels(false)
        yAxisRight.setDrawGridLines(false)
        yAxisRight.setDrawAxisLine(false)

        binding.candleStickChart2.description.isEnabled = false
        binding.candleStickChart2.legend.isEnabled = false

        binding.candleStickChart.setBackgroundColor(Color.rgb(20, 20, 30))
        binding.candleStickChart2.setDrawGridBackground(false)
        binding.candleStickChart2.setDrawBorders(false)

        binding.candleStickChart2.invalidate()
    }

    private fun setDynamicValues() {
        binding.currenciesName.text = tradeViewModel.getPairName()
    }

    override fun onResume() {
        super.onResume()
        checkNetworkAvailability()
        tradeViewModel.startFetchingCandles()
    }

    override fun onPause() {
        super.onPause()
        tradeViewModel.stopFetchingCandles()
    }

    private fun checkNetworkAvailability() {
        if (isNetworkAvailable(requireContext())) {
            Toast.makeText(context, "Check Your Internet Connection", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onNetworkLost(network: Network?) {
        super.onNetworkLost(network)
        requireActivity().runOnUiThread {
        }
    }

    override fun onNetworkAvailable(network: Network) {
        super.onNetworkAvailable(network)
        requireActivity().runOnUiThread {
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
