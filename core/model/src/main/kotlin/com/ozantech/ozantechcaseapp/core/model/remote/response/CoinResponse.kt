package com.ozantech.ozantechcaseapp.core.model.remote.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ozantech.ozantechcaseapp.core.model.extension.StringExt.empty
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CoinResponse(
    @Json(name = "stats")
    val stats: Stats? = null,
    @Json(name = "coins")
    val coins: List<Coin>? = null
) {
    @JsonClass(generateAdapter = true)
    data class Stats(
        @Json(name = "total")
        val total: Int? = null,
        @Json(name = "totalCoins")
        val totalCoins: Int? = null,
        @Json(name = "totalMarkets")
        val totalMarkets: Int? = null,
        @Json(name = "totalExchanges")
        val totalExchanges: Int? = null,
        @Json(name = "totalMarketCap")
        val totalMarketCap: String? = null,
        @Json(name = "total24hVolume")
        val total24hVolume: String? = null
    )

    @Entity(tableName = "coins_db")
    @JsonClass(generateAdapter = true)
    data class Coin(
        @Json(name = "uuid")
        @PrimaryKey(autoGenerate = false)
        val uuid: String = String.empty,
        @Json(name = "symbol")
        val symbol: String? = null,
        @Json(name = "name")
        val name: String? = null,
        @Json(name = "color")
        val color: String? = null,
        @Json(name = "iconUrl")
        val iconUrl: String? = null,
        @Json(name = "marketCap")
        val marketCap: String? = null,
        @Json(name = "price")
        val price: String? = null,
        @Json(name = "listedAt")
        val listedAt: Int? = null,
        @Json(name = "tier")
        val tier: Int? = null,
        @Json(name = "change")
        val change: String? = null,
        @Json(name = "rank")
        val rank: Int? = null,
        @Json(name = "sparkline")
        val sparkline: List<String?>? = null,
        @Json(name = "lowVolume")
        val lowVolume: Boolean? = null,
        @Json(name = "coinrankingUrl")
        val coinrankingUrl: String? = null,
        @Json(name = "24hVolume")
        val hVolume: String? = null,
        @Json(name = "btcPrice")
        val btcPrice: String? = null,
        @Json(name = "contractAddresses")
        val contractAddresses: List<String?>? = null,
        @Transient
        var isFavorite: Boolean = false
    )
}