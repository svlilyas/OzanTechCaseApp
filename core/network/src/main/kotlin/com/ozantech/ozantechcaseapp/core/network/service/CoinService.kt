package com.ozantech.ozantechcaseapp.core.network.service

import com.ozantech.ozantechcaseapp.core.model.remote.response.BaseApiResponse
import com.ozantech.ozantechcaseapp.core.model.remote.response.CoinHistoryResponse
import com.ozantech.ozantechcaseapp.core.model.remote.response.CoinResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinService {
    object Endpoint {
        private const val basePath = "v2"

        // Query
        const val orderBy = "orderBy"

        // Path
        const val uuid = "uuid"

        object Coins {
            const val coins = "$basePath/coins"
            const val coinHistory = "$basePath/coin/{$uuid}/history"
        }
    }

    @GET(Endpoint.Coins.coins)
    suspend fun fetchCoins(@Query(Endpoint.orderBy) orderBy: String): Response<BaseApiResponse<CoinResponse>>

    @GET(Endpoint.Coins.coinHistory)
    suspend fun fetchCoinHistory(@Path(Endpoint.uuid) uuid: String): Response<BaseApiResponse<CoinHistoryResponse>>
}
