package com.ozantech.ozantechcaseapp.core.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ozantech.ozantechcaseapp.core.database.converter.Converters
import com.ozantech.ozantechcaseapp.core.model.local.PairEntity
import com.ozantech.ozantechcaseapp.core.model.remote.response.CoinResponse

@Database(
    entities = [CoinResponse.Coin::class, PairEntity::class],
    version = 8,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao
}
