package com.ozantech.ozantechcaseapp.core.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ozantech.ozantechcaseapp.core.model.extension.StringExt.empty

@Entity(tableName = "favorites_db")
data class CoinEntity(
    @PrimaryKey(autoGenerate = false)
    val uuid: String = String.empty,
    val symbol: String? = null,
    val name: String? = null,
    val color: String? = null,
    val marketCap: String? = null,
    val price: String? = null,
    val change: String? = null,
    var isFavorite: Boolean = false
)