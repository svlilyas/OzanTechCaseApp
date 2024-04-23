package com.ozantech.ozantechcaseapp.core.uicomponents.extensions

import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.ozantech.ozantechcaseapp.core.uicomponents.R

object TextViewExt {
    fun TextView.changeTextColor(@ColorRes colorRes: Int) {
        setTextColor(ContextCompat.getColor(context, colorRes))
    }

    @BindingAdapter(
        "android:pairNameFormat"
    )
    @JvmStatic
    fun TextView.pairNameFormat(
        pairNormalized: String? = null
    ) {
        text = try {
            pairNormalized?.replace("_", "/")
        } catch (e: Exception) {
            pairNormalized
        }
    }

    @BindingAdapter(
        "android:pairNameTitle"
    )
    @JvmStatic
    fun TextView.pairNameTitle(
        pairNormalized: String? = null
    ) {
        text = try {
            context.getString(R.string.pair_name_chart, pairNormalized?.replace("_", "/"))
        } catch (e: Exception) {
            context.getString(R.string.pair_name_chart, pairNormalized)
        }
    }
}
