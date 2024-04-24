package com.ozantech.ozantechcaseapp.core.data.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.ozantech.ozantechcaseapp.core.data.mediator.CoinRemoteMediator
import com.ozantech.ozantechcaseapp.core.data.repository.CoinRepository
import com.ozantech.ozantechcaseapp.core.database.db.AppDatabase
import com.ozantech.ozantechcaseapp.core.database.sharedpref.SharedPreferenceManager
import com.ozantech.ozantechcaseapp.core.model.remote.response.CoinResponse
import com.ozantech.ozantechcaseapp.core.model.utils.AppConstants.Companion.PAIR_LIST_PAGE_SIZE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PagingModule {
    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun provideCoinPager(
        coinRepository: CoinRepository,
        appDatabase: AppDatabase,
        sharedPref: SharedPreferenceManager
    ): Pager<Int, CoinResponse.Coin> = Pager(
        config = PagingConfig(
            pageSize = PAIR_LIST_PAGE_SIZE,
            prefetchDistance = 5,
            initialLoadSize = PAIR_LIST_PAGE_SIZE,
            enablePlaceholders = false
        ),
        remoteMediator = CoinRemoteMediator(
            coinRepository = coinRepository, appDatabase = appDatabase, sharedPref = sharedPref
        ),
        pagingSourceFactory = {
            appDatabase.appDao().pagingSource()
        }
    )
}
