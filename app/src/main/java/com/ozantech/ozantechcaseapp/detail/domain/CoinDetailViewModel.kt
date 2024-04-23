package com.ozantech.ozantechcaseapp.detail.domain

import com.ozantech.ozantechcaseapp.core.data.repository.CoinRepository
import com.ozantech.ozantechcaseapp.core.model.extension.StringExt.empty
import com.ozantech.ozantechcaseapp.core.model.local.UiState
import com.ozantech.ozantechcaseapp.core.uicomponents.platform.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val coinRepository: CoinRepository
) : BaseViewModel<CoinDetailViewState, CoinDetailViewAction>(CoinDetailViewState()) {
    override fun onReduceState(viewAction: CoinDetailViewAction): CoinDetailViewState =
        when (viewAction) {
            is CoinDetailViewAction.OnFailure -> state.copy(
                uiState = UiState.ERROR, errorMessage = viewAction.errorMessage
            )

            CoinDetailViewAction.OnLoading -> state.copy(
                uiState = UiState.LOADING, errorMessage = String.empty
            )

            CoinDetailViewAction.OnSuccess -> state.copy(
                errorMessage = null,
                uiState = UiState.SUCCESS
            )

        }
}
