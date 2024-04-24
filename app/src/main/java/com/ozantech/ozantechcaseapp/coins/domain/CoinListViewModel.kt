package com.ozantech.ozantechcaseapp.coins.domain

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.TerminalSeparatorType
import androidx.paging.cachedIn
import androidx.paging.insertHeaderItem
import androidx.paging.insertSeparators
import androidx.paging.map
import com.ozantech.ozantechcaseapp.coins.presentation.CoinListItem
import com.ozantech.ozantechcaseapp.core.database.db.AppDao
import com.ozantech.ozantechcaseapp.core.database.sharedpref.SharedPreferenceManager
import com.ozantech.ozantechcaseapp.core.model.extension.EntityExt.toEntity
import com.ozantech.ozantechcaseapp.core.model.extension.StringExt.empty
import com.ozantech.ozantechcaseapp.core.model.local.UiState
import com.ozantech.ozantechcaseapp.core.model.remote.response.CoinResponse
import com.ozantech.ozantechcaseapp.core.uicomponents.platform.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    pager: Pager<Int, CoinResponse.Coin>,
    private val sharedPref: SharedPreferenceManager,
    private val appDao: AppDao
) : BaseViewModel<CoinListViewState, CoinListViewAction>(CoinListViewState()) {
    val coinsPagingFlow =
        pager.flow.map { pagingData -> pagingData.map { CoinListItem.CoinItem(coinItem = it) } }
            .map {
                it.insertSeparators { before, after ->
                    if (after != null && before != null) {
                        return@insertSeparators CoinListItem.Separator
                    } else {
                        null
                    }
                }
            }.map {
                it.insertHeaderItem(TerminalSeparatorType.FULLY_COMPLETE, CoinListItem.Header)
            }.onEach {
                sendAction(viewAction = CoinListViewAction.OnSuccess)
            }.cachedIn(viewModelScope)

    fun changeFilterType(filterType: String) {
        sharedPref.filterType = filterType
    }

    fun toggleFavorite(coinItem: CoinResponse.Coin) {
        viewModelScope.launch {
            val coinEntity = coinItem.toEntity()
            if (coinEntity.isFavorite) {
                appDao.insert(coinEntity)
            } else {
                appDao.delete(coinEntity)
            }

            sendAction(CoinListViewAction.RefreshList)
        }
    }

    override fun onReduceState(viewAction: CoinListViewAction): CoinListViewState =
        when (viewAction) {
            is CoinListViewAction.OnFailure -> state.copy(
                uiState = UiState.ERROR, errorMessage = viewAction.errorMessage
            )

            CoinListViewAction.OnLoading -> state.copy(
                uiState = UiState.LOADING, errorMessage = String.empty
            )

            CoinListViewAction.OnSuccess -> state.copy(
                errorMessage = null,
                uiState = UiState.SUCCESS,
                refreshList = false
            )

            CoinListViewAction.GetFavorites -> state.copy(
                errorMessage = null,
                uiState = UiState.LOADING
            )

            CoinListViewAction.RestoreState -> CoinListViewState()
            CoinListViewAction.RefreshList -> state.copy(
                errorMessage = null,
                uiState = UiState.LOADING,
                refreshList = true
            )
        }
}
