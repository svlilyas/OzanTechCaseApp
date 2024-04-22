package com.ozantech.ozantechcaseapp.core.model.extension

import com.ozantech.ozantechcaseapp.core.model.utils.AppConstants.Companion.STRING_EMPTY

object StringExt {
    val String.Companion.empty: String
        inline get() = STRING_EMPTY

    fun Any?.toStringOrNull(): String? = this?.toString()
}