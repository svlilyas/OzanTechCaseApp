package com.ozantech.ozantechcaseapp.core.data.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.ozantech.ozantechcaseapp.core.data.repository.CoinRepository
import com.ozantech.ozantechcaseapp.core.database.db.AppDatabase
import com.ozantech.ozantechcaseapp.core.model.remote.network.Status
import com.ozantech.ozantechcaseapp.core.model.remote.response.CoinResponse
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class CoinRemoteMediator @Inject constructor(
    private val coinRepository: CoinRepository,
    private val appDatabase: AppDatabase
) : RemoteMediator<Int, CoinResponse.Coin>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CoinResponse.Coin>
    ): MediatorResult {
        return try {
            if (loadType == LoadType.PREPEND) {
                return MediatorResult.Success(
                    endOfPaginationReached = true
                )
            }

            var pairList: List<CoinResponse.Coin> = emptyList()

            val response = coinRepository.fetchCoins().first()

            when (response.status) {
                Status.SUCCESS -> pairList = response.data?.coins ?: emptyList()
                Status.ERROR -> Timber.e(response.error?.message)
            }

            appDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    appDatabase.appDao().clearAllPairs()
                }

                val favoritePairs = appDatabase.appDao().getFavoriteCoins().first()

                /**
                 * Controlling whether pairResponse contain inside favorite coins_db
                 */
                /*pairDatabase.pairDao().upsertAll(pairResponseList = pairList.map { item1 ->
                    if (favoritePairs.firstOrNull { item2 ->
                            item1.pair == item2.pair
                        } != null) {
                        item1.isFavorite = true
                    }
                    item1
                })*/
            }

            MediatorResult.Success(
                endOfPaginationReached = true
            )
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        }
    }
}