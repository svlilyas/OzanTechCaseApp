package com.ozantech.ozantechcaseapp.coins.presentation

import com.ozantech.ozantechcaseapp.core.model.remote.response.CoinResponse

sealed class CoinListItem {
    object Header : CoinListItem()
    data class CoinItem(val coinItem: CoinResponse.Coin) : CoinListItem()
    object Separator : CoinListItem()
}
