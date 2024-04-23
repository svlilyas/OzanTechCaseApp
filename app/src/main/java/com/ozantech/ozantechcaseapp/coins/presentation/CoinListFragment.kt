package com.ozantech.ozantechcaseapp.coins.presentation

import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ozantech.ozantechcaseapp.R
import com.ozantech.ozantechcaseapp.coins.domain.CoinListViewModel
import com.ozantech.ozantechcaseapp.core.model.local.OrderType
import com.ozantech.ozantechcaseapp.core.uicomponents.binding.BindingFragment
import com.ozantech.ozantechcaseapp.core.uicomponents.extensions.FlowExt.flowWithLifecycle
import com.ozantech.ozantechcaseapp.databinding.FragmentCoinListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class CoinListFragment :
    BindingFragment<FragmentCoinListBinding>(R.layout.fragment_coin_list) {
    private val viewModel: CoinListViewModel by viewModels()

    private var coinAdapter: CoinAdapter? = null
    private val filterItems = OrderType.values().map { it.type }

    override fun observeData() {
        flowWithLifecycle(
            dispatcher = Dispatchers.Main,
            flowData = viewModel.coinsPagingFlow,
            repeatedLifecycle = Lifecycle.State.STARTED
        ) {
            Timber.d("DataLog -> Started")
            Timber.d("DataLog -> $it")
            coinAdapter?.submitData(it)

            binding.swCoinList.isRefreshing = false
        }
    }

    override fun getViewData() {
        binding {
            coinAdapter = CoinAdapter(viewModel = viewModel)
            val adapterItems =
                ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, filterItems)
            tvFilterName.setAdapter(adapterItems)
            rvCoins.adapter = coinAdapter

            swCoinList.setOnRefreshListener {
                coinAdapter?.refresh()
            }
        }
    }

    override fun initClickListeners() {
        binding {
            tvFilterName.setOnItemClickListener { _, _, position, _ ->
                viewModel.changeFilterType(filterType = filterItems[position])
                coinAdapter?.refresh()
            }
            coinAdapter?.setOnDebouncedClickListener { coinItem ->
                findNavController().navigate(
                    CoinListFragmentDirections.actionCoinListFragmentToCoinDetailFragment(
                        coinItem
                    )
                )
            }
        }
    }

    override fun onDestroyView() {
        coinAdapter = null
        super.onDestroyView()
    }
}