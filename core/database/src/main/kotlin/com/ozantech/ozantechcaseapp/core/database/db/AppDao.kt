package com.ozantech.ozantechcaseapp.core.database.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.ozantech.ozantechcaseapp.core.model.local.PairEntity
import com.ozantech.ozantechcaseapp.core.model.remote.response.CoinResponse
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    /**
     * all coins
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pairResponse: CoinResponse.Coin)

    @Upsert
    suspend fun upsertAll(pairResponseList: List<CoinResponse.Coin>)

    @Query("SELECT * FROM coins_db")
    fun pagingSource(): PagingSource<Int, CoinResponse.Coin>

    @Query("SELECT * FROM coins_db")
    fun getAllPairs(): Flow<List<CoinResponse.Coin>>

    @Query("Delete From coins_db")
    suspend fun clearAllPairs()

    /**
     * favourite coins
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pairEntity: PairEntity)

    @Delete
    suspend fun delete(pairEntity: PairEntity): Int

    @Query("Delete From favorites_db")
    suspend fun clearAllFavorites()

    @Query("SELECT * FROM favorites_db")
    fun getFavoriteCoins(): Flow<List<PairEntity>>
}
