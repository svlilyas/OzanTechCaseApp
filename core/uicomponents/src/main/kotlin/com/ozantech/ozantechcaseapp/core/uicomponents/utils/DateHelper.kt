package com.ozantech.ozantechcaseapp.core.uicomponents.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateHelper {
    /**
     * TimeStamp to DateTime
     */
    fun timeStampToDateTime(timeStamp: Long): String {
        return try {
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val netDate = Date(timeStamp)
            sdf.format(netDate)
        } catch (e: Exception) {
            e.toString()
        }
    }
}