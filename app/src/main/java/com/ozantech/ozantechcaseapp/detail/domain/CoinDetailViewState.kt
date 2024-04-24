package com.ozantech.ozantechcaseapp.detail.domain

import com.ozantech.ozantechcaseapp.core.model.extension.StringExt.empty
import com.ozantech.ozantechcaseapp.core.model.local.UiState
import com.ozantech.ozantechcaseapp.core.model.remote.response.CoinHistoryResponse
import com.ozantech.ozantechcaseapp.core.uicomponents.platform.viewmodel.BaseViewState

data class CoinDetailViewState(
    val errorMessage: String? = String.empty,
    override val uiState: UiState = UiState.LOADING,
    val coinHistory: CoinHistoryResponse? = null
) : BaseViewState
