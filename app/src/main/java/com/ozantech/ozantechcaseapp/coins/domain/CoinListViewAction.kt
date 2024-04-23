package com.ozantech.ozantechcaseapp.coins.domain

import com.ozantech.ozantechcaseapp.core.model.extension.StringExt.empty
import com.ozantech.ozantechcaseapp.core.uicomponents.platform.viewmodel.BaseAction

sealed class CoinListViewAction : BaseAction {
    data class OnFailure(val errorMessage: String? = String.empty) : CoinListViewAction()
    object OnLoading : CoinListViewAction()
    object OnSuccess : CoinListViewAction()
    object GetCoinList : CoinListViewAction()
}
