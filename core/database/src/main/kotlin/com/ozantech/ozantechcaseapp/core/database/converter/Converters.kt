package com.ozantech.ozantechcaseapp.core.database.converter

import androidx.room.TypeConverter
import com.ozantech.ozantechcaseapp.core.model.utils.JsonSerializer.toJson
import com.ozantech.ozantechcaseapp.core.model.utils.JsonSerializer.toObject

class Converters {
    @TypeConverter
    fun fromStringList(value: List<String?>?): String? =
        value?.toJson<List<String>>()

    @TypeConverter
    fun toStringList(json: String): List<String> =
        json.toObject<List<String>>() ?: emptyList()
}