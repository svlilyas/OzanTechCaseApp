package com.ozantech.ozantechcaseapp.core.model.extension

object CollectionExt {
    fun <T> List<T>?.safeGet(index: Int): T? = try {
        if (!isNullOrEmpty() && size > index) {
            get(index)
        } else {
            null
        }
    } catch (e: IndexOutOfBoundsException) {
        null
    }
}