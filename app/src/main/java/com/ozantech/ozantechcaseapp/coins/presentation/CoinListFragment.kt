package com.ozantech.ozantechcaseapp.coins.presentation

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.ozantech.ozantechcaseapp.R
import com.ozantech.ozantechcaseapp.coins.domain.CoinListViewModel
import com.ozantech.ozantechcaseapp.core.uicomponents.binding.BindingFragment
import com.ozantech.ozantechcaseapp.databinding.FragmentCoinListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class CoinListFragment :
    BindingFragment<FragmentCoinListBinding>(R.layout.fragment_coin_list) {
    private val viewModel: CoinListViewModel by viewModels()

    override fun observeData() {
        lifecycleScope.launch {
            viewModel.uiStateFlow.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collectLatest {
                    Timber.d(it.uiState.toString())
                }
        }
    }

    override fun getViewData() {
    }

    override fun initClickListeners() {
    }
}