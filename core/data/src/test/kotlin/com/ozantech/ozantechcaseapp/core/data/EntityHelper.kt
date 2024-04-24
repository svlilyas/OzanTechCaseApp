package com.ozantech.ozantechcaseapp.core.data

import com.ozantech.ozantechcaseapp.core.model.extension.EntityExt.toEntity
import com.ozantech.ozantechcaseapp.core.model.local.CoinEntity
import com.ozantech.ozantechcaseapp.core.model.remote.response.CoinResponse

object EntityHelper {
    fun generateCoinResponse(coinName: String = "CoinName") = CoinResponse(
        coins = listOf(
            CoinResponse.Coin(name = coinName),
            CoinResponse.Coin(name = coinName),
            CoinResponse.Coin(name = coinName)
        )
    )

    fun generateCoinEntity(coinName: String = "CoinName"): List<CoinEntity> =
        generateCoinResponse(coinName = coinName).coins?.map {
            it.toEntity()
        } ?: emptyList()
}