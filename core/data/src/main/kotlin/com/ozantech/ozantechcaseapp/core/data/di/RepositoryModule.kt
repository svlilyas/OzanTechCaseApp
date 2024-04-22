package com.ozantech.ozantechcaseapp.core.data.di

import com.ozantech.ozantechcaseapp.core.data.client.CoinClient
import com.ozantech.ozantechcaseapp.core.data.repository.CoinRepository
import com.ozantech.ozantechcaseapp.core.database.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideCoinRepository(
        coinClient: CoinClient,
        appDb: AppDatabase,
        ioDispatcher: CoroutineDispatcher
    ): CoinRepository =
        CoinRepository(coinClient = coinClient, ioDispatcher = ioDispatcher, pairDb = appDb)
}
