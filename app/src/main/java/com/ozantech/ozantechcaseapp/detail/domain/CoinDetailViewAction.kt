package com.ozantech.ozantechcaseapp.detail.domain

import com.ozantech.ozantechcaseapp.core.model.extension.StringExt.empty
import com.ozantech.ozantechcaseapp.core.model.remote.response.CoinHistoryResponse
import com.ozantech.ozantechcaseapp.core.uicomponents.platform.viewmodel.BaseAction

sealed class CoinDetailViewAction : BaseAction {
    data class OnFailure(val errorMessage: String? = String.empty) : CoinDetailViewAction()
    object OnLoading : CoinDetailViewAction()
    data class OnSuccess(val coinHistory: CoinHistoryResponse) : CoinDetailViewAction()
}
