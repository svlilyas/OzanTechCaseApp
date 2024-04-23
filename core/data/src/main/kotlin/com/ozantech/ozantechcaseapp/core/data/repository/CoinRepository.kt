package com.ozantech.ozantechcaseapp.core.data.repository

import androidx.annotation.WorkerThread
import com.ozantech.ozantechcaseapp.core.data.client.CoinClient
import com.ozantech.ozantechcaseapp.core.database.db.AppDatabase
import com.ozantech.ozantechcaseapp.core.model.local.CoinEntity
import com.ozantech.ozantechcaseapp.core.model.remote.network.Resource
import com.ozantech.ozantechcaseapp.core.model.remote.response.CoinHistoryResponse
import com.ozantech.ozantechcaseapp.core.model.remote.response.CoinResponse
import com.ozantech.ozantechcaseapp.core.network.utils.NetworkHandler.handleResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CoinRepository @Inject constructor(
    private val coinClient: CoinClient,
    private val ioDispatcher: CoroutineDispatcher,
    private val pairDb: AppDatabase
) {
    @WorkerThread
    suspend fun fetchCoins(orderBy: String): Flow<Resource<CoinResponse>> =
        handleResponse {
            coinClient.fetchCoins(orderBy = orderBy)
        }.flowOn(ioDispatcher)

    suspend fun fetchCoinHistory(uuid: String): Flow<Resource<CoinHistoryResponse>> =
        handleResponse {
            coinClient.fetchCoinHistory(uuid = uuid)
        }.flowOn(ioDispatcher)

    @WorkerThread
    suspend fun getFavoriteCoins(): Flow<List<CoinEntity>> = flow {
        emit(pairDb.appDao().getFavoriteCoins().first())
    }.flowOn(ioDispatcher)

    /*    @WorkerThread
        suspend fun toggleFavoritePair(
            pairEntity: PairEntity
        ): Flow<Boolean> = flow {
            val rowsEffected = pairDb.withTransaction {
                if (!pairEntity.isFavorite) {
                    pairDb.pairDao().insert(pairEntity = pairEntity)
                    pairDb.pairDao().updatePair(pair = pairEntity.pair, isFavorite = true)
                } else {
                    pairDb.pairDao().delete(pairEntity = pairEntity)
                    pairDb.pairDao().updatePair(pair = pairEntity.pair, isFavorite = false)
                }
            }

            emit(rowsEffected > 0)
        }*/
}
