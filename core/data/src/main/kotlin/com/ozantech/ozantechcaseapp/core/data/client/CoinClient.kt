package com.ozantech.ozantechcaseapp.core.data.client

import com.ozantech.ozantechcaseapp.core.model.remote.response.BaseApiResponse
import com.ozantech.ozantechcaseapp.core.model.remote.response.CoinResponse
import com.ozantech.ozantechcaseapp.core.network.service.CoinService
import retrofit2.Response
import javax.inject.Inject

class CoinClient @Inject constructor(
    private val coinService: CoinService
) {
    suspend fun fetchCoins(): Response<BaseApiResponse<CoinResponse>> =
        coinService.fetchCoins()
}
