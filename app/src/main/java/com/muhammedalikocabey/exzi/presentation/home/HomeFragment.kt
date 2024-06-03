package com.muhammedalikocabey.exzi.presentation.home

import android.net.Network
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.muhammedalikocabey.exzi.R
import com.muhammedalikocabey.exzi.core.adapters.NegativeOrderAdapter
import com.muhammedalikocabey.exzi.core.adapters.PositiveOrderAdapter
import com.muhammedalikocabey.exzi.core.utils.NetworkResults
import com.muhammedalikocabey.exzi.core.utils.PairName
import com.muhammedalikocabey.exzi.core.utils.isNetworkAvailable
import com.muhammedalikocabey.exzi.databinding.FragmentHomeBinding
import com.muhammedalikocabey.exzi.presentation.base.BaseFragment
import com.muhammedalikocabey.exzi.presentation.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels()

    private lateinit var negativeOrderAdapter: NegativeOrderAdapter
    private lateinit var positiveOrderAdapter: PositiveOrderAdapter

    companion object {
        const val SELECTED_CURRENCY = "selected_currency"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setupSwipeRefresh()
        handleClickListeners()
        observer()
        setDynamicValues()
    }
    private fun handleClickListeners() {
        binding.apply {
        }
    }
    private fun initView() {
        binding.navTrade.setOnClickListener {
            openTradeFragment(homeViewModel.getPair())
        }

        negativeOrderAdapter = NegativeOrderAdapter()
        positiveOrderAdapter = PositiveOrderAdapter()

        binding.lastNegativePricesRecyclerView.apply {
            adapter = negativeOrderAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        binding.lastPositivePricesRecyclerView.apply {
            adapter = positiveOrderAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setDynamicValues() {
        binding.currenciesName.text = homeViewModel.getPairName()
        binding.pairText.text = homeViewModel.getPairName()
        binding.cryptoCurrencyText.hint = homeViewModel.getFirstCurrency()
        binding.btcValueText2.text = homeViewModel.getFirstCurrency()
        binding.currencyText.hint = homeViewModel.getSecondCurrency()
        binding.usdPriceEqualityText2.text = homeViewModel.getSecondCurrency()
        binding.rightPriceText2.text = homeViewModel.formatAndGetSecondCurrency()
    }

    private fun openTradeFragment(pairName: PairName) {
        homeViewModel.stopFetchingOrderBook()
        val bundle = Bundle()
        bundle.putSerializable(SELECTED_CURRENCY, pairName)

        findNavController().navigate(R.id.action_homeFragment_to_tradeFragment, bundle)
    }

    private fun observer() {
        homeViewModel.orderResponse.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResults.Success -> binding.apply {
                    it.data?.let { orderBookResponse ->
                        val viewEntity = orderBookResponse.toViewEntity()
                        negativeOrderAdapter.submitList(viewEntity?.buy?.take(6))
                        positiveOrderAdapter.submitList(viewEntity?.sell?.take(6))
                        binding.buySellPriceText.hint = viewEntity?.sell?.firstOrNull()?.rateF
                        binding.totalTransactionCountText.text = viewEntity?.buy?.firstOrNull()?.rateF
                    }
                    setDynamicValues()
                }
                is NetworkResults.Error -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
                is NetworkResults.Loading -> {
                }
            }
        }
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            refreshFragment()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun refreshFragment() {
        findNavController().navigate(R.id.action_homeFragment_self)
    }

    override fun onResume() {
        super.onResume()
        checkNetworkAvailability()
    }
    private fun checkNetworkAvailability() {
        if (!isNetworkAvailable(requireContext())) {
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
            homeViewModel.startFetchingOrderBook(1041, buy = HomeViewModel.DEFAULT_ORDER_COUNT, sell = HomeViewModel.DEFAULT_ORDER_COUNT)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
