package com.ozantech.ozantechcaseapp.detail.presentation

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.ozantech.ozantechcaseapp.R
import com.ozantech.ozantechcaseapp.core.uicomponents.binding.BindingFragment
import com.ozantech.ozantechcaseapp.databinding.FragmentCoinDetailBinding
import com.ozantech.ozantechcaseapp.detail.domain.CoinDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CoinDetailFragment :
    BindingFragment<FragmentCoinDetailBinding>(R.layout.fragment_coin_detail) {
    private val viewModel: CoinDetailViewModel by viewModels()

    override fun observeData() {
        lifecycleScope.launch {
            viewModel.uiStateFlow.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {

                }
        }
    }

    override fun getViewData() {
    }

    override fun initClickListeners() {
    }
}