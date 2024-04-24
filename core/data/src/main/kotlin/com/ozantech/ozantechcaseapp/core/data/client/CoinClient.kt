package com.ozantech.ozantechcaseapp.core.data.client

import com.ozantech.ozantechcaseapp.core.model.remote.response.BaseApiResponse
import com.ozantech.ozantechcaseapp.core.model.remote.response.CoinHistoryResponse
import com.ozantech.ozantechcaseapp.core.network.service.CoinService
import retrofit2.Response
import javax.inject.Inject

class CoinClient @Inject constructor(
    private val coinService: CoinService
) {
    suspend fun fetchCoins(orderBy: String) =
        coinService.fetchCoins(orderBy = orderBy)

    suspend fun fetchCoinHistory(uuid: String): Response<BaseApiResponse<CoinHistoryResponse>> =
        coinService.fetchCoinHistory(uuid = uuid)
}
