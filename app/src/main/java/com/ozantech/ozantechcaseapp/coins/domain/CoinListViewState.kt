package com.ozantech.ozantechcaseapp.coins.domain

import com.ozantech.ozantechcaseapp.core.model.extension.StringExt.empty
import com.ozantech.ozantechcaseapp.core.model.local.UiState
import com.ozantech.ozantechcaseapp.core.uicomponents.platform.viewmodel.BaseViewState

data class CoinListViewState(
    val errorMessage: String? = String.empty,
    override val uiState: UiState = UiState.LOADING
) : BaseViewState
