package com.ozantech.ozantechcaseapp.core.data.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.ozantech.ozantechcaseapp.core.data.repository.CoinRepository
import com.ozantech.ozantechcaseapp.core.database.db.AppDatabase
import com.ozantech.ozantechcaseapp.core.database.sharedpref.SharedPreferenceManager
import com.ozantech.ozantechcaseapp.core.model.local.OrderType
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
    private val appDatabase: AppDatabase,
    private val sharedPref: SharedPreferenceManager
) : RemoteMediator<Int, CoinResponse.Coin>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CoinResponse.Coin>
    ): MediatorResult {
        return try {
            Timber.d("DataLog -> CoinRemoteMediator -> load -> fetchCoins")

            if (loadType == LoadType.PREPEND) {
                return MediatorResult.Success(
                    endOfPaginationReached = true
                )
            }

            var coinList: List<CoinResponse.Coin> = emptyList()

            val filterType = sharedPref.filterType
            val response = coinRepository.fetchCoins(orderBy = filterType).first()

            when (response.status) {
                Status.SUCCESS -> coinList = response.data?.coins ?: emptyList()
                Status.ERROR -> Timber.e(response.error?.message)
            }

            appDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    appDatabase.appDao().clearAllCoins()
                }

                val favoriteCoins = appDatabase.appDao().getFavoriteCoins().first()

                /**
                 * Controlling whether coinResponse contain inside favorite coins_db
                 */
                appDatabase.appDao().upsertAll(pairResponseList = coinList.map { item1 ->
                    if (favoriteCoins.firstOrNull { item2 ->
                            item1.uuid == item2.uuid
                        } != null) {
                        item1.isFavorite = true
                    }
                    item1
                })
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