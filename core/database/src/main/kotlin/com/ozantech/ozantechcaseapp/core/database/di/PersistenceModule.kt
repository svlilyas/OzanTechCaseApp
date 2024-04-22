package com.ozantech.ozantechcaseapp.core.database.di

import android.app.Application
import androidx.room.Room
import com.ozantech.ozantechcaseapp.core.database.db.AppDao
import com.ozantech.ozantechcaseapp.core.database.db.AppDatabase
import com.ozantech.ozantechcaseapp.core.model.utils.AppConstants.Companion.APP_DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {
    @Provides
    @Singleton
    fun provideAppDatabase(
        application: Application
    ): AppDatabase =
        Room.databaseBuilder(application, AppDatabase::class.java, APP_DB_NAME)
            .fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun providePokeDao(appDatabase: AppDatabase): AppDao = appDatabase.appDao()
}
