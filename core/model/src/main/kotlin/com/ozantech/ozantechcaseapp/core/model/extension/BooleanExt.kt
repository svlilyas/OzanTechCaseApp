package com.ozantech.ozantechcaseapp.core.model.extension

object BooleanExt {
    fun Boolean?.safeGet() = this ?: false
}