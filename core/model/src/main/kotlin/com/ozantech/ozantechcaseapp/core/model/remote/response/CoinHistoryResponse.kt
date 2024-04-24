package com.ozantech.ozantechcaseapp.core.model.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CoinHistoryResponse(
    @Json(name = "change")
    val change: String? = null,
    @Json(name = "history")
    val history: List<History?>? = null
) {
    @JsonClass(generateAdapter = true)
    data class History(
        @Json(name = "price")
        val price: String? = null,
        @Json(name = "timestamp")
        val timestamp: Float? = null
    )
}