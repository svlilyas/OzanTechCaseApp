package com.ozantech.ozantechcaseapp.core.model.extension

import com.ozantech.ozantechcaseapp.core.model.local.CoinEntity
import com.ozantech.ozantechcaseapp.core.model.remote.response.CoinResponse

object EntityExt {
    fun CoinResponse.Coin.toEntity() = CoinEntity(
        uuid = uuid,
        name = name,
        symbol = symbol,
        marketCap = marketCap,
        isFavorite = isFavorite
    )
}