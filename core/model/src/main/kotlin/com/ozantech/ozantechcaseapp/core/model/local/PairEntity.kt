package com.ozantech.ozantechcaseapp.core.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ozantech.ozantechcaseapp.core.model.extension.StringExt.empty

@Entity(tableName = "favorites_db")
data class PairEntity(
    @PrimaryKey(autoGenerate = false)
    val pair: String = String.empty,
    val pairNormalized: String? = String.empty,
    val volume: Double? = 0.0,
    val dailyPercent: Double? = 0.0,
    val last: Double? = 0.0,
    val numeratorSymbol: String? = String.empty,
    var isFavorite: Boolean = false
)