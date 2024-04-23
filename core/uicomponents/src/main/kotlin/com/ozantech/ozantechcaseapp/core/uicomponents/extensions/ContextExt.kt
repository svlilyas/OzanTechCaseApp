package com.ozantech.ozantechcaseapp.core.uicomponents.extensions

import android.content.Context
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

object ContextExt {
    fun Context.toastMessage(message: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, message, duration).show()
    }

    fun Context.toastMessage(@StringRes message: Int, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, getString(message), duration).show()
    }

    fun Context.getDrawableRes(@DrawableRes drawableRes: Int) =
        ContextCompat.getDrawable(this, drawableRes)

    fun Context.getColorRes(@ColorRes colorRes: Int) =
        ContextCompat.getColor(this, colorRes)
}