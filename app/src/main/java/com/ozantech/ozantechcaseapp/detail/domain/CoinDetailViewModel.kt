package com.ozantech.ozantechcaseapp.detail.domain

import androidx.lifecycle.viewModelScope
import com.ozantech.ozantechcaseapp.core.data.repository.CoinRepository
import com.ozantech.ozantechcaseapp.core.database.db.AppDao
import com.ozantech.ozantechcaseapp.core.model.extension.EntityExt.toEntity
import com.ozantech.ozantechcaseapp.core.model.local.UiState
import com.ozantech.ozantechcaseapp.core.model.remote.network.Status
import com.ozantech.ozantechcaseapp.core.model.remote.response.CoinResponse
import com.ozantech.ozantechcaseapp.core.uicomponents.platform.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val coinRepository: CoinRepository, private val appDao: AppDao
) : BaseViewModel<CoinDetailViewState, CoinDetailViewAction>(CoinDetailViewState()) {

    fun fetchCoinHistory(coinId: String?) {
        coinId?.let {
            viewModelScope.launch {
                val res = coinRepository.fetchCoinHistory(uuid = it).first()

                when (res.status) {
                    Status.SUCCESS -> res.data?.let {
                        sendAction(CoinDetailViewAction.OnSuccess(coinHistory = it))
                    }

                    Status.ERROR -> sendAction(CoinDetailViewAction.OnFailure(errorMessage = res.error?.message))
                }
            }
        }
    }

    fun toggleFavorite(coinItem: CoinResponse.Coin) {
        viewModelScope.launch {
            val coinEntity = coinItem.toEntity()
            if (coinEntity.isFavorite) {
                appDao.insert(coinEntity)
            } else {
                appDao.delete(coinEntity)
            }
        }
    }

    override fun onReduceState(viewAction: CoinDetailViewAction): CoinDetailViewState =
        when (viewAction) {
            is CoinDetailViewAction.OnFailure -> state.copy(
                uiState = UiState.ERROR, errorMessage = viewAction.errorMessage
            )

            CoinDetailViewAction.OnLoading -> state.copy(
                uiState = UiState.LOADING, errorMessage = null
            )

            is CoinDetailViewAction.OnSuccess -> state.copy(
                errorMessage = null,
                uiState = UiState.SUCCESS,
                coinHistory = viewAction.coinHistory
            )
        }
}
