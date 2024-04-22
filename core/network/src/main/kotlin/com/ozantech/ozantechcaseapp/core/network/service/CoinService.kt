package com.ozantech.ozantechcaseapp.core.network.service

import com.ozantech.ozantechcaseapp.core.model.remote.response.BaseApiResponse
import com.ozantech.ozantechcaseapp.core.model.remote.response.CoinResponse
import retrofit2.Response
import retrofit2.http.GET

interface CoinService {
    object Endpoint {
        private const val basePath = "v2"

        object Coins {
            private const val mainPath = "$basePath/coins"
            const val coins = mainPath
        }
    }

    @GET(Endpoint.Coins.coins)
    suspend fun fetchCoins(): Response<BaseApiResponse<CoinResponse>>
}
